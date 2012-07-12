package org.sis.ancmessaging.server.service;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.EthiopicChronology;
import org.sis.ancmessaging.server.dao.EmergencyDao;
import org.sis.ancmessaging.server.dao.MotherDao;
import org.sis.ancmessaging.server.dao.ReminderDao;
import org.sis.ancmessaging.server.dao.TextMessageDao;
import org.sis.ancmessaging.server.domain.Emergency;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;
import org.sis.ancmessaging.server.domain.TextMessage;
import org.sis.ancmessaging.server.ui.MainForm;
import org.sis.ancmessaging.server.utility.SmsMessageHandler;
import org.smslib.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomingServiceImpl implements IInboundMessageNotification, IncomingService {
	@Autowired
	private ReminderDao reminderDao;

	@Autowired
	private MotherDao motherDao;
	
	@Autowired
	private EmergencyDao emergencyDao;

    @Autowired
    private TextMessageDao textMessageDao;
    
    

    @Override
    public void process(AGateway aGateway, Message.MessageTypes messageTypes, InboundMessage inboundMessage) {
        MainForm mainForm = MainForm.getInstance();
        try {
            aGateway.deleteMessage(inboundMessage);
        } catch (Exception e) {
            mainForm.setLogText("\nError: " + e.getMessage());
        }

        if (messageTypes == Message.MessageTypes.STATUSREPORT) {
            StatusReportMessage message = (StatusReportMessage)inboundMessage;

            int smscCode = Integer.parseInt(message.getRefNo());
            String recipient = "+" + message.getRecipient();
            
            mainForm.setLogText("\n\nIncoming status Report ...");

            TextMessage textMessage = textMessageDao.getBySmscCodeAndRecipient(smscCode, recipient);
            if (textMessage != null) {
                Mother mother = motherDao.getMother(textMessage.getSeqId());
                String reminderType = textMessage.getMessageType();
                boolean reminderAlreadySent;
                if (reminderType.equals("MONTHLY")) {
                    reminderAlreadySent = motherDao.hasMonthlyReminder(mother);
                } else {
                    reminderAlreadySent = motherDao.hasWeeklyReminder(mother);
                }

                if (!reminderAlreadySent) {
                    textMessageDao.changeStatus(textMessage, "A");
                    mainForm.setLogText("\nReminder to be written to database ...");
                    DateTime eddGreg = new DateTime(mother.getEdd());
                    LocalDate eddEth = eddGreg.withChronology(EthiopicChronology.getInstance()).toLocalDate();
                    String date = eddEth.getDayOfMonth() + "/" + eddEth.getMonthOfYear() + "/" + eddEth.getYear();
                    String gott = (mother.getGott() == null) ? "Urban" : mother.getGott();
                    String template = "ID: " + mother.getSeqId() + "\n" + mother.getFullName() + "\n" + gott + "\nEDD: " + date;

                    Reminder reminder = new Reminder();
                    reminder.setStatus("UNCONFIRMED");
                    reminder.setContent(template);
                    reminder.setMother(mother);
                    reminder.setReminderSentOn(new DateTime().toLocalDate().toDate());
                    reminder.setReminderType(textMessage.getMessageType());
                    reminderDao.save(reminder);
                } else {
                    if (textMessage.getStatus().equals("P")) {
                        textMessageDao.changeStatus(textMessage, "R");
                    }
                }
            }
            mainForm.setLogText("\nProcess completed.");
            return;
        }

        String content = inboundMessage.getText().trim();
        String identifier = content.substring(0, 1);
        int seqId = Integer.parseInt(content.substring(2).trim());
        Mother mother = motherDao.getMother(seqId);
        
        if (identifier.equals("1") || identifier.equals("2")) {
            mainForm.setLogText("\n\n" + new DateTime().toString());
        }
        
        if (identifier.equals("1")) {
            
            Reminder reminder = reminderDao.getLatestReminder(mother);
            if (reminder.getStatus().equals("UNCONFIRMED")) {
                reminder.setConfirmationArrivedOn(DateTime.now().toDate());
                reminder.setStatus("CONFIRMED");
                reminderDao.update(reminder);
            }
            mainForm.setLogText("\n1 Reminder Confirmed.");
        } else if (identifier.equals("2")) {
            SmsMessageHandler messageHandler = new SmsMessageHandler();
            String recipient = emergencyDao.getEmergencyPhone();
            messageHandler.setRecipient(recipient);
            
            Emergency emergency = new Emergency();

            emergency.setArrivedOn(new DateTime().toDate());

            String workerName = mother.getHealthWorker().getFullName();
            String messageContent = "Emergency\n" + mother.getMotherId() + "\n" + mother.getFullName()
                        + "\n" + workerName + "\n" + mother.getGott();
            emergency.setContent(messageContent);
            messageHandler.setContent(messageContent);
            messageHandler.setStatusReportEnabled(false);
            emergency.setMother(mother);

            emergencyDao.saveEmergency(emergency);

            try {
                messageHandler.sendMessage();
                mainForm.setLogText("\n1 emergency sent to HC");
            } catch (Exception e) {
                mainForm.setLogText("\nError: " + "\n" + e.getMessage());
            }
        }
        mainForm.setLogText("\nProcess completed.");
    }

    @Override
    public void readIncomingInInterval() {
        MainForm mainForm = MainForm.getInstance();
        SmsMessageHandler messageHandler = new SmsMessageHandler();
        try {
            List<InboundMessage> msgList = messageHandler.readMessages();

            for (InboundMessage inboundMessage : msgList) {
                String content = inboundMessage.getText().trim();
                String identifier = content.substring(0, 1);
                int seqId = Integer.parseInt(content.substring(2).trim());
                Mother m = motherDao.getMother(seqId);

                if (identifier.equals("1") || identifier.equals("2")) {
                    mainForm.setLogText("\n\n" + new DateTime().toString());
                }

                if (identifier.equals("1")) {

                    Reminder reminder = reminderDao.getLatestReminder(m);
                    if (reminder.getStatus().equals("UNCONFIRMED")) {
                        reminder.setConfirmationArrivedOn(DateTime.now().toDate());
                        reminder.setStatus("CONFIRMED");
                        reminderDao.update(reminder);
                    }
                    mainForm.setLogText("\n1 Reminder Confirmed.");
                } else if (identifier.equals("2")) {
                    //SmsMessageHandler messageHandler = new SmsMessageHandler();
                    String r = emergencyDao.getEmergencyPhone();
                    messageHandler.setRecipient(r);

                    Emergency emergency = new Emergency();

                    emergency.setArrivedOn(new DateTime().toDate());

                    String workerName = m.getHealthWorker().getFullName();
                    String messageContent = "Emergency\n" + m.getMotherId() + "\n" + m.getFullName()
                            + "\n" + workerName + "\n" + m.getGott();
                    emergency.setContent(messageContent);
                    messageHandler.setContent(messageContent);
                    messageHandler.setStatusReportEnabled(false);
                    emergency.setMother(m);

                    emergencyDao.saveEmergency(emergency);

                    try {
                        messageHandler.sendMessage();
                        mainForm.setLogText("\n1 emergency sent to HC");
                    } catch (Exception e) {
                        mainForm.setLogText("\nError: " + "\n" + e.getMessage());
                    }
                }
                mainForm.setLogText("\nProcess completed.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}

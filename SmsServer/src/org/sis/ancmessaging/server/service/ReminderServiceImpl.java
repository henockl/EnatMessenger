package org.sis.ancmessaging.server.service;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.EthiopicChronology;
import org.sis.ancmessaging.server.dao.MotherDao;
import org.sis.ancmessaging.server.dao.ReminderDao;
import org.sis.ancmessaging.server.dao.TextMessageDao;
import org.sis.ancmessaging.server.domain.HealthExtensionWorker;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;
import org.sis.ancmessaging.server.domain.TextMessage;
import org.sis.ancmessaging.server.utility.SmsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Service
public class ReminderServiceImpl implements ReminderService {

	@Autowired
	private ReminderDao reminderDao;
	
	@Autowired
	private MotherDao motherDao;

    @Autowired
    private TextMessageDao textMessageDao;
    //private MainForm mainForm;

    @Override
	public Set<Mother> getMothersToRemindOnMonth() {
		DateTime now = new DateTime();
		DateTime min = now.plusDays(7);
		DateTime max = now.plusMonths(1);
		
		Date minDate = min.toLocalDate().toDate();
		Date maxDate = max.toLocalDate().toDate();
		
		Set<Mother> mothers = motherDao.getMothersWithEddInRange(minDate, maxDate);
		Set<Mother>	mothersToSendReminder = new HashSet<Mother>();
		
		for (Mother mother : mothers) {	
			if (!reminderDao.hasMonthlyReminder(mother)) {
				mothersToSendReminder.add(mother);
			}			
		}	
		return mothersToSendReminder;
	}
	

	@Override
	public Set<Mother> getMothersToRemindOnWeek() {
		DateTime now = new DateTime();
		DateTime max = now.plusWeeks(1);
		
		Date minDate = now.toLocalDate().toDate();
		Date maxDate = max.toLocalDate().toDate();
		
		Set<Mother> mothers = motherDao.getMothersWithEddInRange(minDate, maxDate);
		Set<Mother>	mothersToSendReminder = new HashSet<Mother>();
		
		for (Mother mother : mothers) {	
			if (!reminderDao.hasWeeklyReminder(mother)) {
				mothersToSendReminder.add(mother);
			}			
		}
			
		return mothersToSendReminder;
	}

	@Override
	public void sendReminder(Mother mother, String type) {
		//mainForm = MainForm.getInstance();
		DateTime eddGreg = new DateTime(mother.getEdd());
		LocalDate eddEth = eddGreg.withChronology(EthiopicChronology.getInstance()).toLocalDate();
		String date = eddEth.getDayOfMonth() + "/" + eddEth.getMonthOfYear() + "/" + eddEth.getYear();
		String gott = (mother.getGott() == null) ? "Urban" : mother.getGott();
		String template = "ID: " + mother.getSeqId() + "\n" + mother.getFullName() + "\n" + gott + "\nEDD: " + date;
		
        SmsMessageHandler messageHandler = new SmsMessageHandler();
		messageHandler.setContent(template);
        messageHandler.setStatusReportEnabled(false);
		HealthExtensionWorker healthWorker = mother.getHealthWorker();

        String recipient = healthWorker.getPhoneNumber();
		messageHandler.setRecipient(recipient);
		try {
            int smscCode = Integer.parseInt(messageHandler.sendMessage());
            TextMessage oldMessage = textMessageDao.getMessageInProgress(mother.getSeqId());
            if (oldMessage != null) {
                textMessageDao.changeStatus(oldMessage, "R");
            }
            TextMessage textMessage = new TextMessage();
            textMessage.setMessageType(type);
            textMessage.setAttemptSendingOn(new DateTime().toLocalDate().toDate());
            textMessage.setRecipient(recipient);
            textMessage.setSeqId(mother.getSeqId());
            textMessage.setSmscCode(smscCode);
            if (smscCode > 0) {
                textMessage.setStatus("A");
                Reminder reminder = new Reminder();
                reminder.setStatus("UNCONFIRMED");
                reminder.setContent(template);
                reminder.setMother(mother);
                reminder.setReminderSentOn(new DateTime().toLocalDate().toDate());
                reminder.setReminderType(type);
                reminderDao.save(reminder);
            } else {
                textMessage.setStatus("P");
            }

            textMessageDao.save(textMessage);

            //incomingService.readIncomingInInterval();
            /*
            Reminder reminder = new Reminder();
            reminder.setStatus("UNCONFIRMED");
            reminder.setContent(template);
            reminder.setMother(mother);
            reminder.setReminderSentOn(new DateTime().toLocalDate().toDate());
            reminder.setReminderType(type);
            reminderDao.save(reminder);
            */

		} catch (Exception ex) {
            ex.printStackTrace();
		}
	}

}

package org.sis.ancmessaging.server.ui;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.sis.ancmessaging.server.domain.AppSetting;
import org.sis.ancmessaging.server.schedule.ReminderJob;
import org.sis.ancmessaging.server.service.AppSettingService;
import org.sis.ancmessaging.server.service.AppSettingServiceImpl;
import org.sis.ancmessaging.server.service.IncomingServiceImpl;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * Author: chappex
 * Date: 10/19/11
 * Time: 9:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
    private JButton startButton;
    private JPanel mainPanel;
    private JTextArea logText;
    private JButton stopButton;
    private JLabel statusLabel;

    private static MainForm mainForm = null;

    //private Logger logger;
    ApplicationContext context = null;
    AppSetting configuration = null;
    SerialModemGateway gateway = null;
    SchedulerFactory factory = null;
    IInboundMessageNotification incomingService;
    ReminderJob reminderJob;
    
    private MainForm() {

        //logger = Logger.getLogger(MainForm.class);
        context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //configuration = (SmsConfiguration) context.getBean("smsConfiguration");
        AppSettingService settingService = (AppSettingServiceImpl) context.getBean("appSettingService");
        incomingService = (IncomingServiceImpl) context.getBean("incomingService");

        configuration = settingService.getSetting();
        gateway = new SerialModemGateway(configuration.getModemName(), configuration.getComPort(),
					            configuration.getBaudRate(), configuration.getManufacturer(), configuration.getModel());
        factory = new StdSchedulerFactory();

        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                logText.setText(logText.getText() + "\n\n" + "Attempting to Start Server ...");
                try {
                    gateway.setProtocol(AGateway.Protocols.PDU);
                    gateway.setInbound(true);
                    gateway.setOutbound(true);
                    gateway.setSimPin(configuration.getSimPin());

                    Service.getInstance().setInboundMessageNotification(incomingService);
                    Service.getInstance().addGateway(gateway);
                    Service.getInstance().startService();
                    reminderJob = new ReminderJob();
                    reminderJob.start();
                    startButton.setEnabled(false);
                    statusLabel.setForeground(Color.BLUE);
                    statusLabel.setText("Started");
                    logText.setText(logText.getText() + "\n" + "Server Started ...");
                } catch (Exception ex) {
                    logText.setText(logText.getText() + "\n" + "Error Starting Server ...");
                    logText.setText(logText.getText() + "\n" + ex.getMessage());
                }

            }
        });
        statusLabel.setForeground(Color.RED);
        startButton.doClick();
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logText.setText("\nStopping SMS server ...");
                    reminderJob.stop();
                    System.exit(0);
                } catch (Exception ex) {
                    logText.setText(logText.getText() + "\n" + ex.getMessage());
                }
            }
        });
    }

    public static MainForm getInstance() {
        if (mainForm == null) {
            mainForm = new MainForm();
        }
        return mainForm;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sms Server");
        MainForm form = MainForm.getInstance();
        frame.setContentPane(form.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void setLogText(String text) {
        logText.setText(logText.getText() + text);
    }

}

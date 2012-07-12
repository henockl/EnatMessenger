package org.sis.ancmessaging.server.schedule;

import org.joda.time.DateTime;
import org.sis.ancmessaging.server.domain.AppSetting;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Report;
import org.sis.ancmessaging.server.service.*;
import org.sis.ancmessaging.server.ui.MainForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 1/28/12
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderJob {
    private int reminderInterval;
    private ScheduledExecutorService scheduler;
    private static final int NUM_THREADS = 2;
    private static final boolean DONT_INTERRUPT_IF_RUNNING = false;
    private ReminderService reminderService;
    private IncomingService incomingService;
    private AppSetting configuration;
    private ReportService reportService;
    ScheduledFuture<?> reminderFuture;

    public ReminderJob() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AppSettingService settingService = (AppSettingServiceImpl) context.getBean("appSettingService");
        reminderService = (ReminderServiceImpl) context.getBean("reminderService");
        reportService = (ReportServiceImpl) context.getBean("reportService");
        incomingService = (IncomingServiceImpl) context.getBean("incomingService");
        configuration = settingService.getSetting();
        reminderInterval = configuration.getOutgoingInterval();
        scheduler = Executors.newScheduledThreadPool(NUM_THREADS);
    }

    public void start() {
        Runnable periodicReminder = new PeriodicReminder();
        reminderFuture = scheduler.scheduleWithFixedDelay(periodicReminder, 0, reminderInterval,
                TimeUnit.MINUTES);
    }
    
    public void stop() {
        Runnable haltReminderTask = new HaltReminderTask(reminderFuture);
        scheduler.schedule(haltReminderTask, 0, TimeUnit.SECONDS);
    }

    private class PeriodicReminder implements Runnable {

        private void updateProgress(final String text) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainForm mainForm = MainForm.getInstance();
                    mainForm.setLogText(text);
                }
            });
        }

        @Override
        public void run() {
            final Set<Mother> monthlyMothers = reminderService.getMothersToRemindOnMonth();
            final Set<Mother> weeklyMothers = reminderService.getMothersToRemindOnWeek();
            final List<Report> reports = reportService.getUnsent();
            updateProgress("\n\n" + new DateTime().toString());
            updateProgress("\n" + monthlyMothers.size() + " mothers to send first reminder.");
            try {
                for (Mother mother : monthlyMothers) {
                    reminderService.sendReminder(mother, "MONTHLY");
                }
                updateProgress("\n" + weeklyMothers.size() + " mothers to send second reminder.");

                for (Mother mother : weeklyMothers) {
                    reminderService.sendReminder(mother, "WEEKLY");
                }
                updateProgress("\nSending complete.");
                String reportPhone = configuration.getReportPhone();
                if (reportPhone != null) {
                    reportPhone = reportPhone.trim();
                    if (!reportPhone.isEmpty()) {
                        updateProgress("\n" + reports.size() + " reports to send.");
                        for (Report report : reports) {
                            reportService.sendReport(report);
                        }
                        updateProgress("\nSending complete");
                    } else {
                        updateProgress("\nNo number set to send report to.");
                    }
                } else {
                    updateProgress("\nNo number set to send report to.");
                }
                incomingService.readIncomingInInterval();
            } catch (Exception ex) {
                updateProgress("\nError sending reminders / reports.");
                updateProgress("\n" + ex.getMessage());
            }
            
        }
    }

    private class HaltReminderTask implements Runnable{
        private ScheduledFuture<?> scheduledFuture;

        HaltReminderTask(ScheduledFuture<?> scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
        }

        @Override
        public void run() {
            scheduledFuture.cancel(DONT_INTERRUPT_IF_RUNNING);
            scheduler.shutdown();
        }
    }
}

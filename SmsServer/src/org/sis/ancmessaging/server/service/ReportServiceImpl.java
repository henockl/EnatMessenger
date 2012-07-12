package org.sis.ancmessaging.server.service;

import org.sis.ancmessaging.server.dao.AppSettingDao;
import org.sis.ancmessaging.server.dao.ReportDao;
import org.sis.ancmessaging.server.domain.Report;
import org.sis.ancmessaging.server.domain.ReportDetail;
import org.sis.ancmessaging.server.utility.SmsMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ReportServiceImpl implements ReportService{
    @Autowired
    private ReportDao reportDao;
    
    @Autowired
    private AppSettingDao appSettingDao;

    @Override
    public List<Report> getUnsent() {
        return reportDao.getUnsent();
    }

    @Override
    public void update(int reportId, String status) {
        reportDao.updateStatus(reportId, status);
    }

    @Override
    public void sendReport(Report report) {
        ReportDetail reportDetail = reportDao.getReportDetail(report);
        String template = "Delivery\n";
        String line1 = "Home: " + reportDetail.getQ1HomeDelivery();
        String line2 = "HC: " + reportDetail.getQ1HcDelivery();
        String line3 = "HP: " + reportDetail.getQ1HpDelivery();
        String line4 = "Referred: " + reportDetail.getQ1ReferredDelivery();
        String line5 = "Unknown: " + reportDetail.getQ1UnknownDelivery();
        String line6 = "First: " + reportDetail.getQ1FirstReminder();
        String line7 = "Second: " + reportDetail.getQ1SecondReminder();
        String line8 = "EDD: " + reportDetail.getQ1Edd();
        
        for (int i = report.getFromQuarter(); i <= report.getToQuarter(); i++) {
            switch(i) {
                case 2: line1 += ", " + reportDetail.getQ2HomeDelivery();
                        line2 += ", " + reportDetail.getQ2HcDelivery();
                        line3 += ", " + reportDetail.getQ2HpDelivery();
                        line4 += ", " + reportDetail.getQ2ReferredDelivery();
                        line5 += ", " + reportDetail.getQ2UnknownDelivery();
                        line6 += ", " + reportDetail.getQ2FirstReminder();
                        line7 += ", " + reportDetail.getQ2SecondReminder();
                        line8 += ", " + reportDetail.getQ2Edd();
                        break;
                case 3: line1 += ", " + reportDetail.getQ3HomeDelivery();
                        line2 += ", " + reportDetail.getQ3HcDelivery();
                        line3 += ", " + reportDetail.getQ3HpDelivery();
                        line4 += ", " + reportDetail.getQ3ReferredDelivery();
                        line5 += ", " + reportDetail.getQ3UnknownDelivery();
                        line6 += ", " + reportDetail.getQ3FirstReminder();
                        line7 += ", " + reportDetail.getQ3SecondReminder();
                        line8 += ", " + reportDetail.getQ3Edd();
                        break;
                case 4: line1 += ", " + reportDetail.getQ4HomeDelivery();
                        line2 += ", " + reportDetail.getQ4HcDelivery();
                        line3 += ", " + reportDetail.getQ4HpDelivery();
                        line4 += ", " + reportDetail.getQ4ReferredDelivery();
                        line5 += ", " + reportDetail.getQ4UnknownDelivery();
                        line6 += ", " + reportDetail.getQ4FirstReminder();
                        line7 += ", " + reportDetail.getQ4SecondReminder();
                        line8 += ", " + reportDetail.getQ4Edd();
                        break;
            }
        }
        line1 += "\n"; line2 += "\n"; line3 += "\n"; line4 += "\n";
        line5 += "\n"; line6 += "\n"; line7 += "\n"; line8 += "\n";

        template += line1 + line2 + line3 + line4 + line5;
        template += "Reminders\n";
        template += line6 + line7;
        template += "EDD\n" + line8;
        
        SmsMessageHandler messageHandler = new SmsMessageHandler();
        messageHandler.setContent(template);
        messageHandler.setStatusReportEnabled(true);
        
        String recipient = appSettingDao.getSetting().getReportPhone();
        messageHandler.setRecipient(recipient);
        try {
            int smscCode = Integer.parseInt(messageHandler.sendMessage());
            if (smscCode > 0) {
                report.setStatus("SENT");
                reportDao.updateStatus(report.getReportId(), report.getStatus());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    @Override
    public void sendReports() {
        List<Report> reports = reportDao.getUnsent();
        for (Report report : reports) {
            sendReport(report);
        }
    }
    */
}

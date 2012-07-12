package org.sis.ancmessaging.web;

import org.joda.time.DateTime;
import org.sis.ancmessaging.domain.Report;
import org.sis.ancmessaging.domain.ReportDetail;
import org.sis.ancmessaging.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:42 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(@RequestParam(value = "response", required = false, defaultValue = "false") boolean response,
                           Model model) {
        List<Integer> years = new ArrayList<Integer>();
        int begin = 2012;
        int end = new DateTime().getYear();
        for (int i = begin; i <= end; i++) {
            years.add(i);
        }
        model.addAttribute("years", years);
        if (response) {
            model.addAttribute("responseText", "Report will be sent in a few minutes.");
        }
        return "report";
    }
    
    private Report getReportObject(int year) {
        Report report = new Report();
        report.setYear(year);
        report.setStatus("PENDING");
        int month = new DateTime().getMonthOfYear();
        report.setFromQuarter(1);
        if (month <= 3) {
            report.setToQuarter(1);
        } else if (month <= 6) {
            report.setToQuarter(2);
        } else if (month <= 9) {
            report.setToQuarter(3);
        } else if (month <= 12) {
            report.setToQuarter(4);
        }

        return report;
    }
    
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createReport(HttpServletRequest request, Model model) {
        int year = Integer.parseInt(request.getParameter("year"));
        Report report = getReportObject(year);
        ReportDetail reportDetail = reportService.generateDetail(report);
        report.getReportDetails().add(reportDetail);
        reportDetail.setReport(report);
        reportService.save(report);
        //reportService.saveDetail(reportDetail);
        return "redirect:/admin/report?response=true";
    }
    
    @RequestMapping(value = "show", method = RequestMethod.GET)
    public @ResponseBody String showReport(@RequestParam("year") int year) {
        Report report = getReportObject(year);
        ReportDetail reportDetail =  reportService.generateDetail(report);
        int colSize = report.getToQuarter() - report.getFromQuarter() + 2;

        String template = "<table border='1' cellspacing='0'><tr><td colspan=\"" + colSize + "\"><b>Delivery</b></td></tr>";
        String line1 = "<td>Home: </td><td>" + reportDetail.getQ1HomeDelivery() + "</td>";
        String line2 = "<td>HC: </td><td>" + reportDetail.getQ1HcDelivery() + "</td>";
        String line3 = "<td>HP: </td><td>" + reportDetail.getQ1HpDelivery() + "</td>";
        String line4 = "<td>Referred: </td><td>" + reportDetail.getQ1ReferredDelivery() + "</td>";
        String line5 = "<td>Unknown: </td><td>" + reportDetail.getQ1UnknownDelivery() + "</td>";
        String middle = "<tr><td colspan=\"" + colSize + "\"><b>Reminders</b></td></tr>";
        String line6 = "<td>First: </td><td>" + reportDetail.getQ1FirstReminder() + "</td>";
        String line7 = "<td>Second: </td><td>" + reportDetail.getQ1SecondReminder() + "</td>";
        String line8 = "<td><b>EDD: </b></td><td>" + reportDetail.getQ1Edd() + "</td>";

        for (int i = report.getFromQuarter(); i <= report.getToQuarter(); i++) {
            switch(i) {
                case 2: line1 += "<td>" + reportDetail.getQ2HomeDelivery() + "</td>";
                    line2 += "<td>" + reportDetail.getQ2HcDelivery() + "</td>";
                    line3 += "<td>" + reportDetail.getQ2HpDelivery() + "</td>";
                    line4 += "<td>" + reportDetail.getQ2ReferredDelivery() + "</td>";
                    line5 += "<td>" + reportDetail.getQ2UnknownDelivery() + "</td>";
                    line6 += "<td>" + reportDetail.getQ2FirstReminder() + "</td>";
                    line7 += "<td>" + reportDetail.getQ2SecondReminder() + "</td>";
                    line8 += "<td>" + reportDetail.getQ2Edd() + "</td>";
                    break;
                case 3: line1 += "<td>" + reportDetail.getQ3HomeDelivery() + "</td>";
                    line2 += "<td>" + reportDetail.getQ3HcDelivery() + "</td>";
                    line3 += "<td>" + reportDetail.getQ3HpDelivery() + "</td>";
                    line4 += "<td>" + reportDetail.getQ3ReferredDelivery() + "</td>";
                    line5 += "<td>" + reportDetail.getQ3UnknownDelivery() + "</td>";
                    line6 += "<td>" + reportDetail.getQ3FirstReminder() + "</td>";
                    line7 += "<td>" + reportDetail.getQ3SecondReminder() + "</td>";
                    line8 += "<td>" + reportDetail.getQ3Edd() + "</td>";
                    break;
                case 4: line1 += "<td>" + reportDetail.getQ4HomeDelivery() + "</td>";
                    line2 += "<td>" + reportDetail.getQ4HcDelivery() + "</td>";
                    line3 += "<td>" + reportDetail.getQ4HpDelivery() + "</td>";
                    line4 += "<td>" + reportDetail.getQ4ReferredDelivery() + "</td>";
                    line5 += "<td>" + reportDetail.getQ4UnknownDelivery() + "</td>";
                    line6 += "<td>" + reportDetail.getQ4FirstReminder() + "</td>";
                    line7 += "<td>" + reportDetail.getQ4SecondReminder() + "</td>";
                    line8 += "<td>" + reportDetail.getQ4Edd() + "</td>";
                    break;
            }
        }
                       
        line1 = "<tr>" + line1 + "</tr>";
        line2 = "<tr>" + line2 + "</tr>";
        line3 = "<tr>" + line3 + "</tr>";
        line4 = "<tr>" + line4 + "</tr>";
        line5 = "<tr>" + line5 + "</tr>";
        line6 = "<tr>" + line6 + "</tr>";
        line7 = "<tr>" + line7 + "</tr>";
        line8 = "<tr>" + line8 + "</tr>";

        line5 += "\n"; line6 += "\n"; line7 += "\n"; line8 += "\n";

        template += line1 + line2 + line3 + line4 + line5 + middle + line6 + line7 + line8;
        template += "</table>";

        //StringBuilder sb = new StringBuilder();
        return template;
    }
}

package org.sis.ancmessaging.server.service;

import org.sis.ancmessaging.server.domain.Report;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportService {
    public List<Report> getUnsent();
    public void update(int reportId, String status);
    public void sendReport(Report report);
    //public void sendReports();
}

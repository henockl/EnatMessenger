package org.sis.ancmessaging.server.dao;

import org.sis.ancmessaging.server.domain.Report;
import org.sis.ancmessaging.server.domain.ReportDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportDao {
    public List<Report> getUnsent();
    public void updateStatus(int reportId, String status);
    public ReportDetail generateReport(Report report);
    public ReportDetail getReportDetail(Report report);
}

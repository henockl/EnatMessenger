package org.sis.ancmessaging.dao;

import org.sis.ancmessaging.domain.Report;
import org.sis.ancmessaging.domain.ReportDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportDao {
    public List<Report> getAll();
    public void save(Report report);
    public ReportDetail generateReport(Report report);
    public void saveDetail(ReportDetail reportDetail);
}

package org.sis.ancmessaging.service;

import org.sis.ancmessaging.domain.Report;
import org.sis.ancmessaging.domain.ReportDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportService {
    public List<Report> getAll();
    public void save(Report report);
    public void saveDetail(ReportDetail reportDetail);
    public ReportDetail generateDetail(Report report);
}

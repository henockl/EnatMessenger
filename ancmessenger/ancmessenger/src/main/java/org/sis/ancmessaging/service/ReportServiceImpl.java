package org.sis.ancmessaging.service;

import org.sis.ancmessaging.dao.ReportDao;
import org.sis.ancmessaging.domain.Report;
import org.sis.ancmessaging.domain.ReportDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Report> getAll() {
        return reportDao.getAll();
    }

    @Override
    public void save(Report report) {
        reportDao.save(report);
    }

    @Override
    public void saveDetail(ReportDetail reportDetail) {
        reportDao.saveDetail(reportDetail);
    }

    @Override
    public ReportDetail generateDetail(Report report) {
        ReportDetail reportDetail = reportDao.generateReport(report);
        return reportDetail;
    }
}

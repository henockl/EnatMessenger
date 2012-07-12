package org.sis.ancmessaging.server.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;
import org.sis.ancmessaging.server.domain.Report;
import org.sis.ancmessaging.server.domain.ReportDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: Henock
 * Date: 2/8/12
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class ReportDaoImpl extends BaseDao implements ReportDao {

    
    @Override
    public List<Report> getUnsent() {
        Criteria criteria = getSession().createCriteria(Report.class)
                                        .add(Restrictions.eq("status", "PENDING"));
        return criteria.list();
    }

    @Override
    public void updateStatus(int reportId, String status) {
        Report oldReport = (Report) getSession().get(Report.class, reportId);
        if (oldReport != null) {
            oldReport.setStatus(status);
        }
        getSession().update(oldReport);
    }

    @Override
    public ReportDetail generateReport(Report report) {
        DateTime begin, end;
        ReportDetail reportDetail = new ReportDetail();
        for (int i = report.getFromQuarter(); i <= report.getToQuarter(); i++) {
            int endMonth = i * 3;
            int beginMonth = endMonth - 2;
            int endDate = 31;
            int year = report.getYear();
            if (endMonth == 2 || endMonth == 3) {
                endDate = 30;
            }
            begin = new DateTime(year, beginMonth, 1,0, 0);
            end = new DateTime(year, endMonth, endDate, 0, 0);
            long count = (Long) getSession().createCriteria(Reminder.class)
                    .add(Restrictions.between("reminderSentOn", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("reminderType", "MONTHLY"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            int c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1FirstReminder(c);
                        break;
                case 2: reportDetail.setQ2FirstReminder(c);
                        break;
                case 3: reportDetail.setQ3FirstReminder(c);
                        break;
                case 4: reportDetail.setQ4FirstReminder(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Reminder.class)
                    .add(Restrictions.between("reminderSentOn", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("reminderType", "WEEKLY"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1SecondReminder(c);
                        break;
                case 2: reportDetail.setQ2SecondReminder(c);
                        break;
                case 3: reportDetail.setQ3SecondReminder(c);
                        break;
                case 4: reportDetail.setQ4SecondReminder(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("outcome", "Delivered at Home"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1HomeDelivery(c);
                        break;
                case 2: reportDetail.setQ2HomeDelivery(c);
                        break;
                case 3: reportDetail.setQ3HomeDelivery(c);
                        break;
                case 4: reportDetail.setQ4HomeDelivery(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("outcome", "Delivered at HP"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1HpDelivery(c);
                        break;
                case 2: reportDetail.setQ2HpDelivery(c);
                        break;
                case 3: reportDetail.setQ3HpDelivery(c);
                        break;
                case 4: reportDetail.setQ4HpDelivery(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("outcome", "Delivered at HC"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1HcDelivery(c);
                        break;
                case 2: reportDetail.setQ2HcDelivery(c);
                        break;
                case 3: reportDetail.setQ3HcDelivery(c);
                        break;
                case 4: reportDetail.setQ4HcDelivery(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("outcome", "Referred"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1ReferredDelivery(c);
                        break;
                case 2: reportDetail.setQ2ReferredDelivery(c);
                        break;
                case 3: reportDetail.setQ3ReferredDelivery(c);
                        break;
                case 4: reportDetail.setQ4ReferredDelivery(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
                    .add(Restrictions.eq("outcome", "Unknown"))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1UnknownDelivery(c);
                        break;
                case 2: reportDetail.setQ2UnknownDelivery(c);
                        break;
                case 3: reportDetail.setQ3UnknownDelivery(c);
                        break;
                case 4: reportDetail.setQ4UnknownDelivery(c);
                        break;
            }

            count = (Long) getSession().createCriteria(Mother.class)
                    .add(Restrictions.between("edd", begin.toDate(), end.toDate()))
                    .setProjection(Projections.rowCount()).uniqueResult();
            c = (int)count;
            switch(i) {
                case 1: reportDetail.setQ1Edd(c);
                    break;
                case 2: reportDetail.setQ2Edd(c);
                    break;
                case 3: reportDetail.setQ3Edd(c);
                    break;
                case 4: reportDetail.setQ4Edd(c);
                    break;
            }
        }
        
        return reportDetail;
    }

    @Override
    public ReportDetail getReportDetail(Report report) {
        Criteria criteria = getSession().createCriteria(ReportDetail.class)
                                        .add(Restrictions.eq("report", report));
        return (ReportDetail) criteria.uniqueResult();
    }
}

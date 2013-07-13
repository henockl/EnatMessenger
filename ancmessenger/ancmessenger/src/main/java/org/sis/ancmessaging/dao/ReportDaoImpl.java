package org.sis.ancmessaging.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.sis.ancmessaging.domain.Mother;
import org.sis.ancmessaging.domain.Reminder;
import org.sis.ancmessaging.domain.Report;
import org.sis.ancmessaging.domain.ReportDetail;
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
  public List<Report> getAll() {
    Criteria criteria = getSession().createCriteria(Report.class)
        .addOrder(Order.desc("status"));
    return criteria.list();
  }

  @Override
  public void save(Report report) {
    getSession().save(report);
  }

  private int getTotalDaysOfMonth(int year, int month) {
    DateTime dateTime = new DateTime(year, month, 14, 12, 0, 0, 000);
    return dateTime.dayOfMonth().getMaximumValue();
  }

  @Override
  public ReportDetail generateReport(Report report) {
    DateTime begin, end;
    ReportDetail reportDetail = new ReportDetail();
    for (int i = report.getFromQuarter(); i <= report.getToQuarter(); i++) {
      int endMonth = i * 3;
      int beginMonth = endMonth - 2;
      int year = report.getYear();
      int endDate = getTotalDaysOfMonth(year, endMonth);

      begin = new DateTime(year, beginMonth, 1, 0, 0);
      end = new DateTime(year, endMonth, endDate, 0, 0);

      long count = (Long) getSession().createCriteria(Reminder.class)
          .add(Restrictions.between("reminderSentOn", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("reminderType", "MONTHLY"))
          .setProjection(Projections.rowCount()).uniqueResult();
      int c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1FirstReminder(c);
          break;
        case 2:
          reportDetail.setQ2FirstReminder(c);
          break;
        case 3:
          reportDetail.setQ3FirstReminder(c);
          break;
        case 4:
          reportDetail.setQ4FirstReminder(c);
          break;
      }

      count = (Long) getSession().createCriteria(Reminder.class)
          .add(Restrictions.between("reminderSentOn", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("reminderType", "WEEKLY"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1SecondReminder(c);
          break;
        case 2:
          reportDetail.setQ2SecondReminder(c);
          break;
        case 3:
          reportDetail.setQ3SecondReminder(c);
          break;
        case 4:
          reportDetail.setQ4SecondReminder(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("outcome", "Delivered at Home"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1HomeDelivery(c);
          break;
        case 2:
          reportDetail.setQ2HomeDelivery(c);
          break;
        case 3:
          reportDetail.setQ3HomeDelivery(c);
          break;
        case 4:
          reportDetail.setQ4HomeDelivery(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("outcome", "Delivered at HP"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1HpDelivery(c);
          break;
        case 2:
          reportDetail.setQ2HpDelivery(c);
          break;
        case 3:
          reportDetail.setQ3HpDelivery(c);
          break;
        case 4:
          reportDetail.setQ4HpDelivery(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("outcome", "Delivered at HC"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1HcDelivery(c);
          break;
        case 2:
          reportDetail.setQ2HcDelivery(c);
          break;
        case 3:
          reportDetail.setQ3HcDelivery(c);
          break;
        case 4:
          reportDetail.setQ4HcDelivery(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("outcome", "Referred"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1ReferredDelivery(c);
          break;
        case 2:
          reportDetail.setQ2ReferredDelivery(c);
          break;
        case 3:
          reportDetail.setQ3ReferredDelivery(c);
          break;
        case 4:
          reportDetail.setQ4ReferredDelivery(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("arrivalTime", begin.toDate(), end.toDate()))
          .add(Restrictions.eq("outcome", "Unknown"))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1UnknownDelivery(c);
          break;
        case 2:
          reportDetail.setQ2UnknownDelivery(c);
          break;
        case 3:
          reportDetail.setQ3UnknownDelivery(c);
          break;
        case 4:
          reportDetail.setQ4UnknownDelivery(c);
          break;
      }

      count = (Long) getSession().createCriteria(Mother.class)
          .add(Restrictions.between("edd", begin.toDate(), end.toDate()))
          .setProjection(Projections.rowCount()).uniqueResult();
      c = (int) count;
      switch (i) {
        case 1:
          reportDetail.setQ1Edd(c);
          break;
        case 2:
          reportDetail.setQ2Edd(c);
          break;
        case 3:
          reportDetail.setQ3Edd(c);
          break;
        case 4:
          reportDetail.setQ4Edd(c);
          break;
      }
    }

    return reportDetail;
  }

  @Override
  public void saveDetail(ReportDetail reportDetail) {
    getSession().save(reportDetail);
  }


}

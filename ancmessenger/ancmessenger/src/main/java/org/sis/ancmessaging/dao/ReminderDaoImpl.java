package org.sis.ancmessaging.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.sis.ancmessaging.domain.Mother;
import org.sis.ancmessaging.domain.Reminder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ReminderDaoImpl extends BaseDao implements ReminderDao {

	@Override
	public Reminder getRecentReminder(Mother mother) {
		Criteria c = getSession().createCriteria(Reminder.class);
		c.add(Restrictions.eq("mother", mother)).addOrder(Order.desc("sentOn"))
		 .setMaxResults(1);
		return (Reminder) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reminder> getMonthlyRemindersBackdated(int minDays, int maxDays) {
		DateTime now = new DateTime();
		DateTime min = now.minusDays(maxDays);
		DateTime max = now.minusDays(minDays);
		Criteria criteria = getSession().createCriteria(Reminder.class);
		criteria.add(Restrictions.eq("reminderType", "MONTHLY"))
				.add(Restrictions.between("sentOn", min.toDate(), max.toDate()))
				.add(Restrictions.isNull("confirmation"))
				.addOrder(Order.asc("sentOn"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reminder> getWeeklyRemindersBackdated(int minDays, int maxDays) {
		DateTime now = new DateTime();
		DateTime min = now.minusDays(maxDays);
		DateTime max = now.minusDays(minDays);
		Criteria criteria = getSession().createCriteria(Reminder.class);
		criteria.add(Restrictions.eq("reminderType", "WEEKLY"))
				.add(Restrictions.between("sentOn", min.toDate(), max.toDate()))
				.add(Restrictions.isNull("confirmation"))
				.addOrder(Order.asc("sentOn"));
		return criteria.list();
	}

}

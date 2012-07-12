package org.sis.ancmessaging.server.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReminderDaoImpl extends BaseDao implements ReminderDao {

	@Override
	public void save(Reminder reminder) {
		getSession().save(reminder);
	}

	@Override
	public void update(Reminder reminder) {
		getSession().saveOrUpdate(reminder);
		
	}

    @Override
    public boolean hasMonthlyReminder(Mother mother) {
        Criteria criteria = getSession().createCriteria(Reminder.class)
                .add(Restrictions.eq("mother", mother))
                .add(Restrictions.eq("reminderType", "MONTHLY"));
        Reminder reminder = null;
        try {
            reminder = (Reminder) criteria.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        boolean t = reminder != null;
        return t;
        //return (reminder != null);
    }

    @Override
    public boolean hasWeeklyReminder(Mother mother) {
        Criteria criteria = getSession().createCriteria(Reminder.class)
                .add(Restrictions.eq("mother", mother))
                .add(Restrictions.eq("reminderType", "WEEKLY"));
        Reminder reminder = null;
        try {
             reminder = (Reminder) criteria.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (reminder != null);
    }

    @Override
    public Reminder getLatestReminder(Mother mother) {
        Criteria criteria = getSession().createCriteria(Reminder.class)
                                        .add(Restrictions.eq("mother", mother))
                                        .addOrder(Order.desc("reminderSentOn"));
        criteria.setMaxResults(1);
        return (Reminder) criteria.uniqueResult();
    }

}

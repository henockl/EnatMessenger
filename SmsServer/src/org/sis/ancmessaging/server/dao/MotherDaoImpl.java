package org.sis.ancmessaging.server.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository
@Transactional
public class MotherDaoImpl extends BaseDao implements MotherDao {

	@SuppressWarnings("unchecked")
	@Override
	public Set<Mother> getMothersWithEddInRange(Date min, Date max) {
		Criteria c = getSession().createCriteria(Mother.class);
		c.add(Restrictions.between("edd", min, max));
        return new HashSet<Mother>(c.list());
	}

	@Override
	public Mother getMother(long seqId) {
		return (Mother)getSession().get(Mother.class, seqId);
	}

    @Override
    public boolean hasMonthlyReminder(Mother mother) {
        Criteria c = getSession().createCriteria(Reminder.class)
                                 .add(Restrictions.eq("mother", mother))
                                 .add(Restrictions.eq("reminderType", "MONTHLY"));
        return (c.list().size() > 0);
    }

    @Override
    public boolean hasWeeklyReminder(Mother mother) {
        Criteria c = getSession().createCriteria(Reminder.class)
                .add(Restrictions.eq("mother", mother))
                .add(Restrictions.eq("reminderType", "WEEKLY"));
        return (c.list().size() > 0);
    }

}

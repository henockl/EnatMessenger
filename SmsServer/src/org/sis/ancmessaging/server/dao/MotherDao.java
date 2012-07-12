package org.sis.ancmessaging.server.dao;

import java.util.Date;
import java.util.Set;

import org.sis.ancmessaging.server.domain.Mother;

public interface MotherDao {
	public Set<Mother> getMothersWithEddInRange(Date min, Date max);
	public Mother getMother(long seqId);

    public boolean hasMonthlyReminder(Mother mother);
    public boolean hasWeeklyReminder(Mother mother);
}

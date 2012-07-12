package org.sis.ancmessaging.server.dao;

import org.sis.ancmessaging.server.domain.Mother;
import org.sis.ancmessaging.server.domain.Reminder;

public interface ReminderDao {
	public void save(Reminder reminder);
	public void update(Reminder reminder);
    public boolean hasMonthlyReminder(Mother mother);
    public boolean hasWeeklyReminder(Mother mother);
    public Reminder getLatestReminder(Mother mother);
}
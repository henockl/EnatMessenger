package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.Mother;
import org.sis.ancmessaging.domain.Reminder;

public interface ReminderDao {
	public Reminder getRecentReminder(Mother mother);
	public List<Reminder> getMonthlyRemindersBackdated(int minDays, int maxDays);
	public List<Reminder> getWeeklyRemindersBackdated(int minDays, int maxDays);
}
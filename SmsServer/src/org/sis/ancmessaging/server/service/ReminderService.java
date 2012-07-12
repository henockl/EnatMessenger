package org.sis.ancmessaging.server.service;

import org.sis.ancmessaging.server.domain.Mother;

import java.util.Set;

public interface ReminderService {
	public Set<Mother> getMothersToRemindOnMonth();
	public Set<Mother> getMothersToRemindOnWeek();
	public void sendReminder(Mother mother, String type);
}

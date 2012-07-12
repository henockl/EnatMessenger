package org.sis.ancmessaging.server.dao;

import org.sis.ancmessaging.server.domain.Emergency;

public interface EmergencyDao {
	public void saveEmergency(Emergency emergency);
	public String getEmergencyPhone();
}

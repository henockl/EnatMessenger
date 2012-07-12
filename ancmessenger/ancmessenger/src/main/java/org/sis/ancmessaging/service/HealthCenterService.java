package org.sis.ancmessaging.service;

import java.util.List;

import org.sis.ancmessaging.domain.HealthCenter;
import org.sis.ancmessaging.domain.HealthPost;

public interface HealthCenterService {
	public boolean persist(HealthCenter healthCenter);
	public void addHpToHc(HealthCenter hc, HealthPost hp);
	public HealthCenter findById(int centerId);
	public List<HealthCenter> getAllHealthCenters();
}

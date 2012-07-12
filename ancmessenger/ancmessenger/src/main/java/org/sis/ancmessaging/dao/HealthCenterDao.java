package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.HealthCenter;

public interface HealthCenterDao {
	public void save(HealthCenter healthCenter);
	public void update(HealthCenter healthCenter);
	public HealthCenter getById(int centerId);
	public List<HealthCenter> getByQuery(String query);
}

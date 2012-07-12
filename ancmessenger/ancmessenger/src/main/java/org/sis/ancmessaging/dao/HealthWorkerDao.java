package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.HealthExtensionWorker;
import org.sis.ancmessaging.domain.Mother;

public interface HealthWorkerDao {
	public void save(HealthExtensionWorker healthWorker);
	public void update(HealthExtensionWorker healthWorker);
	public HealthExtensionWorker getById(int workerId);
    public List<HealthExtensionWorker> getPaginatedList(int postId, int rows, int page, StringBuilder sb);
}

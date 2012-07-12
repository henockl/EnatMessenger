package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.HealthPost;

public interface HealthPostDao {
	public void save(HealthPost healthPost);
	public void update(HealthPost healthPost);
	public HealthPost getById(int postId);
	public List<HealthPost> getByQuery(String query);
	public List<HealthPost> getPaginatedList(int centerId, int rows, int page, StringBuilder sb);
}

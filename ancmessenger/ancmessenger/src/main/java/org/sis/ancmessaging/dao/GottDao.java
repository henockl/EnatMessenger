package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.Gott;

public interface GottDao {
	public void save(Gott gott);
    public Gott getById(int id);
	public void update(Gott	gott);
	public List<Gott> getPaginatedList(int postId, int rows, int page, StringBuilder sb);
	public List<Gott> getAllGottsForHealthPost(int postId);
}

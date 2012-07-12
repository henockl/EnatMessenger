package org.sis.ancmessaging.dao;

import java.util.List;

import org.sis.ancmessaging.domain.Transporter;

public interface TransporterDao {
	public void save(Transporter transporter);
	public void update(Transporter transporter);
	public Transporter getById(int id);
	public List<Transporter> getPaginatedList(int postId, int rows, int page, StringBuilder sb);
}

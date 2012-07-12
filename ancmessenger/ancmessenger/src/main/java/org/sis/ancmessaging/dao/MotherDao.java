package org.sis.ancmessaging.dao;

import java.util.List;
import java.util.Map;

import org.sis.ancmessaging.domain.Mother;

public interface MotherDao {
	public void save(Mother mother);
	public void update(Mother mother);
	public Mother getById(int seqId);
    public boolean motherExists(String motherId);
	public List<Mother> getByQuery(String query);

    public List<Mother> getAllMothers(String status, String time, int rows, int page, Map<String, String> sb);
    public List<Mother> getMothersByForHealthPost(int postId, int workerId, String status, String time,
                                                  int rows, int page, Map<String, String> sb);
}

package org.sis.ancmessaging.service;

import java.util.List;
import java.util.Map;

import org.sis.ancmessaging.domain.Mother;
import org.sis.ancmessaging.dto.MotherDTO;

public interface MotherService {
	public boolean persist(Mother mother);
	public boolean update(Mother mother);
	public Mother findById(int seqId);
    public boolean motherExists(String motherId);
    public List<Mother> getMothersByCriteria(int postId, int workerId, String status, String time, int rows, 
                                             int page, Map<String, String> sb);
    public boolean motherExistsInList(List<MotherDTO> mothers, Mother mother);
}

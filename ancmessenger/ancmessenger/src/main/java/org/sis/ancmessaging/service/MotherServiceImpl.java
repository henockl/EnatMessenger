package org.sis.ancmessaging.service;

import java.util.List;
import java.util.Map;

import org.sis.ancmessaging.dao.MotherDao;
import org.sis.ancmessaging.dao.ReminderDao;
import org.sis.ancmessaging.domain.Mother;
import org.sis.ancmessaging.dto.MotherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotherServiceImpl implements MotherService { // NO_UCD
	
	@Autowired
	private MotherDao motherDao;
	
	@Autowired 
	private ReminderDao reminderDao;
	
	@Override
	public boolean persist(Mother mother) {
		try {
			Mother existingMother = motherDao.getById(mother.getSeqId());
			if (existingMother != null) {
				existingMother.setMotherId(mother.getMotherId());
				existingMother.setFullName(mother.getFullName());
				existingMother.setAge(mother.getAge());
				existingMother.setLmp(mother.getLmp());
				existingMother.setGott(mother.getGott());
				existingMother.setEdd(mother.getEdd());
				existingMother.setHealthWorker(mother.getHealthWorker());

				motherDao.update(mother);
			} else {
				motherDao.save(mother);
			}
			return true;
		} catch (Exception ex) {
			return false;
		}

	}

	@Override
	public Mother findById(int seqId) {
		return motherDao.getById(seqId);
	}

    @Override
    public boolean motherExists(String motherId) {
        return motherDao.motherExists(motherId);
    }

    @Override
	public List<Mother> getMothersByCriteria(int postId, int workerId, String status, String time,
			int rows, int page, Map<String, String> sb) {
		//List<Mother> mothers = null;
		if (postId == 0) {
			return motherDao.getAllMothers(status, time, rows, page, sb);
		}
		else {
			return motherDao.getMothersByForHealthPost(postId, workerId, status, time, rows, page, sb);
		}
	}

    @Override
    public boolean motherExistsInList(List<MotherDTO> mothers, Mother mother) {
        boolean result = false;
        for (MotherDTO dto : mothers) {
            if (dto.getSeqId() == mother.getSeqId()) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
	public boolean update(Mother mother) {
		try {
			motherDao.update(mother);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}

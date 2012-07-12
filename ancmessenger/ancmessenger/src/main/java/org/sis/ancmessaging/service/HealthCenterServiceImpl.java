package org.sis.ancmessaging.service;

import java.util.List;

import org.sis.ancmessaging.dao.HealthCenterDao;
import org.sis.ancmessaging.dao.HealthPostDao;
import org.sis.ancmessaging.domain.HealthCenter;
import org.sis.ancmessaging.domain.HealthPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCenterServiceImpl implements HealthCenterService {
	@Autowired
	private HealthCenterDao healthCenterDao;
	
	@Autowired
	private HealthPostDao healthPostDao;
	
	@Override
	public boolean persist(HealthCenter healthCenter) {
		try {
			if (healthCenter.getCenterId() == 0) {
				healthCenterDao.save(healthCenter);
			} else {
				HealthCenter existingCenter = healthCenterDao.getById(healthCenter.getCenterId());
				existingCenter.setCenterName(healthCenter.getCenterName());
				existingCenter.setCenterPhone(healthCenter.getCenterPhone());
				healthCenterDao.update(existingCenter); 
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	@Override
	public void addHpToHc(HealthCenter hc, HealthPost hp) {
		if (hp.getPostId() == 0) {
			healthPostDao.save(hp);
		}
		hp.setHealthCenter(hc);
		hc.getHealthPosts().add(hp);
		healthPostDao.update(hp);
	}
	
	@Override
	public List<HealthCenter> getAllHealthCenters() {
		String query = "from HealthCenter";
		return healthCenterDao.getByQuery(query);
	}

	@Override
	public HealthCenter findById(int facilityId) {
		return healthCenterDao.getById(facilityId);
	}

}

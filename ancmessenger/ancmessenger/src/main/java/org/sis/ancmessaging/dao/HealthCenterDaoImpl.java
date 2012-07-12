package org.sis.ancmessaging.dao;

import java.util.List;

import org.hibernate.Query;
import org.sis.ancmessaging.domain.HealthCenter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HealthCenterDaoImpl extends BaseDao implements HealthCenterDao {

	@Override
	public void save(HealthCenter healthCenter) {
		getSession().save(healthCenter);
	}
	
	@Override
	public void update(HealthCenter healthCenter) {
		getSession().update(healthCenter);
		getSession().flush();
	}

	@Override
	public HealthCenter getById(int centerId) {
		return (HealthCenter) getSession().get(HealthCenter.class, centerId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HealthCenter> getByQuery(String query) {
		Query q = getSession().createQuery(query);
		return q.list();
	}

}

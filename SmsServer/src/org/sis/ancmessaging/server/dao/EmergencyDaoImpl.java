package org.sis.ancmessaging.server.dao;

import org.hibernate.Query;
import org.sis.ancmessaging.server.domain.Emergency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EmergencyDaoImpl extends BaseDao implements EmergencyDao {

	@Override
	public void saveEmergency(Emergency emergency) {
		getSession().save(emergency);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEmergencyPhone() {
		String q = "SELECT CenterPhone FROM HealthCenter LIMIT 1";
		Query query = getSession().createSQLQuery(q);
		return query.uniqueResult().toString();
	}

}

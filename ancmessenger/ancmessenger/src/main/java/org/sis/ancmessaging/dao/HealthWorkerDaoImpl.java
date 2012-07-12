package org.sis.ancmessaging.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.HealthExtensionWorker;
import org.sis.ancmessaging.domain.HealthPost;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HealthWorkerDaoImpl extends BaseDao implements HealthWorkerDao {

	@Override
	public HealthExtensionWorker getById(int workerId) {
		return (HealthExtensionWorker) getSession().get(HealthExtensionWorker.class, workerId);
	}

	@Override
	public void save(HealthExtensionWorker healthWorker) {
		getSession().save(healthWorker);
	}

	@Override
	public void update(HealthExtensionWorker healthWorker) {
		getSession().update(healthWorker);
		getSession().flush();
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<HealthExtensionWorker> getPaginatedList(int postId, int rows, int page, StringBuilder sb) {
		HealthPost healthPost = (HealthPost) getSession().createCriteria(HealthPost.class)
															 .add(Restrictions.eq("postId", postId))
															 .uniqueResult();
		int total = healthPost.getHealthWorkers().size();
		int totalPages;
		if (total <= rows) {
			totalPages = 1;
		} else {
			totalPages = total / rows;
			if (total % rows > 0) {
				totalPages += 1;
			}
		}
		sb.append(totalPages);
		Criteria criteria = getSession().createCriteria(HealthExtensionWorker.class)
										.add(Restrictions.eq("healthPost", healthPost))
										.addOrder(Order.asc("fullName"));
		
		criteria.setMaxResults(rows);
		int start = (page - 1);
		if (start > 0) {
			start = start + rows;
		}
		criteria.setFirstResult(start);
		return criteria.list();
	}

}

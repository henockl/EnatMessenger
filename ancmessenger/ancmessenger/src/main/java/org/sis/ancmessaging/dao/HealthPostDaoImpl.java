package org.sis.ancmessaging.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.HealthCenter;
import org.sis.ancmessaging.domain.HealthPost;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HealthPostDaoImpl extends BaseDao implements HealthPostDao {

	@Override
	public void save(HealthPost healthPost) {
		getSession().saveOrUpdate(healthPost);
	}

	@Override
	public void update(HealthPost healthPost) {
		getSession().update(healthPost);
		getSession().flush();
	}

	@Override
	public HealthPost getById(int postId) {
		return (HealthPost) getSession().get(HealthPost.class, postId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HealthPost> getByQuery(String query) {
		Query q = getSession().createQuery(query);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HealthPost> getPaginatedList(int centerId, int rows, int page, StringBuilder sb) {
		HealthCenter healthCenter = (HealthCenter) getSession().createCriteria(HealthCenter.class)
															 .add(Restrictions.eq("centerId", centerId))
															 .uniqueResult();
		int total = healthCenter.getHealthPosts().size();
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
		Criteria criteria = getSession().createCriteria(HealthPost.class)
										.add(Restrictions.eq("healthCenter", healthCenter))
										.addOrder(Order.asc("postName"));
		
		criteria.setMaxResults(rows);
		int start = (page - 1) * rows;
		criteria.setFirstResult(start);
		return criteria.list();
	}
	
}

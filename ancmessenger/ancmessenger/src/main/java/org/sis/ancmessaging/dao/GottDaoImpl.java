package org.sis.ancmessaging.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.Gott;
import org.sis.ancmessaging.domain.HealthPost;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GottDaoImpl extends BaseDao implements GottDao {

	@Override
	public void save(Gott gott) {
		getSession().save(gott);

	}

    @Override
	public Gott getById(int id) {
		return (Gott) getSession().get(Gott.class, id);
	}

	@Override
	public void update(Gott gott) {
		getSession().update(gott);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gott> getPaginatedList(int postId, int rows, int page, StringBuilder sb) {
		HealthPost healthPost = (HealthPost) getSession().createCriteria(HealthPost.class)
															 .add(Restrictions.eq("postId", postId))
															 .uniqueResult();
		int total = healthPost.getGotts().size();
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
		Criteria criteria = getSession().createCriteria(Gott.class)
										.add(Restrictions.eq("healthPost", healthPost))
										.addOrder(Order.asc("gottName"));
		criteria.setMaxResults(rows);
		int start = (page - 1) * rows;
		
		criteria.setFirstResult(start);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gott> getAllGottsForHealthPost(int postId) {
		HealthPost healthPost = (HealthPost) getSession().createCriteria(HealthPost.class)
														.add(Restrictions.eq("postId", postId))
														.uniqueResult();
		Criteria criteria = getSession().createCriteria(Gott.class)
										.add(Restrictions.eq("healthPost", healthPost))
										.addOrder(Order.asc("gottName"));
		return criteria.list();
	}
}

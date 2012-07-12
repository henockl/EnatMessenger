package org.sis.ancmessaging.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.HealthPost;
import org.sis.ancmessaging.domain.Transporter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TransporterDaoImpl extends BaseDao implements TransporterDao {

	@Override
	public Transporter getById(int id) {
		return (Transporter) getSession().get(Transporter.class, id);
	}

	@Override
	public void save(Transporter transporter) {
		getSession().save(transporter);
	}
	
	public void update(Transporter transporter) {
		getSession().update(transporter);
		getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Transporter> getPaginatedList(int postId, int rows, int page, StringBuilder sb) {
		HealthPost healthPost = (HealthPost) getSession().createCriteria(HealthPost.class)
															 .add(Restrictions.eq("postId", postId))
															 .uniqueResult();
		int total = healthPost.getTransporters().size();
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
		Criteria criteria = getSession().createCriteria(Transporter.class)
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

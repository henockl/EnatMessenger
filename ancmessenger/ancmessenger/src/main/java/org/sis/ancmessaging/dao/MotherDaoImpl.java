package org.sis.ancmessaging.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.Mother;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MotherDaoImpl extends BaseDao implements MotherDao {
	
	@Override
	public void save(Mother mother) {
		getSession().save(mother);
	}

	@Override
	public void update(Mother mother) {
		getSession().update(mother);
		getSession().flush();
	}

	@Override
	public Mother getById(int seqId) {
		Query query = getSession().createQuery("from Mother m where m.seqId = " + seqId);
		return (Mother) query.uniqueResult();
	}

    @Override
    public boolean motherExists(String motherId) {
        Criteria criteria = getSession().createCriteria(Mother.class)
                .add(Restrictions.eq("motherId", motherId));
        Mother mother = (Mother) criteria.uniqueResult();
        return (mother != null);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Mother> getByQuery(String query) {
		Query q = getSession().createQuery(query);
		return q.list();
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Mother> getAllMothers(String status, String time, int rows, int page, Map<String, String> sb) {
        /*
        String hql = "select m from Mother m left outer join m.reminders mr ";
        if (!status.equals("ALL")) {
            hql += "where mr.status=:status";

        }
        if (!time.equals("ALL")) {
            if (hql.contains("where")) {
                hql += " and mr.reminderType=:time";
            } else {
                hql += "where mr.reminderType=:time";
            }
        }
        hql += " order by mr.status desc, mr.reminderSentOn desc";
        Query query = getSession().createQuery(hql);

        if (!status.equals("ALL")) {
            query.setString("status", status);
        }
        if (!time.equals("ALL")) {
            query.setString("time", time);
        }
        List<Mother> mothers =  query.list();

        int total = mothers.size();


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

        query.setMaxResults(rows);
        int start = (page - 1) * rows;
        query.setFirstResult(start);

        return query.list();

        */
        String hql = "select m from Mother m ";
        if (!time.equals("WEEKLY")) {
            hql += "left outer join m.reminders mr1 with mr1.reminderType = 'MONTHLY' ";
        }
        if (!time.equals("MONTHLY")) {
            hql += "left outer join m.reminders mr2 with mr2.reminderType = 'WEEKLY' ";
        }
        
        if (!status.equals("ALL")) {
            if (time.equals("MONTHLY")) {
                hql += "where mr1.status=:status ";
            } else if (time.equals("WEEKLY")) {
                hql += "where mr2.status=:status ";
            } else {
                hql += "where mr1.status=:status or mr2.status=:status ";
            }
        }

        hql += "order by mr1.status desc, mr2.status desc, mr2.reminderSentOn desc";
        Query query = getSession().createQuery(hql);

        if (!status.equals("ALL")) {
            query.setString("status", status);
        }
        if (!time.equals("ALL")) {
            query.setString("time", time);
        }
        List<Mother> mothers =  query.list();

        int total = mothers.size();


        int totalPages;
        if (total <= rows) {
            totalPages = 1;
        } else {
            totalPages = total / rows;
            if (total % rows > 0) {
                totalPages += 1;
            }
        }
        sb.put("totalPages", String.valueOf(totalPages));
        sb.put("records", String.valueOf(total));
        query.setMaxResults(rows);
        int start = (page - 1) * rows;
        query.setFirstResult(start);

        return query.list();

    }

    @Override
    public List<Mother> getMothersByForHealthPost(int postId, int workerId, String status, String time, int rows, 
                                                  int page, Map<String, String> sb) {
        /*
        String hql = "select m from Mother m left outer join m.reminders mr ";
        if (workerId == 0) {
            //HealthPost healthPost = (HealthPost) getSession().get(HealthPost.class, postId);
            hql += "where m.healthWorker.healthPost.postId=:postId";
        } else {
            //HealthExtensionWorker healthWorker = (HealthExtensionWorker) getSession().get(
            //        HealthExtensionWorker.class, workerId);
            hql += "where m.healthWorker.workerId=:workerId";
        }

        if (!status.equals("ALL")) {
            hql += " and mr.status=:status";
        }
        if (!time.equals("ALL")) {
            hql += " and mr.reminderType=:time";
        }
        hql += " order by mr.status desc, mr.reminderSentOn desc";
        Query query = getSession().createQuery(hql);
        if (workerId == 0) {
            query.setParameter("postId", postId);
        } else {
            query.setParameter("workerId", workerId);
        }

        if (!status.equals("ALL")) {
            query.setParameter("status", status);
        }

        if (!time.equals("ALL")) {
            query.setParameter("time", time);
        }

        List<Mother> mothers =  query.list();

        int total = mothers.size();


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

        query.setMaxResults(rows);
        int start = (page - 1) * rows;
        query.setFirstResult(start);

        return query.list();
        */

        String hql = "select m from Mother m ";
        if (!time.equals("WEEKLY")) {
            hql += "left outer join m.reminders mr1 with mr1.reminderType = 'MONTHLY' ";
        }
        if (!time.equals("MONTHLY")) {
            hql += "left outer join m.reminders mr2 with mr2.reminderType = 'WEEKLY' ";
        }

        if (!status.equals("ALL")) {
            if (time.equals("MONTHLY")) {
                hql += "where mr1.status=:status ";
            } else if (time.equals("WEEKLY")) {
                hql += "where mr2.status=:status ";
            } else {
                hql += "where mr1.status=:status or mr2.status=:status ";
            }
        }
        if (workerId == 0) {
            //HealthPost healthPost = (HealthPost) getSession().get(HealthPost.class, postId);
            hql += "where m.healthWorker.healthPost.postId=:postId";
        } else {
            //HealthExtensionWorker healthWorker = (HealthExtensionWorker) getSession().get(
            //        HealthExtensionWorker.class, workerId);
            hql += "where m.healthWorker.workerId=:workerId";
        }


        hql += " order by mr1.status desc, mr2.status desc, mr2.reminderSentOn desc";
        Query query = getSession().createQuery(hql);
        if (workerId == 0) {
            query.setParameter("postId", postId);
        } else {
            query.setParameter("workerId", workerId);
        }

        if (!status.equals("ALL")) {
            query.setParameter("status", status);
        }

        if (!time.equals("ALL")) {
            query.setParameter("time", time);
        }

        List<Mother> mothers =  query.list();

        int total = mothers.size();


        int totalPages;
        if (total <= rows) {
            totalPages = 1;
        } else {
            totalPages = total / rows;
            if (total % rows > 0) {
                totalPages += 1;
            }
        }
        sb.put("totalPages", String.valueOf(totalPages));
        sb.put("records", String.valueOf(total));
        query.setMaxResults(rows);
        int start = (page - 1) * rows;
        query.setFirstResult(start);

        return query.list();
    }
}
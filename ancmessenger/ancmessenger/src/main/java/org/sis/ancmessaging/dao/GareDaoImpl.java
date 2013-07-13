package org.sis.ancmessaging.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.sis.ancmessaging.domain.Gare;
import org.sis.ancmessaging.domain.Gott;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: henock
 * Date: 2/13/13
 * Time: 6:04 AM
 * To change this template use File | Settings | File Templates.
 */
@Repository
@Transactional
public class GareDaoImpl extends BaseDao implements GareDao {

  @Override
  public void save(Gare gare) {
    getSession().save(gare);
  }

  @Override
  public Gare getById(int id) {
    return (Gare) getSession().get(Gare.class, id);
  }

  @Override
  public void update(Gare gare) {
    getSession().update(gare);
  }

  @Override
  public List<Gare> getPaginatedList(int gottId, int rows, int page, StringBuilder sb) {
    Gott gott = (Gott) getSession().createCriteria(Gott.class)
        .add(Restrictions.eq("gottId", gottId))
        .uniqueResult();
    int total = gott.getGares().size();
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
    Criteria criteria = getSession().createCriteria(Gare.class)
        .add(Restrictions.eq("gott", gott))
        .addOrder(Order.asc("gareName"));
    criteria.setMaxResults(rows);
    int start = (page - 1) * rows;

    criteria.setFirstResult(start);
    return criteria.list();
  }

  @Override
  public List<Gare> getAllGaresForGott(int gottId) {
    Gott gott = (Gott) getSession().createCriteria(Gott.class)
        .add(Restrictions.eq("gottId", gottId))
        .uniqueResult();
    Criteria criteria = getSession().createCriteria(Gare.class)
        .add(Restrictions.eq("gott", gott))
        .addOrder(Order.asc("gareName"));
    return criteria.list();
  }
}

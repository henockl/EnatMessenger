package org.sis.ancmessaging.server.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	@Autowired
	protected SessionFactory sessionFactory;
	
	public Session getSession( ) {
		return sessionFactory.getCurrentSession();
	}
}

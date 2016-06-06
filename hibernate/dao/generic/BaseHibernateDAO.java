package com.csc.mail.jsh.db.hibernate.dao.generic;

import org.hibernate.Session;

import com.csc.mail.jsh.db.hibernate.core.HibernateSessionFactory;

public class BaseHibernateDAO {
	
	public Session getSession() {
		return HibernateSessionFactory.getSession();
	}
	
	public void closeSession()
	{
		HibernateSessionFactory.closeSession();
	}

}

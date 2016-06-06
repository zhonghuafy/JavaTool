/**
 * 
 */
package casco.com.tse.hibernate.dao.core;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

import org.hibernate.Session;

import casco.com.tse.hibernate.factory.HibernateSessionFactory;

/**
 * dao calls procedures
 * @author 60874
 *
 */
public class ProcedureDao {

	@SuppressWarnings("deprecation")
	public boolean call(String proc,List<Object> params){
		Session session = null;
		try {
			CallableStatement statement = null;
			session = HibernateSessionFactory.getSession();
			statement = session.connection().prepareCall(proc);
			int i = 1;
			if (params != null) {
				for (Object object : params) {
					if (object != null) {
						statement.setObject(i, object);
					} else {
						statement.setNull(i, Types.NULL);
					}
					i += 1;
				}
			}
			statement.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
	
	public String callwithresponse(String proc,List<Object> params) {
		String result = "";
		Session session = null;
		try {
			CallableStatement statement = null;
			session = HibernateSessionFactory.getSession();
			statement = session.connection().prepareCall(proc);
			int i = 1;
			if (params != null) {
				for (Object object : params) {
					if (object != null) {
						statement.setObject(i, object);
					} else {
						statement.setNull(i, Types.NULL);
					}
					i += 1;
				}
			}
			statement.registerOutParameter(i, Types.VARCHAR);
			statement.executeQuery();
			result = statement.getString(i);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			session.close();
		}
		return result;
	}
}

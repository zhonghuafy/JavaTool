package fy.eagle.finance.hibernate.dao.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import fy.eagle.finance.hibernate.dao.tools.FieldsMap;
import fy.eagle.finance.hibernate.dao.tools.MultiFields;
import fy.eagle.finance.hibernate.dao.tools.OrderModel;
import fy.eagle.finance.hibernate.dao.tools.PageModel;

public class HibernateDao extends BaseHibernateDAO implements IHibernateDao {

	/**************************************************
	 * @author jero.wang
	 * @since 2011-02-14 at SH
	 * @comments manage session and transaction by self
	 **************************************************/

	private Logger log = Logger.getLogger(this.getClass());
	private int timeout = 5;// 设置事务的超时时间(秒)

	public String saveByObj(Object o) {
		if (o != null) {
			Session sess = getSession();
			Transaction ts = null;
			String genid = null;
			try {
				if (sess != null && sess.isOpen()) {
					ts = sess.getTransaction();
					ts.setTimeout(this.timeout);
					ts.begin();
					genid = (String) sess.save(o);
					ts.commit();
					return genid;
				}
			} catch (Exception e) {
				if (ts != null) {
					ts.rollback();
				}
				log.error("[saveByObj] error!" + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return null;
	}

	public List<String> saveByList(List<?> list) {
		if (list != null) {
			List<String> genIds = new LinkedList<String>();
			Session sess = getSession();
			Transaction ts = null;
			try {
				if (sess != null && sess.isOpen()) {
					ts = sess.getTransaction();
					ts.setTimeout(this.timeout);
					ts.begin();
					for (int i = 0, length = list.size(); i < length; i++) {
						genIds.add((String) sess.save(list.get(i)));
						if ((i % 20) == 0) {
							sess.flush();
							sess.clear();
						}
					}
					ts.commit();
					return genIds;
				}
			} catch (Exception e) {
				if (ts != null) {
					ts.rollback();
				}
				log.error("[saveByList] error!" + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return null;
	}
	
	public Object findById(Class<?> cls, Object id) {
		try {
			if (id != null) {
				return getSession().get(cls, (Serializable) id);
			}
		} catch (Exception e) {
			log.error("[findById] error,id=" + id + "," + e.getMessage()
					+ ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

	public List<?> findByIds(Class<?> cls, Object[] ids) {
		if (ids != null) {
			try {
				List<Object> list = new ArrayList<Object>();
				Session sess = getSession();
				for (Object id : ids) {
					if (id != null) {
						list.add(sess.get(cls, (Serializable) id));
					}
				}
				return list;
			} catch (Exception e) {
				log.error("[findByIds] error," + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return null;
	}

	public List<?> findBySql(Class<?> cls, String sql,OrderModel order) {
		if (sql != null) {
			try {
				sql = "from " + cls.getName() + " " + sql;
				if(order!=null){
					sql = sql + order.getOrderSql();
				}
				log.info("[findBySql]excute sql:" + sql);
				Query queryObject = getSession().createQuery(sql);
				return queryObject.list();
			} catch (Exception e) {
				log.error("[findBySql] error when excute sql:" + "[" + sql
						+ "]," + e.getMessage() + ",cause:" + e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return null;
	}

	public List<?> findBySqlInPageMode(Class<?> cls, String sql,
			PageModel pageMode,OrderModel order) {
		if (sql != null && pageMode != null) {
			try {
				sql = "from " + cls.getName() + " " + sql;
				if(order!=null){
					sql = sql + order.getOrderSql();
				}
				log.info("[findBySqlInPageMode]excute sql:" + sql);
				Query queryObject = getSession().createQuery(sql);
				int currentPage = pageMode.getCurrentPage();
				currentPage = (currentPage > 0 ? currentPage : 1);
				queryObject.setFirstResult((currentPage - 1)
						* pageMode.getPageRows());
				queryObject.setMaxResults(pageMode.getPageRows());
				log.info("[findBySqlInPageMode]current page:" + currentPage
						+ ",maxRows:" + pageMode.getPageRows());
				return queryObject.list();
			} catch (Exception e) {
				log.error("[findBySqlInPageMode] error when excute sql:" + "["
						+ sql + "]," + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return null;
	}

	public List<?> findByParameters(Class<?> cls, FieldsMap fieldsMap,OrderModel order) {
		String sql = "";
		try {
			sql = "from " + cls.getName() + " where" + fieldsMap.getSqlStr();
			if(order!=null){
				sql = sql + order.getOrderSql();
			}
			log.info("[findByParameters]excute sql:" + sql);
			if (fieldsMap.getParameters() != null) {
				Query queryObject = getSession().createQuery(sql);
				Object[] parameters = fieldsMap.getParameters();
				int count = fieldsMap.getCount();
				for (int i = 0; i < count; i++) {
					queryObject.setParameter(i, parameters[i]);
				}
				return queryObject.list();
			}
		} catch (Exception e) {
			log.error("[findByParameters] error when excute sql:" + "[" + sql
					+ "]," + e.getMessage() + ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

	public List<?> findByParametersInPageMode(Class<?> cls,
			FieldsMap fieldsMap, PageModel pageMode,OrderModel order) {
		String sql = "";
		try {
			sql = "from " + cls.getName() + " where" + fieldsMap.getSqlStr();
			if(order!=null){
				sql = sql + order.getOrderSql();
			}
			log.info("[findByParametersInPageMode]excute sql:" + sql);
			if (fieldsMap.getParameters() != null) {
				Query queryObject = getSession().createQuery(sql);
				Object[] parameters = fieldsMap.getParameters();
				int count = fieldsMap.getCount();
				for (int i = 0; i < count; i++) {
					queryObject.setParameter(i, parameters[i]);
				}
				int currentPage = pageMode.getCurrentPage();
				currentPage = (currentPage > 0 ? currentPage : 1);
				queryObject.setFirstResult((currentPage - 1)
						* pageMode.getPageRows());
				queryObject.setMaxResults(pageMode.getPageRows());
				log.info("[findByParametersInPageMode]current page:"
						+ currentPage + ",maxRows:" + pageMode.getPageRows());
				return queryObject.list();
			}
		} catch (Exception e) {
			log.error("[findByParametersInPageMode] error when excute sql:"
					+ "[" + sql + "]," + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

	public boolean updateByObj(Object o) {
		if (o != null) {
			Session sess = getSession();
			Transaction ts = null;
			try {
				if (sess != null && sess.isOpen()) {
					ts = sess.getTransaction();
					ts.setTimeout(this.timeout);
					ts.begin();
					sess.update(o);
					ts.commit();
					return true;
				}
			} catch (Exception e) {
				if (ts != null) {
					ts.rollback();
				}
				log.error("[updateByObj] error!" + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return false;
	}

	public boolean updateByList(List<?> list) {
		if (list != null) {
			Session sess = getSession();
			Transaction ts = null;
			try {
				if (sess != null && sess.isOpen()) {
					ts = sess.getTransaction();
					ts.setTimeout(this.timeout);
					ts.begin();
					for (int i = 0, length = list.size(); i < length; i++) {
						sess.update(list.get(i));
						if ((i % 20) == 0) {
							sess.flush();
							sess.clear();
						}
					}
					ts.commit();
					return true;
				}
			} catch (Exception e) {
				if (ts != null) {
					ts.rollback();
				}
				log.error("[updateByList] error!" + e.getMessage() + ",cause:"
						+ e.getCause());
				e.printStackTrace();
			} finally {
				closeSession();
			}
		}
		return false;
	}

	public int updateByParametersInsetField(Class<?> cls, FieldsMap fieldsMap,
			String[] fieldsName, Object[] values) {
		StringBuilder sb = new StringBuilder();
		int result = -1;
		Transaction ts = null;
		try {
			int length = values.length;
			sb.append("update ").append(cls.getName()).append(" set ");
			for (int j = 0; j < length - 1; j++) {
				sb.append(fieldsName[j]).append("=?, ");
			}
			sb.append(fieldsName[length - 1]).append("=? ");

			sb.append(" where " + fieldsMap.getSqlStr());
			log.info("[deleteByParametersInSetFlagMode]excute sql:"
					+ sb.toString());
			if (fieldsMap.getParameters() != null) {
				Session sess = getSession();
				Query queryObject = sess.createQuery(sb.toString());
				ts = sess.getTransaction();
				ts.setTimeout(this.timeout);
				ts.begin();
				for (int j = 0; j < length; j++) {
					queryObject.setParameter(j, values[j]);
				}
				Object[] parameters = fieldsMap.getParameters();
				int count = fieldsMap.getCount();
				for (int i = length; i < count + length; i++) {
					queryObject.setParameter(i, parameters[i - length]);
				}
				result = queryObject.executeUpdate();
				ts.commit();
			}
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log.error("[updateByParametersInsetField] error when excute sql:"
					+ "[" + sb.toString() + "]," + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public int deleteByParameters(Class<?> cls, FieldsMap fieldsMap) {
		String sql = "";
		int result = -1;
		Transaction ts = null;
		try {
			sql = "delete from " + cls.getName() + " where"
					+ fieldsMap.getSqlStr();
			log.info("[deleteByParameters]excute sql:" + sql);
			if (fieldsMap.getParameters() != null) {
				Session sess = getSession();
				Query queryObject = sess.createQuery(sql);
				ts = sess.getTransaction();
				ts.setTimeout(this.timeout);
				ts.begin();
				Object[] parameters = fieldsMap.getParameters();
				int count = fieldsMap.getCount();
				for (int i = 0; i < count; i++) {
					queryObject.setParameter(i, parameters[i]);
				}
				result = queryObject.executeUpdate();
				ts.commit();
			}
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log.error("[deleteByParameters] error when excute sql:" + "[" + sql
					+ "]," + e.getMessage() + ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public int deleteByParametersInSetFlagMode(Class<?> cls,
			FieldsMap fieldsMap, String fieldName, Object value) {
		StringBuilder sb = new StringBuilder();
		int result = -1;
		Transaction ts = null;
		try {
			sb.append("update ").append(cls.getName()).append(
					" set " + fieldName + "= ?");
			sb.append(" where " + fieldsMap.getSqlStr());
			log.info("[deleteByParametersInSetFlagMode]excute sql:"
					+ sb.toString());
			if (fieldsMap.getParameters() != null) {
				Session sess = getSession();
				Query queryObject = sess.createQuery(sb.toString());
				ts = sess.getTransaction();
				ts.setTimeout(this.timeout);
				ts.begin();
				queryObject.setParameter(0, value);
				Object[] parameters = fieldsMap.getParameters();
				int count = fieldsMap.getCount();
				for (int i = 0; i < count; i++) {
					queryObject.setParameter(i + 1, parameters[i]);
				}
				result = queryObject.executeUpdate();
				ts.commit();
			}
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log
					.error("[deleteByParametersInSetFlagMode] error when excute sql:"
							+ "["
							+ sb.toString()
							+ "],"
							+ e.getMessage()
							+ ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public int deleteAll(Class<?> cls) {
		// TODO Auto-generated method stub\
		int result = -1;
		Transaction trc = null;
		try {
		      String sql = "delete from " + cls.getName();
		      Session session = getSession();
		      Query query = session.createQuery(sql);
		      trc = session.getTransaction();
		      trc.begin();
		      result = query.executeUpdate();
		      trc.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if (trc != null) {
				trc.rollback();
			}
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public List<?> findDistinctList(Class<?> cls,String field) {
		if (field!=null) {
			try {
				String sql="select distinct "+field+" from "+cls.getName()+" order by "+field;
				Query query=getSession().createQuery(sql);
				return query.list();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				closeSession();
			}
		}
		return null;
	}
	
	public List<?> findDistinctByParameter(Class<?> cls,String field,FieldsMap fieldsMap) {
		String sql="";
		try {
			sql="select distinct "+field+" from "+cls.getName()+" where"+fieldsMap.getSqlStr() + " order by "+field;
			if (fieldsMap.getParameters()!=null) {
				Query query=getSession().createQuery(sql);
				Object[] parameters=fieldsMap.getParameters();
				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(i, parameters[i]);
				}
				return query.list();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			closeSession();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> excuteSql(String sql) {
		try {
			if (sql != null && !sql.equals("")) {
				log.info("[excuteSql]excute sql:" + sql);
				Query queryObject = getSession().createSQLQuery(sql);
				return queryObject.list();
			}
		} catch (Exception e) {
			log.error("[excuteSql] error!" + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> excuteSqlInPageMode(String sql,PageModel pageModel,OrderModel orderModel){
		try {
			if (sql != null) {
				if(orderModel!=null){
					sql = sql + orderModel.getOrderSql();
				}
				log.info("[excuteSqlInPageMode]excute sql:" + sql);
				Query queryObject = getSession().createSQLQuery(sql);
				if (pageModel != null) {
					int currentPage = pageModel.getCurrentPage();
					currentPage = (currentPage > 0 ? currentPage : 1);
					queryObject.setFirstResult((currentPage - 1)
							* pageModel.getPageRows());
					queryObject.setMaxResults(pageModel.getPageRows());
					log.info("[findBySqlInPageMode]current page:" + currentPage
							+ ",maxRows:" + pageModel.getPageRows());
				}
				return queryObject.list();
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[excuteSql] error!" + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}
	
	public int count(String sql){
		if (sql != null) {
			Query queryObject = getSession().createSQLQuery(sql);
			Object result = queryObject.uniqueResult();
			if (result == null) {
				return 0;
			}
			return ((BigDecimal)result).intValue();
		}
		return 0;
	}
	
	public int count(Class<?> cls, String sql){
		if (sql!=null && !sql.isEmpty()) {
			sql="select count(*) from "+cls.getName()+" "+sql;
		}else {
			sql="select count(*) from "+cls.getName();
		}
		Query query=getSession().createQuery(sql);
		return ((Long) query.uniqueResult()).intValue();
	}

	public int count(Class<?> cls, FieldsMap fieldsMap) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
			if (fieldsMap != null) {
				sql = "select count(*) from " + cls.getName() + " where " + fieldsMap.getSqlStr();
			}else {
				sql = "select count(*) from " + cls.getName();
			}
			Query query = getSession().createQuery(sql);
			if (fieldsMap != null && fieldsMap.getParameters() != null) {
				Object[] parameters = fieldsMap.getParameters();
				for (int i = 0; i < parameters.length; i++) {
					query.setParameter(i, parameters[i]);
				}
			}
			return ((Long) query.uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return 0;
	}

	public int count(Class<?> cls, MultiFields multiFields) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
			if (multiFields != null && multiFields.haveFields()) {
				sql = "select count(*) from " + cls.getName() + " where " + multiFields.getSql();
			}else {
				sql = "select count(*) from " + cls.getName();
			}
			Query query = getSession().createQuery(sql);
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap != null && fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						for (int j = 0; j < parameters.length; j++) {
							query.setParameter(counter, parameters[j]);
							counter = counter + 1;
						}
					}
				}
			}
			return ((Long) query.uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return 0;
	}

	public int deleteByParameters(Class<?> cls, MultiFields multiFields) {
		// TODO Auto-generated method stub
		String sql = "";
		int result = -1;
		Transaction ts = null;
		try {
			if (multiFields != null && multiFields.haveFields()) {
				sql = "delete from " + cls.getName() + " where"
				+ multiFields.getSql();
			}else {
				sql = "delete from " + cls.getName();
			}
			
			log.info("[deleteByParameters]excute sql:" + sql);
			Session sess = getSession();
			Query queryObject = sess.createQuery(sql);
			ts = sess.getTransaction();
			ts.setTimeout(this.timeout);
			ts.begin();
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						int count = fieldsMap.getCount();
						for (int j = 0; j < count; j++) {
							queryObject.setParameter(counter, parameters[j]);
							counter = counter + 1;
						}
					}
				}
			}
			result = queryObject.executeUpdate();
			ts.commit();
			
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log.error("[deleteByParameters] error when excute sql:" + "[" + sql
					+ "]," + e.getMessage() + ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public int deleteByParametersInSetFlagMode(Class<?> cls,
			MultiFields multiFields, String fieldName, Object value) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		int result = -1;
		Transaction ts = null;
		try {
			sb.append("update ").append(cls.getName()).append(
					" set " + fieldName + "= ?");
			if (multiFields != null && multiFields.haveFields()) {
				sb.append(" where " + multiFields.getSql());
			}
			log.info("[deleteByParametersInSetFlagMode]excute sql:"
					+ sb.toString());
			Session sess = getSession();
			Query queryObject = sess.createQuery(sb.toString());
			ts = sess.getTransaction();
			ts.setTimeout(this.timeout);
			ts.begin();
			queryObject.setParameter(0, value);
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						int count = fieldsMap.getCount();
						for (int j = 0; j < count; j++) {
							counter = counter + 1;
							queryObject.setParameter(counter, parameters[j]);
						}
					}
				}
			}
			result = queryObject.executeUpdate();
			ts.commit();
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log
					.error("[deleteByParametersInSetFlagMode] error when excute sql:"
							+ "["
							+ sb.toString()
							+ "],"
							+ e.getMessage()
							+ ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

	public List<?> findByParameters(Class<?> cls, MultiFields multiFields,
			OrderModel order) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
			sql = "from " + cls.getName();
			if (multiFields != null && multiFields.haveFields()) {
				sql = sql + " where " + multiFields.getSql();
			}
			if(order!=null){
				sql = sql + order.getOrderSql();
			}
			log.info("[findByParameters]excute sql:" + sql);
			Query queryObject = getSession().createQuery(sql);
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						int count = fieldsMap.getCount();
						for (int j = 0; j < count; j++) {
							queryObject.setParameter(counter, parameters[j]);
							counter = counter + 1;
						}
					}
				}
			}
			return queryObject.list();
		} catch (Exception e) {
			log.error("[findByParameters] error when excute sql:" + "[" + sql
					+ "]," + e.getMessage() + ",cause:" + e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

	public List<?> findByParametersInPageMode(Class<?> cls,
			MultiFields multiFields, PageModel pageModel, OrderModel orderModel) {
		// TODO Auto-generated method stub
		String sql = "";
		try {
			sql = "from " + cls.getName();
			if (multiFields != null && multiFields.haveFields()) {
				sql = sql + " where " + multiFields.getSql();
			}
			if(orderModel!=null){
				sql = sql + orderModel.getOrderSql();
			}
			log.info("[findByParametersInPageMode]excute sql:" + sql);
			Query queryObject = getSession().createQuery(sql);
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						int count = fieldsMap.getCount();
						for (int j = 0; j < count; j++) {
							queryObject.setParameter(counter, parameters[j]);
							counter = counter + 1;
						}
					}
				}
			}
			if (pageModel != null) {
				int currentPage = pageModel.getCurrentPage();
				currentPage = (currentPage > 0 ? currentPage : 1);
				queryObject.setFirstResult((currentPage - 1)
						* pageModel.getPageRows());
				queryObject.setMaxResults(pageModel.getPageRows());
				log.info("[findByParametersInPageMode]current page:"
						+ currentPage + ",maxRows:" + pageModel.getPageRows());
			}
			return queryObject.list();
		} catch (Exception e) {
			log.error("[findByParametersInPageMode] error when excute sql:"
					+ "[" + sql + "]," + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return null;
	}

	public List<?> findDistinctByParameter(Class<?> cls, String field,
			MultiFields multiFields) {
		// TODO Auto-generated method stub
		String sql="";
		try {
			sql="select distinct "+field+" from "+cls.getName();
			if (multiFields != null && multiFields.haveFields()) {
				sql = sql + " where " + multiFields.getSql() + " order by " + field;
			}
			Query query=getSession().createQuery(sql);
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters()!=null) {
						Object[] parameters=fieldsMap.getParameters();
						for (int j = 0; j < parameters.length; j++) {
							query.setParameter(counter, parameters[j]);
							counter = counter + 1;
						}
					}
				}
			}
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			closeSession();
		}
		return null;
	}

	public int updateByParametersInsetField(Class<?> cls,
			MultiFields multiFields, String[] fieldsName, Object[] values) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		int result = -1;
		Transaction ts = null;
		try {
			int length = values.length;
			sb.append("update ").append(cls.getName()).append(" set ");
			for (int j = 0; j < length - 1; j++) {
				sb.append(fieldsName[j]).append("=?, ");
			}
			sb.append(fieldsName[length - 1]).append("=? ");
			if (multiFields != null && multiFields.haveFields()) {
				sb.append(" where " + multiFields.getSql());
			}
			log.info("[deleteByParametersInSetFlagMode]excute sql:"
					+ sb.toString());
			Session sess = getSession();
			Query queryObject = sess.createQuery(sb.toString());
			ts = sess.getTransaction();
			ts.setTimeout(this.timeout);
			ts.begin();
			for (int j = 0; j < length; j++) {
				queryObject.setParameter(j, values[j]);
			}
			if (multiFields != null && multiFields.haveFields()) {
				int counter = 0;
				for (int i = 0; i < multiFields.getFieldsMapList().size(); i++) {
					FieldsMap fieldsMap = multiFields.getFieldsMapList().get(i);
					if (fieldsMap.getParameters() != null) {
						Object[] parameters = fieldsMap.getParameters();
						int count = fieldsMap.getCount();
						for (int j = length; j < count + length; j++) {
							queryObject.setParameter(counter + length, parameters[j - length]);
							counter = counter + 1;
						}
					}
				}
			}
			result = queryObject.executeUpdate();
			ts.commit();
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			log.error("[updateByParametersInsetField] error when excute sql:"
					+ "[" + sb.toString() + "]," + e.getMessage() + ",cause:"
					+ e.getCause());
			e.printStackTrace();
		} finally {
			closeSession();
		}
		return result;
	}

}

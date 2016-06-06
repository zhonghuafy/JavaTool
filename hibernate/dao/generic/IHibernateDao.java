package fy.eagle.finance.hibernate.dao.core;

import java.util.List;
import fy.eagle.finance.hibernate.dao.tools.FieldsMap;
import fy.eagle.finance.hibernate.dao.tools.MultiFields;
import fy.eagle.finance.hibernate.dao.tools.OrderModel;
import fy.eagle.finance.hibernate.dao.tools.PageModel;

public interface IHibernateDao {
	
	
	/**************************************************
	 * @author jero.wang
	 * @since 2011-02-14 at SH
	 * @comments manage session and transaction by self
	 **************************************************/
	
	
	//-------------------------------------Begin:添加数据----------------------------------------
	/**
	 * 添加单条数据
	 * @return 添加成功返回id,失败返回null
	 */
	public String saveByObj(Object o);
	
	/**
	 * 以list形式添加数据
	 * @return 添加成功返回新生成的id,失败返回null
	 */
	public List<String> saveByList(List<?> list);
	
	//-------------------------------------Begin:查找数据----------------------------------------
	/**
	 * 直接根据ID查找数据
	 * @param cls
	 * @param id
	 * @return
	 */
	public Object findById(Class<?> cls,Object id);
	
	/**
	 * 直接根据多个ID查找数据
	 * @param cls
	 * @param ids
	 * @return
	 */
	public List<?> findByIds(Class<?> cls,Object[] ids);
	
	/**
	 * 通过查询语句查找实体数据
	 * sql中的参数名称为实体类中的字段名称
	 * @param sql
	 * @return
	 */
	public List<?> findBySql(Class<?> cls,String sql,OrderModel order);
	
	/**
	 * 在分页模式下通过查询语句查找实体数据
	 * sql中的参数名称为实体类中的字段名称
	 * @param sql
	 * @param pageMode
	 * @return
	 */
	public List<?> findBySqlInPageMode(Class<?> cls,String sql,PageModel pageMode,OrderModel order);
	
	/**
	 * 根据参数查找数据
	 * @param cls
	 * @param fieldMap:参数集合
	 * @return
	 */
	public List<?> findByParameters(Class<?> cls,FieldsMap fieldsMap,OrderModel order);
	
	/***********
	 * 根据多个参数查找数据
	 * @param cls
	 * @param multiFields
	 * @param order
	 * @return
	 */
	public List<?> findByParameters(Class<?> cls,MultiFields multiFields,OrderModel order);
	
	/**
	 * 在分页模式下根据参数查找数据
	 * @param cls
	 * @param fieldMap
	 * @param pageMode
	 * @return
	 */
	public List<?> findByParametersInPageMode(Class<?> cls,FieldsMap fieldsMap,PageModel pageMode,OrderModel order);
	
	/**********
	 * 在分页模式下根据多个参数查找数据
	 * @param cls
	 * @param multiFields
	 * @param pageModel
	 * @param orderModel
	 * @return
	 */
	public List<?> findByParametersInPageMode(Class<?> cls,MultiFields multiFields,PageModel pageModel,OrderModel orderModel);
	
	//-------------------------------------Begin:更新数据----------------------------------------
	/**
	 * 更新单条数据
	 * @return 添加成功返回true,失败返回false
	 */
	public boolean updateByObj(Object o);
	
	/**
	 * 以list形式更新数据
	 * @return 更新成功返回true,失败返回false
	 */
	public boolean updateByList(List<?> list);
	
	/**
	 * 通过条件更新某些字段的值,fieldsName,values中对应字段和字段的值顺序要一致
	 * @param cls
	 * @param fieldsMap
	 * @param fieldsName
	 * @param values
	 * @return 收影响的数据条数
	 */
	public int updateByParametersInsetField(Class<?> cls,FieldsMap fieldsMap,String[] fieldsName,Object[] values);
	
	/************
	 * 通过条件更新某些字段的值,fieldsName,values中对应字段和字段的值顺序要一致
	 * @param cls
	 * @param multiFields
	 * @param fieldsName
	 * @param values
	 * @return
	 */
	public int updateByParametersInsetField(Class<?> cls,MultiFields multiFields,String[] fieldsName,Object[] values);
	
	//-------------------------------------Begin:删除数据----------------------------------------	
	/**
	 * 根据参数删除数据(真删除)
	 * @param cls
	 * @param ids
	 * @return:删除的数据条数
	 */
	public int deleteByParameters(Class<?> cls,FieldsMap fieldsMap);
	
	/*************
	 * 根据多个参数删除数据(真删除)
	 * @param cls
	 * @param multiFields
	 * @return:删除的数据条数
	 */
	public int deleteByParameters(Class<?> cls,MultiFields multiFields);
	
	/**
	 * 通过设置标志位来根据参数删除数据(假删除)
	 * @param cls
	 * @param ids
	 * @return:删除的数据条数
	 */
	public int deleteByParametersInSetFlagMode(Class<?> cls,FieldsMap fieldsMap,String fieldName,Object value);
	
	/**************
	 * 通过设置标志位来根据多个参数删除数据(假删除)
	 * @param cls
	 * @param multiFields
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public int deleteByParametersInSetFlagMode(Class<?> cls,MultiFields multiFields,String fieldName,Object value);
	
	/****
	 * 删除所有记录
	 * @param cls
	 * @return:删除的数据条数
	 */
	public int deleteAll(Class<?> cls);
	
	//-------------------------------------Begin:工具函数----------------------------------------
	/**
	 * 执行sql语句
	 * 返回类型为单维数组的List，
	 * 数组类型根据sql语句中查询的字段而定
	 */
	public List<Object[]> excuteSql(String sql);
	
	public List<Object[]> excuteSqlInPageMode(String sql,PageModel pageModel,OrderModel orderModel);
	
	/**********
	 * count by execute a sql<br>
	 * eg. select count(*) ...
	 * @param sql
	 * @return
	 */
	public int count(String sql);
	
	/*******
	 * count
	 * @param cls
	 * @param sql
	 * @return
	 */
	public int count(Class<?> cls, String sql);
	
	/*******
	 * count
	 * @param cls
	 * @param fieldsMap given conditions
	 * @return
	 */
	public int count(Class<?> cls, FieldsMap fieldsMap);
	
	/**********
	 * count
	 * @param cls
	 * @param multiFields
	 * @return
	 */
	public int count(Class<?> cls,MultiFields multiFields);
	
	/***********
	 * 对指定字段使用distinct限定查询
	 * @param cls
	 * @param field 使用distinct过滤的字段
	 * @return
	 */
	public List<?> findDistinctList(Class<?> cls,String field);
	
	/************
	 * 对指定字段使用distinct限定查询
	 * @param cls
	 * @param field 使用distinct过滤的字段
	 * @param fieldsMap 执行查询的条件
	 * @return
	 */
	public List<?> findDistinctByParameter(Class<?> cls,String field,FieldsMap fieldsMap);
	
	public List<?> findDistinctByParameter(Class<?> cls,String field,MultiFields multiFields);
}

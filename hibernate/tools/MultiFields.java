package com.csc.mail.jsh.db.hibernate.tools;

import java.util.LinkedList;
import java.util.List;

/******************
 * tool for complex condition select.
 * @author Joshua
 *
 */
public class MultiFields{
	
	private List<FieldsMap> fieldsMapList;
	
	private Boolean or;

	public MultiFields() {
		super();
		// TODO Auto-generated constructor stub
		fieldsMapList = new LinkedList<FieldsMap>();
		or = false;
	}
	
	/******************
	 * convert MultiFields to sql
	 * @return
	 */
	public String getSql() {
		if (fieldsMapList == null || fieldsMapList.size() == 0) {
			return " ";
		}
		StringBuilder sb = new StringBuilder();
		if (this.or) {
			for (int i = 0; i < fieldsMapList.size()-1; i++) {
				sb.append(" (" + fieldsMapList.get(i).getSqlStr() + ") " + " or ");
			}
			sb.append(" (" + fieldsMapList.get(fieldsMapList.size()-1).getSqlStr() + ") ");
		}else {
			for (int i = 0; i < fieldsMapList.size()-1; i++) {
				sb.append(" (" + fieldsMapList.get(i).getSqlStr()+ ") " + " and ");
			}
			sb.append(" (" + fieldsMapList.get(fieldsMapList.size()-1).getSqlStr() + ") ");
		}
		return sb.toString();
	}
	
	/****************
	 * whether have FieldsMap.
	 * @return
	 */
	public Boolean haveFields(){
		if (fieldsMapList == null || fieldsMapList.size() == 0) {
			return false;
		}
		return true;
	}
	
	public List<FieldsMap> getFieldsMapList() {
		return fieldsMapList;
	}

	public void setFieldsMapList(List<FieldsMap> fieldsMapList) {
		this.fieldsMapList = fieldsMapList;
	}

	public Boolean getOr() {
		return or;
	}

	public void setOr(Boolean or) {
		this.or = or;
	}

	public void addFieldsMap(FieldsMap fieldsMap){
		if (fieldsMap != null) {
			fieldsMapList.add(fieldsMap);
		}
	}
	
	public void clearFieldsMap(){
		fieldsMapList.clear();
	}
	
	public FieldsMap getFieldsMap(int index){
		return fieldsMapList.get(index);
	}
	
	public Boolean isEmpty(){
		return fieldsMapList.isEmpty();
	}
	
	public FieldsMap removeFieldsMap(int index){
		return fieldsMapList.remove(index);
	}
	
	public int size(){
		return fieldsMapList.size();
	}
	
}
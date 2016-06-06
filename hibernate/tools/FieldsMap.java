package com.csc.mail.jsh.db.hibernate.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FieldsMap {
	
	/**
	 * @author jero.wang
	 * @since 2011-02-14 at SH.CASCO
	 */
	
	private HashMap<String,Field> fieldMap = null;
	private Object[] parameters = null;
	private int count = 0;

	public FieldsMap(){
		this.fieldMap = new HashMap<String,Field>();
	}
	
	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void putMap(String key,Object o){
		if(key!=null&&!(key.equals(""))&&o!=null){
			if(this.fieldMap.containsKey(key)){
				this.count--;
				this.fieldMap.remove(key);
			}
			this.fieldMap.put(key, new Field(o));
			this.count++;
		}
	}
	
	public void putMap(String key,Field field){
		if(key!=null&&!(key.equals(""))&&field!=null){
			if(this.fieldMap.containsKey(key)){
				Field f = this.fieldMap.get(key);
				if(f.isBetween()||f.isIn()){
					this.count = this.count - f.getObjs().length;
				}else{
					this.count--;
				}
				this.fieldMap.remove(key);
			}
			
			if(field.isIn() || field.isNotIn()){
				if(field.getObjs()!=null&&field.getObjs().length>0){
					this.fieldMap.put(key, field);
					this.count = this.count + field.getObjs().length;
				}
			}else if(field.isBetween()){
				if(field.getObjs()!=null&&field.getObjs().length==2){
					this.fieldMap.put(key, field);
					this.count = this.count + 2;
				}
			}else{
				this.fieldMap.put(key, field);
				if(field.getCompare().equals(CompareEnum.NO_NULL)||
						field.getCompare().equals(CompareEnum.IS_NULL)){
					
				}else{
					this.count++;
				}
			}
		}
	}

	public HashMap<String, Field> getFieldMap() {
		return this.fieldMap;
	}

	public void setFieldMap(HashMap<String, Field> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	public String getSqlStr()
	{
		try{
			this.parameters = new Object[this.count];
			Set<String> set = this.fieldMap.keySet();
			Iterator<String> iterator = set.iterator();
			String key = null;
			Field field = null;
			StringBuilder sb = new StringBuilder();
			int i = 0;
			int a = 0;
			boolean addFlag = true;
			while(iterator.hasNext()){
				addFlag = true;
				key = iterator.next();
				field = this.fieldMap.get(key);
				sb.append(" ");
				if(field.isBetween()){
					sb.append(key).append(" between ? ").append(" and ? ");
					for(Object o:field.getObjs()){
						this.parameters[i] = o;
						i++;
					}
				}else if(field.isLike()){
					sb.append(key).append(" like ? ");
					this.parameters[i] = field.getObj();
					i++;
				}else if(field.isIn()){
					sb.append(key).append(" in (");
					int l = field.getObjs().length-1;
					for(int j=0;j<l;j++){
						this.parameters[i] = field.getObjs()[j];
						sb.append("?,");
						i++;
					}
					this.parameters[i] = field.getObjs()[l];
					sb.append("?) ");
					i++;
				}else if (field.isNotIn()) {
					sb.append(key).append(" not in (");
					int l = field.getObjs().length-1;
					for(int j=0;j<l;j++){
						this.parameters[i] = field.getObjs()[j];
						sb.append("?,");
						i++;
					}
					this.parameters[i] = field.getObjs()[l];
					sb.append("?) ");
					i++;
				}else{
					if(field.getCompare().equals(CompareEnum.MORE)){
						sb.append(key).append(">? ");
					}else if(field.getCompare().equals(CompareEnum.MORE_OR_EQUAL)){
						sb.append(key).append(">=? ");
					}else if(field.getCompare().equals(CompareEnum.LESS)){
						sb.append(key).append("<? ");
					}else if(field.getCompare().equals(CompareEnum.LESS_OR_EQUAL)){
						sb.append(key).append("<=? ");
					}else if(field.getCompare().equals(CompareEnum.NO_EQUAL)){
						sb.append(key).append("<>? ");
					}else if(field.getCompare().equals(CompareEnum.NO_NULL)){
						sb.append(key).append(" is not null ");
						addFlag = false;
					}else if(field.getCompare().equals(CompareEnum.IS_NULL)){
						sb.append(key).append(" is null ");
						addFlag = false;
					}else{
						sb.append(key).append("=? ");
					}
					if(addFlag){
						this.parameters[i] = field.getObj();
						i++;
					}
				}
				if((a+1)<this.fieldMap.size()){
					sb.append(" and ");
				}
				a++;
			}
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
}

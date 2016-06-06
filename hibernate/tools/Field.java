package com.csc.mail.jsh.db.hibernate.tools;

public class Field {
	
	private Object obj = null;
	private Object[] objs = null;
	private boolean isBetween = false;
	private boolean isLike = false;
	private boolean isIn = false;
	private boolean isNotIn = false;
	private CompareEnum compare = CompareEnum.EQUAL;
	
	public Field(){
	}
	
	public Field(Object o){
		this.obj = o;
	}
	
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object[] getObjs() {
		return objs;
	}
	public void setObjs(Object[] objs) {
		this.objs = objs;
	}
	public boolean isBetween() {
		return isBetween;
	}
	public void setBetween(boolean isBtween) {
		this.isBetween = isBtween;
	}
	public boolean isLike() {
		return isLike;
	}
	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public boolean isIn() {
		return isIn;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

	public CompareEnum getCompare() {
		return compare;
	}

	public void setCompare(CompareEnum compare) {
		this.compare = compare;
	}

	public boolean isNotIn() {
		return isNotIn;
	}

	public void setNotIn(boolean isNotIn) {
		this.isNotIn = isNotIn;
	}
	
}

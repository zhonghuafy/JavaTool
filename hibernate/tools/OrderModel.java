package com.csc.mail.jsh.db.hibernate.tools;

import java.util.LinkedList;
import java.util.List;

public class OrderModel {
	
	private final static String ORDER_BY_SQL = " order by ";
	private final static String ORDER_BY_ASC = " asc";
	private final static String ORDER_BY_DESC = " desc";
	
	private List<String> orderList = null;
	
	public OrderModel()
	{
		orderList =  new LinkedList<String>();
	}
	
	public void desc(String fieldName)
	{
		orderList.add(fieldName+ORDER_BY_DESC);
	}
	
	public void asc(String fieldName)
	{
		orderList.add(fieldName+ORDER_BY_ASC);
	}

	public String getOrderSql()
	{
		if (orderList==null||orderList.size() == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0,length = orderList.size() - 1;i < length;i++){
			sb.append(" ").append(orderList.get(i)).append(",");
		}
		sb.append(" ").append(orderList.get(orderList.size() - 1));
		return ORDER_BY_SQL + sb.toString();
	}
}

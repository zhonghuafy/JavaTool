package fy.eagle.finance.action.base;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;

import fy.eagle.finance.cache.SParam;
import fy.eagle.finance.door.SessionListener;
import fy.eagle.finance.hibernate.dao.tools.PageModel;

import com.opensymphony.xwork2.ActionSupport;

public class BasicAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1608094634550940041L;

	protected static Logger logger = Logger.getLogger(BasicAction.class);

	protected Boolean success;

	protected String msg;

	protected String forward;

	/******************
	 * get HttpServletRequest
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/****************
	 * get HttpServletResponse
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/*****************
	 * write a json string to client side.
	 * 
	 * @param json
	 */
	protected void writeJson(String json) {
		try {
			HttpServletResponse response = getResponse();
			response.setContentType(SParam.RESPONSE_CODE);
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
		}
	}

	/**************
	 * write a json string to client side.
	 * 
	 * @param json
	 * @param contentType
	 */
	protected void writeJson(String json, String contentType) {
		try {
			HttpServletResponse response = getResponse();
			if (contentType == null || contentType.trim().length() == 0) {
				response.setContentType(SParam.RESPONSE_CODE);
			} else {
				response.setContentType(contentType);
			}
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString());
		}
	}

	/*******************
	 * get a PageModel initializied with parameters in HttpServletRequest or use
	 * default parameters.
	 * 
	 * @return
	 */
	protected PageModel getPageModel() {
		PageModel pageModel = new PageModel();
		HttpServletRequest request = getRequest();
		String startStr = request.getParameter("start");
		String limitStr = request.getParameter("limit");
		int start = SParam.PAGE_START;
		int limit = SParam.PAGE_SIZE;
		if (startStr != null && startStr.trim().length() > 0) {
			try {
				start = Integer.parseInt(startStr);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		if (limitStr != null && limitStr.trim().length() > 0) {
			try {
				limit = Integer.parseInt(limitStr);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		start = start / limit + 1;
		pageModel.setCurrentPage(start);
		pageModel.setPageRows(limit);
		return pageModel;
	}

	protected void writeMsg() {
		String json = "{success:" + this.success + ",msg:\"" + this.msg + "\"}";
		writeJson(json);
	}

	protected void writeMsg(Boolean success, String msg) {
		if (success == null) {
			success = this.success;
		}
		if (msg == null || msg.length() == 0) {
			msg = this.msg;
		}
		String json = "{success:" + success + ",msg:\"" + msg + "\"}";
		writeJson(json);
	}
	
	/**********
	 * client ip
	 * @return
	 */
	protected String clientIP() {
		return ServletActionContext.getRequest().getRemoteAddr();
	}
	
	/*************
	 * kick out userid's old session if exist 
	 * and add userid's new session when session is not null
	 * @param userid
	 * @param session
	 */
	protected void kickadd(String userid,HttpSession session) {
		if (SessionListener.sessionMap.containsKey(userid)) {
			System.out.println("kick out: "+userid);
			HttpSession oldSession = SessionListener.sessionMap.get(userid);
			if (session != null && oldSession != null && session.getId().equals(oldSession.getId())) {
				return;
			}
			try {
				oldSession.invalidate();
			} catch (Exception e) {
				// TODO: handle exception
			}
			SessionListener.sessionMap.remove(userid);
		}
		if (session != null) {
			SessionListener.sessionMap.put(userid, session);
		}
		//print who's online
//		Iterator iterator = SessionListener.sessionMap.keySet().iterator();
//		while (iterator.hasNext()) {
//			System.out.println("online: "+iterator.next());
//		}
	}
	
	/************
	 * export excel
	 * @param <T>
	 * @param title
	 * @param headers
	 * @param dataset
	 */
	@SuppressWarnings("unchecked")
	protected<T> void exportExcel(String title,String[] headers,Collection<T> dataset){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(15);
		
		HSSFCellStyle style = workbook.createCellStyle();
		
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		style.setFont(font);
		
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	    style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	    
	    HSSFFont font2 = workbook.createFont();
	    font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    
	    style2.setFont(font2);
	    
	    HSSFRow row = sheet.createRow(0);
	    HSSFCell cell = null;
	    HSSFRichTextString text = null;
	    for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	    
	    Iterator<?> iterator = dataset.iterator();
	    int index = 0;
	    while (iterator.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) iterator.next();
			Field[] fields = t.getClass().getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(style2);
				String fieldName = fields[j].getName();
				String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
				try {
					Class tcls = t.getClass();
					Method getMethod = tcls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String textvalue = "";
					if (value != null) {
						textvalue = value.toString();
					}
					text = new HSSFRichTextString(textvalue);
					cell.setCellValue(text);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.toString());
				}
			}
		}
	    OutputStream out = null;
	    try {
	    	HttpServletResponse response = getResponse();
	    	response.reset();
	    	response.setContentType("APPLICATION/x-msdownload");
	    	response.addHeader("Content-Disposition", "attachment;filename=\"" 
 + new String((title + ".xls").getBytes("GBK"), 
 "ISO8859_1") + "\"");
			out = response.getOutputStream();
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
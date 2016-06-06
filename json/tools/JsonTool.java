package fy.eagle.finance.util.json.util;

import java.util.List;

import com.google.gson.Gson;

/**************
 * use gson
 * @author Joshua
 *
 */
public class JsonTool {
	
	private static Gson gson = new Gson();

	/***
	 * Convert a list to json.<br>
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String ConvertListToJson(List<?> list) throws Exception {
		if (list == null || list.size() == 0) {
			return "{\"totalCount\":\"0\"}";
		}
		return gson.toJson(list);
	}
	
	/***
	 * Convert a list to json.<br>
	 * 
	 * @param list
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public static String ConvertListToJson(List<?> list, int count) {
		if (list == null || list.size() == 0) {
			return "{\"totalCount\":\"0\"}";
		}
		String json = "{\"totalCount\":\"" + String.valueOf(count)
		+ "\",\"root\":" + gson.toJson(list) + "}";
		return json;
	}
	
	/*****
	 * Convert an object to json string<br>
	 * NO additional info will be added.
	 * @param obj
	 * @return
	 */
	public static String ConvertObjToJson(Object obj) {
		return gson.toJson(obj);
	}
	
	/*****
	 * Convert an object to json string<br>
	 * auto add {success:true,data:[obj-to-json]}
	 * @param cls
	 * @param obj
	 * @return
	 */
	public static String ConvertObjToJson(Class<?>cls, Object obj){
		String json = "{success:true,\"data\":[" + gson.toJson(obj) + "]}";
		return json;
	}
	
	/************************
	 * Convert an object to json string<br>
	 * this function have addtional success information. use
	 * ConvertObjToJson(Object obj) to get a pure object converted json.<br>
	 * success:true, data: obj-json(with prex)
	 * @param cls
	 * @param obj
	 * @param prex
	 *            prefix
	 * @return
	 */
	public static String ConvertObjToJson(Class<?> cls, Object obj, String prex) {
		return null;
	}
	
	/*****
	 * Convert a json string to an array. <br>
	 * The parameter should be like:
	 * ["string1","string2", ... "stringN"]
	 * 
	 * @param json
	 * @return
	 */
	public static String[] ConvertJsonStringToArray(String json) {
		if (json==null || json.trim().length()<2) {
			return null;
		}
		json = json.substring(1, json.length() - 1);
		if (json == null || json.isEmpty()) {
			return null;
		}
		return gson.fromJson(json, String[].class);
	}
	
	public static Object ConvertJsonStringToObject(String json, Class<?> cls){
		return gson.fromJson(json, cls);
	}
}

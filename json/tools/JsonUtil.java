package fy.eagle.finance.util.json.util;

import java.util.List;

import fy.eagle.finance.util.json.core.JSONArray;
import fy.eagle.finance.util.json.core.JSONObject;

/***
 * Utilities for Json and general Objects
 * 
 * @author Joshua
 * 
 */
public class JsonUtil {

	/***
	 * Convert a list to json.<br>
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public String ConvertListToJson(List<?> list) throws Exception {
		if (list == null || list.size() == 0) {
			return "{\"totalCount\":\"0\"}";
		}
		JSONArray jArray = new JSONArray();
		JSONObject jObject = null;
		for (int i = 0; i < list.size(); i++) {
			jObject = new JSONObject(list.get(i));
			jArray.put(jObject);
		}
		return jArray.toString();
	}

	/***
	 * Convert a list to json.<br>
	 * 
	 * @param list
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public String ConvertListToJson(List<?> list, int count) {
		if (list == null || list.size() == 0) {
			return "{\"totalCount\":\"0\"}";
		}
		JSONArray jArray = new JSONArray();
		JSONObject jObject = null;
		for (int i = 0; i < list.size(); i++) {
			jObject = new JSONObject(list.get(i));
			jArray.put(jObject);
		}
		String json = "{\"totalCount\":\"" + String.valueOf(count)
				+ "\",\"root\":" + jArray.toString() + "}";
		return json;
	}

	/*****
	 * Convert an object to json string<br>
	 * NO additional info will be added.
	 * @param obj
	 * @return
	 */
	public String ConvertObjToJson(Object obj) {
		JSONObject jObject = new JSONObject(obj);
		return jObject.toString();
	}
	
	/*****
	 * Convert an object to json string<br>
	 * auto add {success:true,data:[obj-to-json]}
	 * @param cls
	 * @param obj
	 * @return
	 */
	public String ConvertObjToJson(Class<?>cls, Object obj){
		JSONObject jObject = new JSONObject(obj);
		String json = "{success:true,\"data\":[" + jObject.toString() + "]}";
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
	public String ConvertObjToJson(Class<?> cls, Object obj, String prex) {
		JSONObject jObject = new JSONObject(obj);
		String json = "{success:true,\"data\":[" + jObject.toString(prex)
				+ "]}";
		return json;
	}

	/*****
	 * Convert a json string to an array. <br>
	 * The parameter should be like:
	 * ["string1","string2", ... "stringN"]
	 * 
	 * @param json
	 * @return
	 */
	public String[] ConvertJsonStringToArray(String json) {
		if (json==null || json.trim().length()<2) {
			return null;
		}
		json = json.substring(1, json.length() - 1);
		if (json == null || json.isEmpty()) {
			return null;
		}
		String[] array = json.split(",");
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				array[i] = array[i].substring(1, array[i].length() - 1);
			}
		}
		return array;
	}
}
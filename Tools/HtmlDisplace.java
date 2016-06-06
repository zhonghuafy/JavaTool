package com.csc.mail.jsh.util.sys;

/*****************
 * replace html symbols.
 * @author Joshua
 *
 */
public class HtmlDisplace {

	/****************
	 * change '"', '&lt;', '>' to html code.
	 * @param str
	 * @return
	 */
	public static String str4html(String str) {
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}
	
	/************
	 * change html code to  '"', '&lt;', '>'
	 * @param html
	 * @return
	 */
	public static String html4str(String html) {
		return html;
	}
}

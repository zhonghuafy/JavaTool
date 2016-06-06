/**
 * 
 */
package casco.com.tse.util.sys.tool;


import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * exception stack trace
 * @author Joshua
 *
 */
public class StackTrace {

	/************
	 * get stack trace from Exception and convert to String.
	 * @param ex
	 * @return
	 */
	public static String tostring(Exception exception){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}
	
	/***********
	 * get abstract message from exception.
	 * @param exception
	 * @return
	 */
	public static String abstractmsg(Exception exception){
		String msg = exception.getMessage()
		   + " Cause: " + exception.getCause()
		   + " " + exception.getClass();
		return msg;
	}
}

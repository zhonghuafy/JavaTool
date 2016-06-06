/**
 * 
 */
package fy.eagle.finance.door;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import fy.eagle.finance.cache.OperCache;
import fy.eagle.finance.cache.SParam;
import fy.eagle.finance.util.tool.Property;


/**
 * @author Joshua
 *
 */
public class Boot implements ServletContextListener {

	private static Logger logger = Logger.getLogger(Boot.class);
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		logger.info("trying to initialize FINANCE >>>>>>");
		logger.info("loading config.properties...");
		Property property = Property.getInstance();
//		logger.info("version: "+property.getProperty("version"));
		
		logger.info("initializing system parameters...");
		SParam.SESSION_USER = property.getProperty("session_user");
		SParam.SESSION_ACCOUNT = property.getProperty("session_account");
		SParam.SESSION_USER_NAME = property.getProperty("session_username");
		SParam.SESSION_ROLE = property.getProperty("session_role");
		SParam.RESPONSE_CODE = property.getProperty("response_code");
		SParam.BOOL_TRUE = property.getProperty("bool_true");
		SParam.BOOL_FALSE = property.getProperty("bool_false");
		SParam.PAGE_START = Integer.parseInt(property.getProperty("page_start"));
		SParam.PAGE_SIZE = Integer.parseInt(property.getProperty("page_size"));
		OperCache.getInstance();//load operation type
		logger.info("initialize succeeded >>>>>> ");
	}

}

/**
 * 
 */
package casco.com.tse.util.sys.tool;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Joshua
 *
 */
public class Property {

	private Property() {
		super();
		// TODO Auto-generated constructor stub
		config = new Properties();
		load();
	}

	private static Logger logger = Logger.getLogger(Property.class);
	private final static String CONFIG_FILE = "/config.properties";
	
	private static Property instance;
	private static Properties config;
	
	public static Property getInstance(){
		if (instance == null) {
			instance = new Property();
		}
		return instance;
	}
	
	private void load(){
		InputStream iStream = null;
		try {
			iStream = getClass().getResourceAsStream(CONFIG_FILE);
			config.load(iStream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("load properties failed: "+StackTrace.tostring(e));
		} finally {
			try {
				iStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}
	
	public String getProperty(String key){
		return config.getProperty(key);
	}
	
	public boolean setProperty(String key, String value){
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(CONFIG_FILE);
			config.setProperty(key, value);
			config.store(oStream, "Update " + key + " value");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				oStream.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}
}

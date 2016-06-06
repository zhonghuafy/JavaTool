package casco.core.tools.util;

import java.io.IOException;
import org.apache.log4j.Logger;

public class ReadProperty {
	
	private static java.util.Properties  cfgFile=new java.util.Properties();
	private final static Logger log = Logger.getLogger(ReadProperty.class);
	
	static
	{
		new ReadProperty();
	}
	
	public ReadProperty()
	{
		String dir="/config.properties";
		java.io.InputStream cfgin=getClass().getResourceAsStream(dir);
		
		try{
			cfgFile.load(cfgin);
			log.info("successed to load ["+dir+"]");
		}catch (IOException e) {
			e.printStackTrace();
			log.error("failed to load ["+dir+"]");
		}finally{
			try{
				cfgin.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getKey(String key){
		return cfgFile.getProperty(key);
	}
	
	public static boolean containsKey(String key)
	{
		return cfgFile.containsKey(key);
	}

	public static String getKey(String key,String defaultValue)
	{
		return cfgFile.getProperty(key, defaultValue);
	}
}

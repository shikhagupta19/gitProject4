package in.co.rays.proj4.util;
import java.util.ResourceBundle;

/**
 * Read the property values from application properties file using Resource
 * Bundle
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */


public class PropertyReader {
	
	private static final String FILENAME = "in.co.rays.proj4.bundle.system";
	public static ResourceBundle rbBundle=ResourceBundle.getBundle(FILENAME);
	
	/**
     * Return value of key
     * 
     * @param key
     * @return
     * 
     */
	
	public static String getValue(String key)	{
	
		String val=null;
		try
		{
		val=rbBundle.getString(key);
		
		}
		
		catch (Exception e) {
			val=key;
		}
		
		return val; 
	}
	
	public static int getValueInt(String key)	{
		
		int val=0;
		try
		{
		val=Integer.parseInt(rbBundle.getString(key));
		
		}
		
		catch (Exception e) {
			val=Integer.parseInt(key);
		}
		
		return val; 
	}
	
	
	
	 /**
     * Gets String after placing parameter values
     * 
     * @param key
     * @param param
     * @return String
     */

	public static String getValue(String key,String param)
	{
		String msg=getValue(key);
		msg=msg.replace("{0}",param);
		return msg;
	}
	
	 /**
     * Gets String after placing parameters values
     * 
     * @param key
     * @param params
     * @return
     */

	public static String getValue(String key,String []param)
	{
	    String msg=getValue(key);
	    for (int i = 0; i < param.length; i++) 
	    {
		msg=msg.replace("{"+i+"}",param[i]);
		}
		return msg;
	}
	
	/**
     * Test method
     * 
     * @param args
     */
	
	public static void main(String[] args) {
	        String[] params = { "Roll No" };
	        System.out.println(PropertyReader.getValue("url"));
	        System.out.println(PropertyReader.getValue("error.require", params));
	    }
}

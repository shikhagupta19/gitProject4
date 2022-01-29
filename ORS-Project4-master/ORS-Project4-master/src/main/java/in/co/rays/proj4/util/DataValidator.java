package in.co.rays.proj4.util;
import java.util.Date;

import in.co.rays.proj4.util.DataUtility;

/**
 * This class validates input data
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class DataValidator {
	
	/**
     * Checks if value is Null
     
     * @param val
     * @return
     */
    
    
    public static boolean isNull(String val){
    	if(val==null||val.trim().length()==0){
    		return true;
    	}else{
    		return false;
    	}
    }
    

    /**
     * Checks if value is NOT Null
     * 
     * @param val
     * @return
     */
    public static boolean isNotNull(String val) {
        return !isNull(val);
    }

    /**
     * Checks if value is an Integer
     * 
     * @param val
     * @return
     */


    public static boolean isInteger(String val){
    	if(isNotNull(val)){
    		try{
    			int i= Integer.parseInt(val);
    			return true;
    		}catch(NumberFormatException e){
    			return false;
    			}
    		}
    	 else {
    		return false;
    }
    
}
    /**
     * Checks if value is Long
     * 
     * @param val
     * @return
     */
    public static boolean isLong(String val){
    	if (isNotNull(val)){
    		try{
    			long i=Long.parseLong(val);
    			return true;
    		}catch(NumberFormatException e){
    			return false;
    		}
    	}
    		else{
    			return false;
    		}
    	}
    /**
     * Checks if value is valid Email ID
     * 
     * @param val
     * @return
     */

	public static boolean isEmailId(String email)
	{
		String emailreg="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(email))
		{
		try 
		{
			return email.matches(emailreg);
		} 
		catch (NumberFormatException e)
		{
			return false;
		}		
		} 
		else
		{
        return false;
		}
	}
    /**
     * Checks if value is Date
     * 
     * @param val
     * @return
     */
    public static boolean isDate(String val) {

        Date d = null;
        if (isNotNull(val)) {
            d = DataUtility.getDate(val);
        }
        return d != null;
    }


	
	 /**
		 * Checks if value is Phone No
		 * 
		 * @param val
		 * @return boolean
		 */
		public static boolean isPhoneNo(String val) 
		{
			String regex ="^[6-9]\\d{9}$";
			if (!val.matches(regex)) 
			{
				return true;
			} 
			else
			{	
				return false;
			}
		}
		
		
		
		
		/**
		 * Checks if value is name
		 * 
		 * @param val
		 * @return
		 */
		public static boolean isName(String val) {

		String name = "^[a-zA-Z]{3,15}$";
		System.out.println("isName method run");
			
			if (!val.matches(name)) 
			{
				return true;
			} else
			{
				return false;
			}
			
		}

		/**
		 * check value is in range 
		 * @param val
		 * @return
		 */
		public static boolean isRange(String val) {

			System.out.println("isRange method run");
			
			String name ="^[a-zA-Z]{3,15}$";
			if (!val.matches(name)) {
				return true;
			} else {
				return false;
			}
		}
		
		public static boolean isRollNO(String val) {
		
			String passregex = "^([0-9]{2}[a-zA-Z]{2}[0-9]{2})$";
			if (!val.matches(passregex)) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Checks if value is Password
		 * 
		 * @param val
		 * @return boolean
		 */
		/*This regex will enforce these rules:

			At least one upper case English letter, (?=.*?[A-Z])
			At least one lower case English letter, (?=.*?[a-z])
			At least one digit, (?=.*?[0-9])
			At least one special character, (?=.*?[#?!@$%^&*-])
			Minimum eight in length .{8,} (with the anchors)
*/
		public static boolean isPassword(String val) {
			String passregex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

			if (val.matches(passregex))
			{
				return true;
			} 
			else
			{
				return false;
			}
		}
		
		
		public static boolean isMarks(String value)
		{
			//System.out.println("is marks called");
			int i=Integer.parseInt(value);
			//System.out.println("int value------------>"+i);
		    boolean pass=false;
			
			if(i<=100 && i>=0 )
			{
		    pass=true;
			}
			return pass;
			
			
		}

    
    /**
     * Test above methods
     * 
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Not Null 2" + isNotNull("ABC"));
        System.out.println("Not Null 3" + isNotNull(null));
        System.out.println("Not Null 4" + isNull("123"));

        System.out.println("Is Int " + isInteger(null));
        System.out.println("Is Int " + isInteger("ABC1"));
        System.out.println("Is Int " + isInteger("123"));
        System.out.println("Is Int " + isNotNull("123"));
    }

}







    


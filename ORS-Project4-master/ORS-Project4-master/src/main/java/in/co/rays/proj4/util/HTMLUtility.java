package in.co.rays.proj4.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import in.co.rays.proj4.bean.DropdownListBean;
import in.co.rays.proj4.model.BaseModel;


/**
 * HTML Utility class to produce HTML contents like Drop down List. 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
public class HTMLUtility {

	 /**
     * Create HTML SELECT list from MAP parameters values 
     * @param name
     * @param selectedValue
     * @param map
     * @return
     */

	 public static String getList(String name, String selectedVal,HashMap<String, String> map) 
	 {
		 StringBuffer sb=new StringBuffer( "<select style='width: 206px;  height: 30px;' class='form-control' name='" + name + "'>");
		 
		   Set<String> keys = map.keySet();
	        String val = null;
          
	        boolean select=true;
	        if (select) {

	            sb.append("<option style='width: 206px;  height: 30px;' selected value=''>-----------Select---------------</option>");
	        }

	        for (String key : keys)
	        {
	            val = map.get(key);
	            if (key.trim().equals(selectedVal))
	            {
	            	/*System.out.println("inside if block");
	            	System.out.println("key value in HTMLUtility getList method---------->"+key);
	            	System.out.println("Val value in HTMLUtility getList method---------->"+val);*/
	                sb.append("<option style=style='width: 206px;  height: 30px;' selected value='" + key + "'>" + val + "</option>");
	            } 
	            else 
	            {
	            /*	System.out.println("inside else block");
	            	System.out.println("key value in HTMLUtility getList method---------->"+key);
	            	System.out.println("Val value in HTMLUtility getList method---------->"+val);*/
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	        }
	        sb.append("</select>");
	        return sb.toString();
		 
	 }
	 
	  /**
	     * Create HTML SELECT List from List parameter
	     * 
	     * @param name
	     * @param selectedVal
	     * @param list
	     * @return
	     */

	    public static String getList(String name, String selectedVal, List list) 
	    {
	    	
	        Collections.sort(list);

	        List<DropdownListBean> dd =(List<DropdownListBean>)list;
			/*
			 * Iterator<DropdownListBean> itr = dd.iterator(); while (itr.hasNext()) {
			 * DropdownListBean dd1 = (DropdownListBean) itr.next();
			 * System.out.println("key= "+dd1.getKey()+" Value= "+dd1.getValue());
			 * 
			 * }
			 */
	        
	       // System.out.println("value of name = "+name+" value of selecyedVal = "+selectedVal);
	        StringBuffer sb = new StringBuffer("<select style='width: 206px;  height: 30px;' class='form-control' name='" + name + "'>");

	        String key = null;
	        String val = null;
	        boolean select=true;
	        if (select)
	        {

	            sb.append("<option style='width: 206px;  height: 30px;' selected value=''>-----------Select---------------</option>");
	        }

	        for (DropdownListBean obj : dd) 
	        {
	            key = obj.getKey();
	            val = obj.getValue();

	            if (key.trim().equals(selectedVal)) 
	            {
					/*
					 * System.out.println("inside if block");
					 * System.out.println("key value in HTMLUtility getList method---------->"+key);
					 * System.out.println("Val value in HTMLUtility getList method---------->"+val);
					 */
	                sb.append("<option style='width: 206px;  height: 30px;' selected value='" + key + "'>" + val + "</option>");
	            }
	            else
	            {
					/*
					 * System.out.println("inside else block");
					 * System.out.println("key value in HTMLUtility getList method---------->"+key);
					 * System.out.println("Val value in HTMLUtility getList method---------->"+val);
					 */
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	        }
	        sb.append("</select>");
	        return sb.toString();
	    }
	    
	    /**
	     * Create HTML SELECT list from MAP parameters values 
	     * @param name
	     * @param selectedValue
	     * @param map
	     * @return
	     */

	    public static String getList(String name, String selectedVal, HashMap<String, String> map, boolean select)
	    {

	        StringBuffer sb = new StringBuffer( "<select style='width: 206px;  height: 30px;' class='form-control' name='" + name + "'>");

	        Set<String> keys = map.keySet();
	        String val = null;

	        if (select) 
	        {
	            sb.append("<option selected value=''>----------------Select---------------</option>");
	        }

	        for (String key : keys) 
	        {
	            val = map.get(key);
	            if (key.trim().equals(selectedVal))
	            {
	            	/*System.out.println("inside if block");
	            	System.out.println("key value in HTMLUtility getList method---------->"+key);
	            	System.out.println("val value in HTMLUtility getList method---------->"+val);
	               */
	                sb.append("<option style='width: 206px;  height: 30px;' selected value='" + key + "'>" + val + "</option>");
	            }
	            else
	            {
	            	/*System.out.println("inside else block");
	            	System.out.println("key value in HTMLUtility getList method---------->"+key);
	            	System.out.println("Val value in HTMLUtility getList method---------->"+val);
	               */
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	        }
	        sb.append("</select>");
	        System.out.println(sb.toString());
	        return sb.toString();
	    }
	    
	    /**
	     * Returns Input Error Message with HTML tag and CSS
	     * 
	     * @param request
	     * @return
	     */


	    public static String getInputErrorMessages(HttpServletRequest request)
	    {

	        Enumeration<String> e = request.getAttributeNames();

	        StringBuffer sb = new StringBuffer("<UL>");
	        String name = null;

	        while (e.hasMoreElements()) 
	        {
	            name = e.nextElement();
	            if (name.startsWith("error."))
	            {
	                sb.append("<LI class='error'>" + request.getAttribute(name) + "</LI>");
	            }
	        }
	        sb.append("</UL>");
	        System.out.println(sb.toString());
	        return sb.toString();
	       
	    }
	    
	    /**
	     * Returns Error Message with HTML tag and CSS
	     * 
	     * @param request
	     * @return
	     */    
	    
	    public static String getErrorMessage(HttpServletRequest request)
	    {
	    	String msg=ServletUtility.getErrorMessage(request);
	    	if (!DataValidator.isNull(msg))
	    	{
	            msg = "<p class='st-error-header'>" + msg + "</p>";
	        }
	    	System.out.println(msg);
	        return msg;
	    }
	 
	    /**
	     * Returns Success Message with HTML tag and CSS
	     * 
	     * @param request
	     * @return
	     */

	    public static String getSuccessMessage(HttpServletRequest request)
	    {
	        String msg = ServletUtility.getSuccessMessage(request);
	        if (!DataValidator.isNull(msg)) 
	        {
	            msg = "<p class='st-success-header'>" + msg + "</p>";
	        }
	        System.out.println(msg);
	        return msg;
	    }

	    /**
	     * Creates submit button if user has access permission.
	     * 
	     * @param label
	     * @param access
	     * @param request
	     * @return
	     */
	 
	    public static String getSubmitButton(String label, boolean access, HttpServletRequest request) 
	    {

	        String button = "";

	        if (access)
	        {
	            button = "<input type='submit' name='operation'    value='" + label + "' >";
	        }
	        return button;
	    }
	 
	    public static String getCommonFields(HttpServletRequest request) 
	    {

	        BaseModel model = ServletUtility.getModel(request);

	        StringBuffer sb = new StringBuffer();

	        sb.append("<input type='hidden' name='id' value=" + model.getId() + ">");
	        
//	         sb.append("<input type='hidden' name='createdBy' value=" + DataUtility.getString(model.getCreatedBy()) + ">");
//	         sb.append("<input type='hidden' name='modifiedBy' value=" + DataUtility.getString(model.getModifiedBy()) + ">");
//	         sb.append("<input type='hidden' name='createdDatetime' value=" + DataUtility.getTimeStamp(model.getCreatedDatetime()) + ">");
//	         sb.append("<input type='hidden' name='modifiedDatetime' value=" + DataUtility.getTimeStamp(model.getModifiedDatetime()) + ">");
	        System.out.println(sb.toString());
	        return sb.toString();
	    }
	 
	
}

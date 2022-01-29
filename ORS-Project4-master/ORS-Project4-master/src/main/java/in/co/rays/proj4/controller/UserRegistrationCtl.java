package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

 // User functionality Controller. Performs operation for add, update and get User

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */ 
@ WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})
public class UserRegistrationCtl extends BaseCtl
{

	
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_BACK = "Back";
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	 /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */

     
	@Override
	protected boolean validate(HttpServletRequest req)
	{//System.out.println("Step 3");
		log.debug("UserRegistrationCtl Method validate Started");
       boolean pass=true;
       String login=req.getParameter("login");
       String dob=req.getParameter("dob");
       
       if(DataValidator.isNull(req.getParameter("firstName")))
       {
    	   req.setAttribute("firstName",PropertyReader.getValue("error.require","First Name") );
    	   pass=false;
       }
       else if (DataValidator.isName(req.getParameter("firstName"))) {
			req.setAttribute("firstName",PropertyReader.getValue("error.name", "First Name"));
			pass = false;
		}
       
       
       if(DataValidator.isNull(req.getParameter("lastName")))
       {
    	   req.setAttribute("lastName",PropertyReader.getValue("error.require","Last Name"));
    	   pass=false;
       }
       
       
       else if (DataValidator.isName(req.getParameter("lastName"))) {
			req.setAttribute("lastName",PropertyReader.getValue("error.name", "Last Name"));
			pass = false;
		}
       
       if(DataValidator.isNull(login))
       {
    	   req.setAttribute("login", PropertyReader.getValue("error.require", "Login ID"));
    	   pass=false;
       }
       else if (!DataValidator.isEmailId(login))
       {
    	   req.setAttribute("login",PropertyReader.getValue("error.email", "Login ID "));
           pass = false;   
	   }
       
       
       
       if (DataValidator.isNull(req.getParameter("gender")))
       {
           req.setAttribute("gender",PropertyReader.getValue("error.require", "Gender"));
           pass = false;
       }
       
       if (DataValidator.isNull(dob))
       {
           req.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth"));
           pass = false;
       } 
        /*if (DataValidator.isDate(dob))
       {
           req.setAttribute("dob",PropertyReader.getValue("error.date", "Date Of Birth"));
           pass = false;
       }*/
       
       if (DataValidator.isNull(req.getParameter("mobileNo"))) {
           req.setAttribute("mobileNo",
                   PropertyReader.getValue("error.require", "Mobile No"));
           pass = false;
       }
       else if (DataValidator.isPhoneNo(req.getParameter("mobileNo")))
       {
    	   System.out.println("isPhoneNo is called");
    	   req.setAttribute("mobileNo",
                   PropertyReader.getValue("error.number", "Mobile No"));
    	   pass = false;
       }

       if(DataValidator.isNull(req.getParameter("password")))
       {
    	   req.setAttribute("password",PropertyReader.getValue("error.require", "Password") );
    	   pass=false;
       }
       else if (!DataValidator.isPassword(req.getParameter("password"))) {
			req.setAttribute("password",PropertyReader.getValue("error.password", "Password"));
			return false;
		}
       
       if(DataValidator.isNull(req.getParameter("confirmPassword")))
       {
    	   req.setAttribute("confirmPassword", PropertyReader.getValue("error.require","Confirm Password"));
    	   pass=false;
       }
       else  if (!req.getParameter("password").equals(req.getParameter("confirmPassword"))&& !"".equals(req.getParameter("confirmPassword")))
       {
    	   req.setAttribute("confirmPassword",PropertyReader.getValue("error.confirmPassword", "ConfirmPassword"));
           pass = false;
       }
       System.out.println("Step 4"+pass);

       return pass;
     
	} 
	  /**
     * Populates bean object from request parameters
     * 
     * @param request
     * @return
     */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) 
	{System.out.println("Step 5");
		 log.debug("UserRegistrationCtl Method populatebean Started");

	        UserBean bean = new UserBean();

	        bean.setId(DataUtility.getLong(request.getParameter("id")));
	        
	        bean.setRoleId(RoleBean.STUDENT);

	        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

	        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

	        bean.setLogin(DataUtility.getString(request.getParameter("login")));

	        bean.setPassword(DataUtility.getString(request.getParameter("password")));

	        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

	        bean.setGender(DataUtility.getString(request.getParameter("gender")));

	        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
	        System.out.println(bean.getDob());
	        bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

	        populateDTO(bean, request);

	        log.debug("UserRegistrationCtl Method populatebean Ended");

	        return bean;

	}

	
	/**
     * Contains DIsplay logics
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		  log.debug("UserRegistrationCtl Method doGet Started");
	        ServletUtility.forward(getView(), req, resp);	
	}

	/**
     * Contains Submit logics
     */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		//System.out.println("in get method");
        log.debug("UserRegistrationCtl Method doPost Started");
        String op = DataUtility.getString(req.getParameter("operation"));
       // System.out.println(op);
        // get model
        UserModel model = new UserModel();
      //  long id = DataUtility.getLong(req.getParameter("id"));
        if(OP_BACK.equalsIgnoreCase(op))
        {
        	System.out.println("back===="+op);
        	 ServletUtility.redirect(ORSView.LOGIN_CTL, req, resp);
             return;
        }
        else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
        	//System.out.println("dopost signup operation start");
        	//System.out.println(op);
            UserBean bean = (UserBean) populateBean(req);
            try {
                long pk = model.add(bean);
              //  bean.setId(pk);
               // req.getSession().setAttribute("UserBean", bean);
                
                ServletUtility.setSuccessMessage("User Registered Successfully", req);
       
                ServletUtility.forward(getView(), req, resp);
                return;
            } catch (ApplicationException e) {
            	e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, req, resp);
                return;
            } catch (DuplicateRecordException e) {
            	e.printStackTrace();
            	log.error(e);
                ServletUtility.setBean(bean, req);
                ServletUtility.setErrorMessage("Login id already exists", req);
                ServletUtility.forward(getView(), req, resp);
            } catch (DatabaseException e) {
            	e.printStackTrace();
            	log.error(e); 
				
			}
           // System.out.println("dopost signup operation end");
        }
        
        log.debug("UserRegistrationCtl Method doPost Ended");
	
	}

	
	 /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	
	protected String getView()
	{
		return ORSView.USER_REGISTRATION_VIEW;
	}
	 
	
	
}

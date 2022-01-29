package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@ WebServlet(name="ForgetPasswordCtl",urlPatterns={"/ForgetPasswordCtl"})

public class ForgetPasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);
	
	  /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
  
	@Override
	protected boolean validate(HttpServletRequest request)
	{
		log.debug("ForgetPasswordCtl Method validate Started");
        boolean pass = true;
        String login=request.getParameter("login");
        if(DataValidator.isNull(login))
        {
        request.setAttribute("login", PropertyReader.getValue("error.require","Email id"));   	
        	pass=false;
        }
        else if (!DataValidator.isEmailId(login))
        {
        	request.setAttribute("login",PropertyReader.getValue("error.email", "Email id"));
            pass = false;
		}
        log.debug("ForgetPasswordCtl Method validate Ended");
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
	{
		log.debug("ForgetPasswordCtl Method populatebean Started");

        UserBean bean = new UserBean();
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        log.debug("ForgetPasswordCtl Method populatebean Ended");
        return bean;

	}
	
	 /**
     * Contains display logics
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		log.debug("ForgetPasswordCtl Method doGet Started");
        ServletUtility.forward(getView(), request, response);		
	}

	 /**
	    * Contains submit logics
	    */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        log.debug("ForgetPasswordCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        
        UserBean bean = (UserBean) populateBean(request);
        System.out.println("loginid-------------"+bean.getLogin());
        
        // get model
        UserModel model = new UserModel();

        if (OP_GO.equalsIgnoreCase(op)) {
        	
            try
            {	
                boolean flag=model.forgetPassword(bean.getLogin());
                System.out.println("loginid-------------"+bean.getLogin());
                System.out.println("forgetpwd---------"+flag);
                ServletUtility.setSuccessMessage("Password has been sent to your email id.", request);
            }
            catch (RecordNotFoundException e) 
            {
                ServletUtility.setErrorMessage(e.getMessage(), request);
                log.error(e);
            }
            catch (ApplicationException e)
            {
            	log.error(e);
               ServletUtility.handleException(e, request, response);
               // ServletUtility.setErrorMessage("Oops! Please Check your net Connection", request);
               // ServletUtility.forward(getView(), request, response);
               return; 
            } 
            catch (DatabaseException e)
            {
				e.printStackTrace();
			}
            
        }
        ServletUtility.forward(getView(), request, response);
        log.debug("ForgetPasswordCtl Method doPost Ended");

	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView()
	{
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}

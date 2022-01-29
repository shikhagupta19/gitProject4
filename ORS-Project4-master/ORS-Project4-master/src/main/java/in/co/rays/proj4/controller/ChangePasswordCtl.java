package in.co.rays.proj4.controller;


import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ChangePasswordCtl;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.controller.ORSView;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@ WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})

public class ChangePasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	 public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";
     private static Logger log = Logger.getLogger(ChangePasswordCtl.class);


     /**
      * Validates input data entered by User
      * 
      * @param request
      * @return
      */
   
   	@Override
	protected boolean validate(HttpServletRequest request) {
		
   		log.debug("ChangePasswordCtl Method validate Started");

        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {

            return pass;
        }
        if (DataValidator.isNull(request.getParameter("oldPassword"))) {
            request.setAttribute("oldPassword",
                    PropertyReader.getValue("error.require", "Old Password"));
            pass = false;
        }else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword",
					PropertyReader.getValue("error.password", "Old Password"));
			return false;
		}
        if (DataValidator.isNull(request.getParameter("newPassword"))) {
            request.setAttribute("newPassword",
                    PropertyReader.getValue("error.require", "New Password"));
            pass = false;
        }
        else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword",
					PropertyReader.getValue("error.password", "New Password"));
			return false;
		}
        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue(
                    "error.require", "Confirm Password"));
            pass = false;
        }
        if (!request.getParameter("newPassword").equals(
                request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
        	request.setAttribute("confirmPassword",PropertyReader.getValue("error.confirmPassword", "ConfirmPassword"));
            pass = false;        }

        log.debug("ChangePasswordCtl Method validate Ended");

        return pass;
	}

    /**
     * Populates bean object from request parameters
     * 
     * @param request
     * @return
     */


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("ChangePasswordCtl Method populatebean Started");

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request
                .getParameter("oldPassword")));

        bean.setConfirmPassword(DataUtility.getString(request
                .getParameter("confirmPassword")));

        populateDTO(bean, request);

        log.debug("ChangePasswordCtl Method populatebean Ended");

        return bean;

	}



	 /**
    * Contains display logics
    */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   	{
		 ServletUtility.forward(getView(), request, response);
		 
	}

	 /**
     * Contains Submit logics
     */
   
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   	{
	
   	 HttpSession session = request.getSession(true);

     log.debug("ChangePasswordCtl Method doGet Started");

     String op = DataUtility.getString(request.getParameter("operation"));

     // get model
     UserModel model = new UserModel();

     UserBean bean = (UserBean) populateBean(request);

     UserBean UserBean = (UserBean) session.getAttribute("user");

     String newPassword = (String) request.getParameter("newPassword");

     long id = UserBean.getId();
    // System.out.println("ID----------"+id);

     if (OP_SAVE.equalsIgnoreCase(op)) {
         try {
             boolean flag = model.changePassword(id, bean.getPassword(), newPassword);
             if (flag == true) {
                 bean = model.findByLogin(UserBean.getLogin());
                 session.setAttribute("user", bean);
                 ServletUtility.setBean(bean, request);
                 ServletUtility.setSuccessMessage( "Password has been changed Successfully.", request);
                 ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
             return;
             
             }
         } 
         catch (ApplicationException e) {
             log.error(e);
             ServletUtility.handleException(e, request, response);
             return;

         }
         catch (RecordNotFoundException e) 
         {
             ServletUtility.setErrorMessage("Old password is invalid", request);
         } 
         catch (DatabaseException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     } else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
         ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
         return;
     }

     ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
     log.debug("ChangePasswordCtl Method doGet Ended");

	}


    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */


	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}

package in.co.rays.proj4.controller;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
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
 * Login functionality Controller. Performs operation for Login
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })

public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);
	  /**
     * Validates input data entered by User
     * 
     * @param request
     * @return--
     */
  
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		System.out.println("LoginCtl validate called");
		log.debug("LoginCtl Method validate Started");
		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}
        String login = request.getParameter("login");
		String password = request.getParameter("password");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmailId(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id "));
			pass = false;
		}
		if (DataValidator.isNull(password)) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPassword(password)) {
			request.setAttribute("password", PropertyReader.getValue("error.password", "Password "));
			pass = false;
		}

		log.debug("LoginCtl Met3hod validate Ended");

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
		log.debug("LoginCtl Method populatebean Started");
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}
	
	 /**
    * Contains display logics
    */



	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("LoginCtl inside doGet() method");
		
		HttpSession session = request.getSession();
		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		
		if (OP_LOG_OUT.equalsIgnoreCase(op)) {

			session = request.getSession();

			session.invalidate();
			ServletUtility.setSuccessMessage("You have been logged out successfully !!", request);
			// request.setAttribute("msg", "You have been logged out
			// successfully !!");
			ServletUtility.forward(getView(), request, response);
			// ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);

			return;

		}
		
		
		// getModel
				UserModel user = new UserModel();
				RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			UserBean userbean;
			try {
				userbean = user.findByPK(id);
				ServletUtility.setBean(userbean, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Ended");
	}

	/**
	 * Submitting or login action performing
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LoginCtl inside doPost() method");
		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		String uri = request.getParameter("uri");
		// getModel

		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {

				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {
					session.setAttribute("user", bean);
					long rollId = bean.getRoleId();
					System.out.println("roleid=" + bean.getRoleId());
				//	System.out.println("roleName=" + bean.getRoleName());
					RoleBean rolebean = role.findByPK(rollId);
					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
					
					if (uri.equals("") || uri.equals(null) || uri == "" || "null".equalsIgnoreCase(uri)) {
						ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
						
					} else {
						System.out.println("uri" + uri);
						ServletUtility.redirect(uri, request, response);
						return;
					}
					return;
					
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {System.out.println("login catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
package in.co.rays.proj4.controller;


import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/***
 * 
 * Base controller class of project. It contain (1) Generic operations (2) Generic constants (3) Generic work flow
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public abstract class BaseCtl extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String OP_BACK = "Back";
    public static final String OP_SAVE = "Save";
    public static final String OP_CANCEL = "Cancel";
    public static final String OP_DELETE = "Delete";
    public static final String OP_LIST = "List";
    public static final String OP_SEARCH = "Search";
    public static final String OP_VIEW = "View";
    public static final String OP_NEXT = "Next";
    public static final String OP_PREVIOUS = "Previous";
    public static final String OP_NEW = "New";
    public static final String OP_GO = "Go";
    public static final String OP_LOG_OUT = "Logout";
    public static final String OP_RESET = "Reset";
    public static final String OP_UPDATE = "Update";


    /**
     * Success message key constant
     */
    public static final String MSG_SUCCESS = "success";

    /**
     * Error message key constant
     */
    public static final String MSG_ERROR = "error";

    /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
    protected boolean validate(HttpServletRequest request) {
        System.out.println("baseCtl validate() called");
    	return true;
    }

    /**
     * Loads list and other data required to display at HTML form
     * 
     * @param request
     */
    protected void preload(HttpServletRequest request) {
    }

    /**
     * Populates bean object from request parameters
     * 
     * @param request
     * @return
     */
    protected BaseBean populateBean(HttpServletRequest request) {
        return null;
    }

    /**
     * Populates Generic attributes in DTO
     * 
     * @param dto
     * @param request
     * @return
     */

    protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

        String createdBy = request.getParameter("createdBy");
        String modifiedBy = null;

        UserBean userbean = (UserBean) request.getSession()
                .getAttribute("user");

        if (userbean == null) {
            // If record is created without login
            createdBy = "root";
            modifiedBy = "root";
        } else {

            modifiedBy = userbean.getLogin();

            // If record is created first time
            if ("null".equalsIgnoreCase(createdBy)
                    || DataValidator.isNull(createdBy)) {
                createdBy = modifiedBy;
            }

        }

        dto.setCreatedBy(createdBy);
        dto.setModifiedBy(modifiedBy);

        long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

        if (cdt > 0) {
            dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
        } else {
            dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
        }

        dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

        return dto;
    }

   
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	String op = DataUtility.getString(request.getParameter("operation"));
    	//System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    	//System.out.println("Step 1"+op);
        // Load the preloaded data required to display at HTML form
        preload(request);

        
       // System.out.println("opration----->"+op);

        // Check if operation is not DELETE, VIEW, CANCEL, and NULL ,reset then
        // perform input data validation

        if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op)
                && !OP_VIEW.equalsIgnoreCase(op)
                && !OP_DELETE.equalsIgnoreCase(op)&& !OP_RESET.equalsIgnoreCase(op)&& !OP_BACK.equalsIgnoreCase(op)) 
        {	
        	System.out.println("Service method called");
            // Check validation, If fail then send back to page with error
            // messages
        	//System.out.println("Step 2"+op);
            if (!validate(request))
            {
                BaseBean bean = (BaseBean) populateBean(request);
                ServletUtility.setBean(bean, request);
                ServletUtility.forward(getView(), request, response);
               // System.out.println("Step 6");
                return;
            }
        }
       // System.out.println("service--->"+request.getMethod());
        super.service(request, response);
    }

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */
    protected abstract String getView();

}
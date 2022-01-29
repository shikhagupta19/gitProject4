package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class CourseCtl
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name="CourseCtl",urlPatterns={"/ctl/CourseCtl"})

public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CourseCtl.class);
	  /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
  
	@Override
	protected boolean validate(HttpServletRequest request)
	{
		log.debug("CourseCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }
        
        else if (DataValidator.isName(request.getParameter("name"))) {
 			request.setAttribute("name",PropertyReader.getValue("error.name", "Course Name"));
 			pass = false;
 		}

        
        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description",
                    PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        log.debug("CourseCtl Method validate Ended");

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
        log.debug("CourseCtl Method populatebean Started");

        CourseBean bean = new CourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        log.debug("CourseCtl Method populatebean Ended");

        return bean;
	
		
	}
	
	 /**
    * Contains display logics
    */


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		 log.debug("CourseCtl Method doGet Started");

	        System.out.println("course ctl");

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        CourseModel model = new CourseModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
	        if (id > 0 || op != null) {
	            CourseBean bean;
	            try {
	                bean = model.findByPK(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }
	        ServletUtility.forward(getView(), request, response);
	        log.debug("CourseCtl Method doGetEnded");
	
	}
	 /**
	    * Contains submit logics
	    */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        log.debug("CourseCtl Method doGet Started");

        System.out.println("In do get");

        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        CourseModel model = new CourseModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            CourseBean bean = (CourseBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                    
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully updated",   request);
                    ServletUtility.forward(getView(), request, response);
                } else {
                    long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
	                ServletUtility.forward(getView(), request, response);
                    bean.setId(pk);
                }
                return;
/*
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved",
                        request);*/

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            CourseBean bean = (CourseBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CourseCtl Method doPOst Ended");

	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView() {
	
		return ORSView.COURSE_VIEW;
	}

}

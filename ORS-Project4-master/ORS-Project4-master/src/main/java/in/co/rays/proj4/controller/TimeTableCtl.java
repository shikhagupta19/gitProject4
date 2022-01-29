package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimeTableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class TimeTableCtl
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name="TimeTableCtl",urlPatterns="/ctl/TimeTableCtl")

public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	 private static Logger log = Logger.getLogger(TimeTableCtl.class);
	  /**
	     * Validates input data entered by User
	     * 
	     * @param request
	     * @return
	     */
	  
	
  	@Override
	protected boolean validate(HttpServletRequest request) {
  		log.debug("TimeTableCtl Method validate Started");

        boolean pass = true;
        
        
        if (DataValidator.isNull(request.getParameter("courseName"))) {
            request.setAttribute("courseName",
                    PropertyReader.getValue("error.require", "courseName"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("subjectName"))) {
            request.setAttribute("subjectName",
                    PropertyReader.getValue("error.require", "subjectName"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("examDate"))) {
            request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("examTime"))) {
            request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("semester"))) {
            request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
            pass = false;
        }
        log.debug("TimeTableCtl Method validate Ended");

        return pass;

	}

    /**
     * Loads list and other data required to display at HTML form
     * 
     * @param request
     */

	@Override
	protected void preload(HttpServletRequest request) {
	 	CourseModel model = new CourseModel();
        try {
            List l = model.list();
            request.setAttribute("courseList", l);            
        } catch (ApplicationException e) {
            log.error(e);
        }
        SubjectModel model1 = new SubjectModel();
        try {
            List l = model1.list();
            request.setAttribute("subjectList", l);            
        } catch (ApplicationException e) {
            log.error(e);
        }

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
        log.debug("TimeTableCtl Method populatebean Started");

        TimeTableBean bean = new TimeTableBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setCourseId(DataUtility.getLong(request.getParameter("courseName")));
        
        bean.setSemester(DataUtility.getString(request.getParameter("semester")));

        bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectName")));

        bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
        
        bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

        

        populateDTO(bean, request);

        System.out.println("TimeTableCtl Method populatebean Ended");

        log.debug("TimeTableCtl Method populatebean Ended");

        return bean;
	
	}
	
	 /**
    * Contains display logics
    */


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  	{
        log.debug("TimeTableCtl Method doGet Started");

       // String op = DataUtility.getString(request.getParameter("operation"));
        TimeTableModel model = new TimeTableModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (id > 0) {
        	TimeTableBean bean;
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
        
        
        log.debug("TimeTableCtl Method doGet Ended");
           

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        log.debug("TimeTableCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        TimeTableModel model = new TimeTableModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op))  {

        	TimeTableBean bean = (TimeTableBean) populateBean(request);
        	  try {
                  if (id > 0) {
                      model.update(bean);
                      ServletUtility.setBean(bean, request);
                      ServletUtility.setSuccessMessage("Data is successfully Updated",request);

                  } else {
                      long pk = model.add(bean);
                    //  ServletUtility.setBean(bean, request);
                      ServletUtility.setSuccessMessage("Data is successfully saved",request);

                      /*bean.setId(pk);*/
                  }
                  
              } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("TimeTable already exist", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

        	TimeTableBean bean = (TimeTableBean) populateBean(request);
            System.out.println("in try");
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request,response);
                System.out.println("in try");
                return;
            } catch (ApplicationException e) {
                System.out.println("in catch");
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
            return;

        }
        ServletUtility.forward(getView(), request, response);
        log.debug("TimeTableCtl Method doGetEnded");
	
	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView() {
		
		return ORSView.TIMETABLE_VIEW;
	}

}

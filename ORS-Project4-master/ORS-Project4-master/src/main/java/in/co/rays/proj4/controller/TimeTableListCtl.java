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
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimeTableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class TimeTableListCtl
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name="TimeTableListCtl",urlPatterns="/ctl/TimeTableListCtl")

public class TimeTableListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
   	
	 private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	 
	 
	@Override
	protected void preload(HttpServletRequest request)
	{
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
        TimeTableBean bean = new TimeTableBean();
        
    	bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
    	bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
    	bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
    	bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
    	bean.setExamTime(DataUtility.getString(request.getParameter("ExamTime")));
    	bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
        return bean;
	}
	 /**
     * Contains Display logics
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
   	{
	 	System.out.println("in do get timetable........");
        log.debug("TimeTableListCtl doGet Start");
        List list = null;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        System.out.println("timetable page size");
        TimeTableBean bean = (TimeTableBean) populateBean(request);
        //String op = DataUtility.getString(request.getParameter("operation"));
        TimeTableModel model = new TimeTableModel();
        try {
            list = model.search(bean, pageNo, pageSize);
        System.out.println("TimeTableListCtl list");
           // ServletUtility.setList(list, request);
            System.out.println(list);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
   System.out.println("in do get timetable ends........");
        log.debug("TimeTableListCtl doGet End");

	}
	/**
	    * Contains submit logics
	    */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		  log.debug("TimeTableListCtl doPost Start");
	        
	        List list = null;
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	        System.out.println("page no inside dopost method of timetablelist Ctl-------->"+pageNo);
	        
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	        System.out.println("page size inside dopost method of timetablelist Ctl-------->"+pageSize);
	        TimeTableBean bean = (TimeTableBean) populateBean(request);
	        try{
	        	if(bean.getCourseId()>0){
	        		CourseModel model=new CourseModel();
	        		CourseBean cbean=model.findByPK(bean.getCourseId());
	        		bean.setCourseName(cbean.getName());
	        	}
	        	if(bean.getSubjectId()>0){
	        		SubjectModel model=new SubjectModel();
	        		SubjectBean sbean=model.findByPK(bean.getSubjectId());
	        		bean.setSubjectName(sbean.getName());
	        	}
	        	
	        	
	        }	catch(Exception e){
	        		
	        	}
	        
	        String op = DataUtility.getString(request.getParameter("operation"));
	        String[] ids = request.getParameterValues("checkAll");
	        TimeTableModel model = new TimeTableModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }

	            } else if (OP_NEW.equalsIgnoreCase(op)) {
	                ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
	                return;
	            } else if (OP_DELETE.equalsIgnoreCase(op)) {
	                pageNo = 1;
	                if (ids != null && ids.length > 0) {
	                	TimeTableBean deletebean = new TimeTableBean();
	                    for (String id : ids) {
	                        deletebean.setId(DataUtility.getInt(id));
	                        model.delete(deletebean);
	                        ServletUtility.setSuccessMessage("Record Deleted Successfully", request);
	                        
	                    }
	                } else {
	                    ServletUtility.setErrorMessage("Select at least one record", request);
	                }
	            }
	            else if (OP_BACK.equalsIgnoreCase(op)) {
	                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
	                return;
	            }
	            else if (OP_RESET.equalsIgnoreCase(op)) {
	    			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
	    			return;
	    		}
	            list = model.search(bean, pageNo, pageSize);
	      
	            if (list == null || list.size() == 0&& !OP_DELETE.equalsIgnoreCase(op)) {
	                ServletUtility.setErrorMessage("No record found ", request);
	            }
	            ServletUtility.setList(list, request);

	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);

	        } catch (ApplicationException e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            e.printStackTrace();
	            return;
	        }
	        log.debug("TimeTableListCtl doPost End");	
	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView() {
		
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}

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
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
import in.co.rays.proj4.controller.ORSView;

/**
 * Servlet implementation class SubjectListCtl
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="SubjectListCtl",urlPatterns="/ctl/SubjectListCtl")

public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
	 
   	@Override
	protected void preload(HttpServletRequest request)
   	{
   	  	CourseModel model = new CourseModel();
        try {
            List clist = model.list();
        	request.setAttribute("courseList", clist);
                       
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
	  	SubjectBean bean = new SubjectBean();

    	bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
    	bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
        bean.setName(DataUtility.getString(request.getParameter("name")));

        return bean;
	}
	/**
	   * Contains display logics
	   */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   	{
        log.debug("SubjectListCtl doGet Start");
        List list = null;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        SubjectBean bean = (SubjectBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        SubjectModel model = new SubjectModel();
        try {
            list = model.search(bean, pageNo, pageSize);
            ServletUtility.setList(list, request);
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
        log.debug("SubjectListCtl doGet End");

	}
	/**
	    * Contains submit logics
	    */


   protected void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
   {
       log.debug("SubjectListCtl doPost Start");
       List list = null;
       int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
       int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
       pageNo = (pageNo == 0) ? 1 : pageNo;
       pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
               .getValue("page.size")) : pageSize;
       SubjectBean bean = (SubjectBean) populateBean(request);
       try{
       	if(bean.getCourseId()>0){
       		CourseModel model=new CourseModel();
       		CourseBean cbean=model.findByPK(bean.getCourseId());
       		bean.setCourseName(cbean.getName());
       	}
     
       }	catch(Exception e){
       		
       	}
     
       String op = DataUtility.getString(request.getParameter("operation"));
       String[] ids = request.getParameterValues("ids");
       SubjectModel model = new SubjectModel();

       try {

           if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                   || "Previous".equalsIgnoreCase(op) || "New".equalsIgnoreCase(op)|| "Delete".equalsIgnoreCase(op)) {

               if (OP_SEARCH.equalsIgnoreCase(op)) {
                   pageNo = 1;
               } else if (OP_NEXT.equalsIgnoreCase(op)) {
                   pageNo++;
               } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                   pageNo--;
               }
               else if (OP_NEW.equalsIgnoreCase(op)) {
                   ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
                   return;
               }
               else if (OP_DELETE.equalsIgnoreCase(op)) {
                   pageNo = 1;
                   if (ids != null && ids.length > 0) {
                   	SubjectBean deletebean = new SubjectBean();
                       for (String id : ids) {
                           deletebean.setId(DataUtility.getInt(id));
                           model.delete(deletebean);
                           ServletUtility.setSuccessMessage("Subject Deleted Successfully", request);
                       }
                   }
                   else {
                       ServletUtility.setErrorMessage("Select at least one record", request);
                   }
               }
           }else if (OP_BACK.equalsIgnoreCase(op)) {
               ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request,
                       response);
               return;
           }
           else if (OP_RESET.equalsIgnoreCase(op)) {
   			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
   			return;
   		}
           
           list = model.search(bean, pageNo, pageSize);
         //  ServletUtility.setList(list, request);
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
           return;
       }
       log.debug("SubjectListCtl doPost End");

   }
	
	
	

   /**
    * Returns the VIEW page of this Controller
    * 
    * @return
    */

	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_LIST_VIEW;
	}
}
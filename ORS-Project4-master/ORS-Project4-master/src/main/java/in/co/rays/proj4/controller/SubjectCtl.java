package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
import in.co.rays.proj4.controller.ORSView;

/**
 * Servlet implementation class SubjectCtl
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="SubjectCtl",urlPatterns="/ctl/SubjectCtl")

public class SubjectCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	
	
	private static Logger log=Logger.getLogger(SubjectCtl.class);
	
	  /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
  
	@Override
	protected boolean validate(HttpServletRequest request)
	{
		 log.debug("SubjectCtl Method validate Started");
         System.out.println("SubjectCtl Method validate Started");
		
         boolean pass=true;
         if(DataValidator.isNull(request.getParameter("courseId")))
         {
        	 request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
        	 pass=false;
         }
         
         if(DataValidator.isNull(request.getParameter("name")))
         {
        	 request.setAttribute("name",PropertyReader.getValue("error.require","Subject Name" ));
        	 pass=false;
         }
         else if (!DataValidator.isName(request.getParameter("name"))) {
 			request.setAttribute("name",PropertyReader.getValue("error.name", "Subject Name"));
 			pass = false;
 		}
        
        
         log.debug("SubjectCtl Method validate Ended");
         System.out.println("SubjectCtl Method validate Ended");

         
         return pass ;
        
	}

    /**
     * Loads list and other data required to display at HTML form
     * 
     * @param request
     */

	
	@Override
	protected void preload(HttpServletRequest request)
	{	CourseModel model=new CourseModel();
		try
		{
			List l=model.list();
			request.setAttribute("courseList", l);
		}catch(ApplicationException e)
		{
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
		  log.debug("SubjectCtl Method populatebean Started");
	        System.out.println("SubjectCtl Method populatebean Started"); 
	        SubjectBean bean = new SubjectBean();

	        bean.setId(DataUtility.getLong(request.getParameter("id")));
	        
	        bean.setName(DataUtility.getString(request.getParameter("name")));
	
	        
	        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
	/*        bean.setDescription(DataUtility.getString(request
	                .getParameter("description")));
	*/
	        populateDTO(bean, request);

	        log.debug("SubjectCtl Method populatebean Ended");
	        System.out.println("SubjectCtl Method populatebean Ended");
	        return bean;

	        
	}
	
	 /**
    * Contains display logics
    */


	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		  log.debug("SubjectCtl Method doGet Started");

	        System.out.println("Subject ctl");

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        SubjectModel model = new SubjectModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
	        if (id > 0 || op != null) {
	        	SubjectBean bean;
	            try {
	                bean = model.findByPK(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	            	e.printStackTrace();
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }
	        ServletUtility.forward(getView(), request, response);
	        log.debug("SubjectCtl Method doGetEnded");

        }
	
	
	
		
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        log.debug("SubjectCtl Method doGet Started");

        System.out.println("In do Post method");

        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        SubjectModel model = new SubjectModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

        	SubjectBean bean = (SubjectBean) populateBean(request);
        	
            try {
                if (id > 0) {
                	System.out.println(bean.getCourseId());
                    model.update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
                    ServletUtility.forward(getView(), request, response);
                    
                } else {
                	
                    long pk = model.add(bean);
                    System.out.println("pk in subject ctl"+pk);
                    ServletUtility.setSuccessMessage("Data is successfully saved",request);
	                ServletUtility.forward(getView(), request, response);
                    bean.setId(pk);
                }

                return;
                /*ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Data is successfully saved",
                        request);
*/
            } catch (ApplicationException e) {
            	e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
            	e.printStackTrace();
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

        	SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request,
                        response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;

        }

        ServletUtility.forward(getView(), request, response);
        System.out.println("SubjectCtl Method doPOst Ended");
        log.debug("SubjectCtl Method doPOst Ended");

	}


    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}

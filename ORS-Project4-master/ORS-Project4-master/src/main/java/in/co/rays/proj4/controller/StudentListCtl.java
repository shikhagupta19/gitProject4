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
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
import in.co.rays.proj4.controller.ORSView;
/**
 * Student List functionality Controller. Performs operation for list, search
 * and delete operations of Student
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name="StudentListCtl",urlPatterns="/ctl/StudentListCtl")

public class StudentListCtl extends BaseCtl{
	
	private static Logger log= Logger.getLogger(StudentListCtl.class);
	
	
	
	/**
   	 *Preload of city in CollegeList
   	 */
   	@Override
	protected void preload(HttpServletRequest request) {
		// TODO Auto-generated method stub

		StudentModel smodel = new StudentModel();
		try {
			List slist=smodel.list();
			request.setAttribute("slist", slist);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		StudentBean bean= new StudentBean();
		
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setId(DataUtility.getInt(request.getParameter("studentId")));
		return bean;
	}
	/**
	   * Contains display logics
	   */

	protected void doGet (HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
	{
        log.debug("StudentListCtl doGet Start");
        List list = null;

        int pageNo = 1;
        
        int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
        System.out.println("page size-------------------------------------------->"+pageSize);
        StudentBean bean= (StudentBean)populateBean(request);
		
        String op=DataUtility.getString(request.getParameter("operation"));
        
        StudentModel model=new StudentModel();
        try{
        	list=model.search(bean, pageNo, pageSize);  
        	ServletUtility.setList(list, request);
        	
        	if(list==null||list.size()==0)
        	{
        		ServletUtility.setErrorMessage("No record found", request);
        	
        	}
        	ServletUtility.setPageNo(pageNo, request);
        	ServletUtility.setPageSize(pageSize, request);
        	ServletUtility.forward(getView(), request, response);
        
        }catch(ApplicationException e){
        	log.error(e);
        	ServletUtility.handleException(e, request, response);
        	return;
        }
        log.debug("StudentListCtl doGet End");

	}

	/**
	    * Contains submit logics
	    */

	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
        log.debug("StudentListCtl doPost Start");
        List list = null;
        
        int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize=DataUtility.getInt(request.getParameter("pageSize"));
        System.out.println("page size1-------------------------------------------->"+pageSize);
        pageNo=(pageNo==0)?1:pageNo;
        pageSize=(pageSize==0)? DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
System.out.println("page size-------------------------------------------->"+pageSize);
        StudentBean bean=(StudentBean)populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        StudentModel model = new StudentModel();
        
        try{
        	if(OP_SEARCH.equalsIgnoreCase(op)||"NEXT".equalsIgnoreCase(op)||"previous".equalsIgnoreCase(op))
        	{
        		if(OP_SEARCH.equalsIgnoreCase(op))
        				{
        					pageNo=1;
        				}
        		else if(OP_NEXT.equalsIgnoreCase(op))
        		{
        			pageNo++;
        		}
        		else if(OP_PREVIOUS.equalsIgnoreCase(op))
        		{
        			pageNo--;
        		}
        	}
        	else if(OP_NEW.equalsIgnoreCase(op))
        	{
        		ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
        		return;
        	}
        	else if(OP_DELETE.equalsIgnoreCase(op)){
        		pageNo=1;
        		
        		if(ids!=null)
        		{
        			StudentBean deleteBean=new StudentBean();
        			
        			for(String id:ids)
        			{
        				deleteBean.setId(DataUtility.getInt(id));
        				model.delete(deleteBean);
        				ServletUtility.setSuccessMessage("Record Deleted Successfully", request);

        			}
        		}
        		else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }
        	}
        		else if(OP_BACK.equalsIgnoreCase(op)){
        			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request,
                            response);
                    return;
        			
        		}
        		else if(OP_RESET.equalsIgnoreCase(op)){
        			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request,
                            response);
                    return;
        			
        		}
        	  list = model.search(bean, pageNo, pageSize);
              ServletUtility.setList(list, request);
              if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
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
          log.debug("StudentListCtl doGet End");


        	
        
	}
	

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

	
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_LIST_VIEW;
	}

}

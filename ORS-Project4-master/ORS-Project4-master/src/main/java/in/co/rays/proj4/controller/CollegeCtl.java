package in.co.rays.proj4.controller;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
import in.co.rays.proj4.controller.ORSView;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.controller.CollegeCtl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@ WebServlet(name="CollegeCtl",urlPatterns={"/ctl/CollegeCtl"})


public class CollegeCtl extends BaseCtl {

	 private static final long serialVersionUID = 1L;

	 private static Logger log = Logger.getLogger(CollegeCtl.class);
	 
	  /**
	     * Validates input data entered by User
	     * 
	     * @param request
	     * @return
	     */
	  
		@Override
		protected boolean validate(HttpServletRequest request) {
			log.debug("CollegeCtl Method validate Started");

			boolean pass = true;
			System.out.println("college city is-------------->"+request.getParameter("city"));

			if (DataValidator.isNull(request.getParameter("name"))) {
				request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
				pass = false;
			}
			/*
			else if (!DataValidator.isName(request.getParameter("name"))) {
				request.setAttribute("name",PropertyReader.getValue("error.name", "College Name"));
				pass = false;
			}*/
			if (DataValidator.isNull(request.getParameter("address"))) {
				request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
				pass = false;
			}

			if (DataValidator.isNull(request.getParameter("state"))) {
				request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("city"))) {
				request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
				pass = false;
			}
			 if (DataValidator.isNull(request.getParameter("phoneNo"))) {
		           request.setAttribute("phoneNo",
		                   PropertyReader.getValue("error.require", "MobileNo"));
		           pass = false;
		       }
		       else if (DataValidator.isPhoneNo(request.getParameter("phoneNo"))) 
		       {
		           request.setAttribute("phoneNo",PropertyReader.getValue("error.number", "MobileNo"));
		           pass = false;
		       } 

			log.debug("CollegeCtl Method validate Ended");

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
				log.debug("CollegeCtl Method populatebean Started");

				CollegeBean bean=new CollegeBean();
				
				bean.setId(DataUtility.getLong(request.getParameter("id")));
				
				bean.setName(DataUtility.getString(request.getParameter("name")));
				
				bean.setAddress(DataUtility.getString(request.getParameter("address")));
				
				bean.setState(DataUtility.getString(request.getParameter("state")));
				bean.setCity(DataUtility.getString(request.getParameter("city")));
				bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
				
				populateDTO(bean,request);
				

				log.debug("CollegeCtl Method populatebean Ended");

				return bean;
				
			}
	  
			 /**
			    * Contains display logics
			    */

	 
	 protected void doGet(HttpServletRequest request,HttpServletResponse response)
			 		throws ServletException,IOException
	 {
		 log.debug("CollegeCtl Method doGet Started");
		 String op=DataUtility.getString(request.getParameter("operation"));
		 
			// get model
			CollegeModel model = new CollegeModel();
			long id = DataUtility.getLong(request.getParameter("id"));

			if(id>0)
			{CollegeBean bean;
				try{
					bean=model.findByPK(id);
					ServletUtility.setBean(bean,request);
				}catch(ApplicationException e)
				{
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return ;
				}
				
			}
			ServletUtility.forward(getView(), request, response);
			log.debug("CollegeCtl Method doGet Ended");
	 }
	 /**
	    * Contains submit logics
	    */

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			log.debug("CollegeCtl Method doPost Started");
			String op = DataUtility.getString(request.getParameter("operation"));
			// get model
			CollegeModel model = new CollegeModel();
			long id = DataUtility.getLong(request.getParameter("id"));
		
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

				CollegeBean bean = (CollegeBean) populateBean(request);

				try {
					if (id > 0) {
						model.update(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Data is successfully Updated", request);

					} else {
						long pk = model.add(bean);
						// ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);

						/* bean.setId(pk); */
					}

				} catch (ApplicationException e) {
					e.printStackTrace();
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("College Name already exists", request);
				} 
				

			} else if (OP_DELETE.equalsIgnoreCase(op)) {

				CollegeBean bean = (CollegeBean) populateBean(request);
				try {
					model.delete(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("College deleted Successfully", request);
					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);

					return;

				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				}

			} else if (OP_CANCEL.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			}

			ServletUtility.forward(getView(), request, response);

			log.debug("CollegeCtl Method doPost Ended");

		}

			

	    /**
	     * Returns the VIEW page of this Controller
	     * 
	     * @return
	     */

	 
	 
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}

}

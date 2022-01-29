package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = "/ctl/GetMarksheetCtl")

public class GetMarksheetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
	  /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
  
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("GetMarksheetCTL Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}

		log.debug("GetMarksheetCTL Method validate Ended");

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
		log.debug("GetMarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		log.debug("GetMarksheetCtl Method populatebean Ended");

		return bean;

	}
	
	 /**
    * Contains display logics
    */


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("GetMarksheetCtl Method dopost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model

		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = (MarksheetBean) populateBean(request);
	//	long id = DataUtility.getLong(request.getParameter("id"));
		//System.out.println("id------------->"+id);
		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				bean = model.findByRollNo(bean.getRollNo());
				if (bean != null) {
					ServletUtility.setBean(bean, request);
				} else {
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
				}
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}

    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */


	@Override
	protected String getView() {

		return ORSView.GET_MARKSHEET_VIEW;
	}

}

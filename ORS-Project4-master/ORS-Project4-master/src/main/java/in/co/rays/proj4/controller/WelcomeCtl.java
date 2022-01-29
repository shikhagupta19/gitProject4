package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.util.ServletUtility;
/**
 * Welcome functionality Controller. Performs operation for Show Welcome page
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@ WebServlet(name="WelcomeCtl",urlPatterns={"/WelcomeCtl"})
public class WelcomeCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(WelcomeCtl.class);

	 /**
     * Contains display logics
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("WelcomeCtl Method doGet Started");

        ServletUtility.forward(getView(), request, response);

        log.debug("WelcomeCtl Method doGet Ended");
    }


    /**
     * Returns the VIEW page of this Controller
     * 
     * @return
     */

      protected String getView() 
      {
        return ORSView.WELCOME_VIEW;
      }
}

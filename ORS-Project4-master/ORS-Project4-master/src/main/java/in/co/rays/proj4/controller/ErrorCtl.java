package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Servlet implementation class ErrorCtl
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name="ErrorCtl",urlPatterns={"/ErrorCtl"})

public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public static final String OP_BACK = "Back";

	/**
	 * Contains display logics
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Dopossssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the VIEW page of this Controller
	 * 
	 * @return
	 */

	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW500;
	}

}

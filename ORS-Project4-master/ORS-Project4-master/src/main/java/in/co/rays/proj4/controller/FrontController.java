package in.co.rays.proj4.controller;


import in.co.rays.proj4.controller.ORSView;
import in.co.rays.proj4.util.ServletUtility;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 * 
 * 
 * @author Shikha
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebFilter(filterName="FrontController",urlPatterns={"/ctl/*","/doc/*"})

public class FrontController implements Filter {
	
	public void destroy(){
		
	}
	
	public void doFilter(ServletRequest req,ServletResponse resp,FilterChain chain)throws IOException,ServletException
	{
		//System.out.println("FrontController called");
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)resp;
		
		HttpSession session=request.getSession(true);
		
		if(session.getAttribute("user")==null)
		{
			System.out.println("FrontController called (session out)");
			String uri=request.getRequestURI();
			request.setAttribute("uri", uri);
			//request.setAttribute("message", "Your session has been expired. Please re-Login.");
			ServletUtility.setErrorMessage("Your session has been expired. Please re-Login.",request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
		}
		else{
			System.out.println("FrontController called (session in)");
			chain.doFilter(req, resp);
		}
	}
	
	public void init(FilterConfig conf)throws ServletException{
		
	}

}

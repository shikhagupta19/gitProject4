<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome Page</title>
</head>
<body>
        <%@ include file="Header.jsp"%>
<div style="height: ; width: 100%; margin-bottom: ">
    <form action="<%=ORSView.WELCOME_CTL%>" method="get">

        
                    <h1 align="Center" style="margin-top: 220px; margin-bottom: 298px">
                        <font size="14px" color="brown">Welcome to ORS </font>
                    </h1>
        
                    <%
                    UserBean beanUserBean = (UserBean) session.getAttribute("user");
                        if (beanUserBean != null) {
                            if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
                    %>
        
                    <h2 align="Center">
                        <a href="<%=ORSView.GET_MARKSHEET_CTL%>"style="text-decoration: none;padding-bottom: 250%;bottom: 50%">Click here to see your Marksheet </a>
                    </h2>
                     
                     <%
                            }
                        }
                     %>
                
                </form>
        </div>
        <div>

        </div>	
        <br>
        <br>
        <br>
        <br>
        
	 <%@ include file="Footer.jsp"%>
</body>
      
</html>
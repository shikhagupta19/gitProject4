<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.ErrorCtl" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error View 500</title>
<style>

#error
{
	 padding: 60px;
	 margin-left: 200px;
}

</style>
</head>
<body>
 <form action="<%= ORSView.ERROR_CTL %>" method="post">
<div id="error">

<h1 align="Left">
                <img src="<%=ORSView.APP_CONTEXT%>/img/error.png" width="170"
                    height="150">
            </h1>

<h2>***** 500! SOMETHING WENT WRONG..*****</h2>

<h4>Try:</h4>

<ul>
  <li>Checking the network cables, modem, and router</li>
  <li>Running Windows Network Diagnostics</li>
  <li>Reconnecting to Wi-Fi</li>
  <li>Problem in WebApplication!! Try after some time</li>
</ul>  
<p>ERR_INTERNET_DISCONNECTED</p>
</div>

<input type="submit" name="operation" value="<%=ErrorCtl.OP_BACK %>"/>
</form>
</body>
</html>
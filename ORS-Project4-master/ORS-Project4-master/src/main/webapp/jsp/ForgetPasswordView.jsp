<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.ForgetPasswordCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Forget Password</title>
</head>
<style>
input[type=submit] {
    width: 88px;  height: 30px;
}
input[type=text],[type=password] {
    width: 200px;  height: 26px;
}
::placeholder { 
    text-align: left;
        padding: 8px 10px;
        font-size: 14px;
}
</style>

<body>
<div style="height: 675px; width: 100%" align="center">
<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
<%@include file="Header.jsp" %>
 <jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
<br><br><br>
<h2><u>Forget Password</u></h2>
<H2 align="center">
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

<input type="hidden" name="id" value="<%=bean.getId()%>" >

<lable><b>Submit your email address and we'll send you password in your registered Email ID.</lable><br><br>

<table id="tab" style="position: absolute; left: 520px; height: 80px;" >
			
			<tr>
			    <th>Email Id:<font color="red">*</font></th>	
				<td><input type="text" name="login" placeholder="Enter Email Id" value="<%=ServletUtility.getParameter("login", request)%>">
				<font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                
            </tr>
                        
			<tr>
			<th></th>
			<td colspan="1">
		         <input type="submit" name="operation"	value="<%=ForgetPasswordCtl.OP_GO%>">
				</td>
				
           </tr>
			</table>










</div>
<!-- <div align="bottom"> -->
<%@include file="Footer.jsp" %>
<!--  </div>-->
</form>
</body>
</html>
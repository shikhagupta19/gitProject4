<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login View </title>
<style>
 
input[type=submit] {
    width: 88px;  height: 30px;
    
}
input[type=text],[type=password] {
    width: 200px;  height: 26px;
}
th
{
	text-align: left;
}
::placeholder { 
    text-align: left;
        padding: 8px 10px;
        font-size: 14px;
}

</style>
</head>
<body>

<%@include file="Header.jsp" %>
<div style="height: 383px; width: 100%" align="center"  >
 <form  action="<%=ORSView.LOGIN_CTL%>" method="post">
<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
<br><br>
       <h2>Login Form</h2>


           <H2 align="center">
               <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
              <input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
	
	<table id="tab" style="position: absolute; height:190px; left: 600px;">
	
 <tr>
                <%--   <%
                 String msg=  (String)request.getAttribute("message");
                  if(msg!=null)
                  {
                   %>           
                     <h2><font color="red"><%=msg%></font></h2>
                  
                  
                  <%
                     }
                  %>
  --%>
 
                    <th align="left">LoginId<font color="red">*</font></th>
                    
                    <td><input type="text" name="login"
                        placeholder="Enter Email ID"
                        value="<%= DataUtility.getStringData(bean.getLogin())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                
                <tr>
                    <th align="left">Password<font color="red">*</font></th>
                    <td><input type="password" name="password" placeholder="Enter Password" id="myInput"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>">
                        <font
                    color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
               	
	<tr>
	
         <th></th>
         <td colspan="2"><input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_IN %>" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="submit" name="operation" value="<%=LoginCtl.OP_SIGN_UP %>">&nbsp;</td>
     </tr>
                
                
    <tr>
         <th></th>

         <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password ?</b></a>&nbsp;</td>

        

      </tr>	

       
                

    </tr>	

	
	</table><%String uri=(String)request.getAttribute("uri"); %>
	<input type="hidden" name="uri" value="<%=uri%>">
	
	</form>
	
</div>

   
</body>
<br><br><br><br><br><br><br><br>&nbsp;
<%@ include file="Footer.jsp" %>
</html>

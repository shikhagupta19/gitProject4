<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
 <%-- <%@page errorPage="ErrorView.jsp" %> --%>
 <%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>User Registration Page</title>
<script type="text/javascript">
function mouseoverPass(obj)
{
	  var obj = document.getElementById('myInput');
	  obj.type = "text";
	}
	function mouseoutPass(obj) {
	  var obj = document.getElementById('myInput');
	  obj.type = "password";
	}
	

</script>
<style>
#tab{
	/* width:"99%"; */
	/* position: fixed; */
	table-layout: fixed;
} 
input[type=submit] {
    width: 88px;  height: 30px;
}
th
{
	text-align: left;
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
    <!-- <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
         <script type="text/javascript" src="src/main/webapp/js/calendar.js"></script>
  <script>
  var d = new Date(90,0,1);
	$(function() {
		$( "#datepicker" ).datepicker({
	                defaultDate:d, //set the default date to Jan 1st 1990
			changeMonth: true,
			changeYear: true,
	        yearRange: '1980:2021', //set the range of years
	        dateFormat: 'dd-mm-yy' //set the format of the date
		});
	}); -->
	 
</script>
</head>
<body>
<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

        <%@ include file="Header.jsp"%>
           
<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>

<div style="height: 500px; width: 100%">
       
            <h1 align="center">User Registration</h1>

           <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <H2 align="center">
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>


            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

        <table id="tab" style="position: absolute; left: 520px;">

                <tr>
                    <th>First Name<font color="red">*</font></th>
                    <td><input type="text" name="firstName" placeholder="Enter First Name"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th>Last Name<font color="red">*</font></th>
                    <td><input type="text" name="lastName" placeholder="Enter Last Name"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th>LoginId<font color="red">*</font></th>
                    <td><input type="text" name="login"
                        placeholder="Must be Email ID"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>
                <tr>
                    <th>Password<font color="red">*</font></th>
                    <td><input type="password" name="password" placeholder="Enter Password" id="myInput"
                        value="<%=DataUtility.getStringData(bean.getPassword())%>">
                       <%--  <img src="<%=ORSView.APP_CONTEXT%>/img/eye.png" onmouseover="mouseoverPass();" onmouseout="mouseoutPass();" />
                        --%> 
                        <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
                </tr>
                <tr>
                    <th>Confirm Password<font color="red">*</font></th>
                    <td><input type="password" name="confirmPassword" placeholder="Enter Confirm Password" 
                        value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>">
                        <font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%>
                     </font></td>
                </tr>
                <tr>
                    <th>Gender<font color="red">*</font></th>
                    <td>
                        <%
                        	HashMap map = new HashMap();
                                                    map.put("M", "Male");
                                                    map.put("F", "Female");

                                                    String htmlList = HTMLUtility.getList("gender", bean.getGender(),map);
                        %> <%=htmlList%>
					<font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font>
                    </td>
                </tr>

                <tr>
                    <th>Date Of Birth<font color="red">*</font></th>
                    <td><input type="text" size="20px" name="dob" id="datepicker"
						readonly="readonly" placeholder="DD/MM/YYYY"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                                
                <tr>
                    <th align="left">Mobile No<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo"
                    	placeholder="Enter 10 Digits No"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                
                <tr>
                    <th></th>
                    <td colspan="2">
                        <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP %>">
                        &nbsp; 
                        <input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_BACK %>">
                    </td>
                    
                </tr>
            </table>
    </form>
    </center>
    </div>

<div>
<%@ include file="Footer.jsp" %>
</div>

</body>
</html>
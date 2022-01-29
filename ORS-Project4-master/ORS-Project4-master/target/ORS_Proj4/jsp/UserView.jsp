<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User View</title>
<style>
#tab {
	/* width:"99%"; */
	/* position: fixed; */
	table-layout: fixed;
}

input[type=submit] {
	width: 88px;
	height: 30px;
}

input[type=text] {
	width: 200px;
	height: 26px;
}

input[type=password] {
	width: 200px;
	height: 26px;
}

input[type=email] {
	width: 200px;
	height: 26px;
}

::placeholder {
	text-align: left;
	padding: 8px 10px;
	font-size: 14px;
}
</style>

<!-- <link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
	        yearRange: '1980:2001', //set the range of years
	        dateFormat: 'dd-mm-yy' //set the format of the date
		});
	});
</script>
 -->
</head>
<body>
	<%@ include file="Header.jsp"%>
	<div style="height: 570px; width: 100%">

		<form action="<%=ORSView.USER_CTL%>" method="post">
			<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
				scope="request"></jsp:useBean>

			<%
             List rlist = (List)request.getAttribute("rlist");   
             
        %>
			<div style="height: 503px; width: 100%">
				<% if(bean.getId()>0){ %>
				<h1 align="center">Update User</h1>
				<%}else{ %>
				<h1 align="center">Add User</h1>
				<%} %>
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>



				<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
					type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
				<input type="hidden" name="modifiedBy"
					value="<%=bean.getModifiedBy()%>"> <input type="hidden"
					name="createdDatetime"
					value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime"
					value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
				
				
				<table id="tab" style="position: absolute; left: 520px; height:;">
					<tr>
						<th align="left">First Name<font color="red">*</font></th>
						<td><input type="text" name="firstName"
							placeholder="Enter First Name"
							value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
							color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
					</tr>

					<tr>
						<th align="left">Last Name<font color="red">*</font></th>
						<td><input type="text" name="lastName" 
							placeholder="Enter Last Name"
							value="<%=DataUtility.getStringData(bean.getLastName())%>">

							<font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
						</td>
					</tr>
					<tr>

						<th align="left">LoginId<font color="red">*</font></th>
						<td><input type="text" name="login"
							placeholder="Enter Login Id"
							value="<%=DataUtility.getStringData(bean.getLogin())%>"
							<%=(bean.getId() > 0) ? "readonly" : ""%>><font
							color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
					</tr>
					<tr>
						<th align="left">Password<font color="red">*</font></th>
						<td><input type="password" name="password"
							placeholder="Xyz@111111"
							value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
							color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
					</tr>
					<tr>
						<th align="left">Confirm Password<font color="red">*</font></th>
						<td><input type="password" name="confirmPassword"
							placeholder="Confirm Password"
							value="<%=DataUtility.getStringData(bean.getPassword())%>"><font
							color="red"> <%=ServletUtility.getErrorMessage("confirmPassword",
                    request)%></font></td>
					</tr>
					<tr>
						<th align="left">Gender<font color="red">*</font></th>
						<td>
							<%
                        	HashMap map = new HashMap();
                            map.put("M", "Male");
                            map.put("F", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),map);
                        %> <%=htmlList%> <font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%>
								<td>
					</tr>
					<tr>
						<th align="left">Role<font color="red">*</font></th>
						<td><%=HTMLUtility.getList("roleId",String.valueOf(bean.getRoleId()),rlist)%>
							<font color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></td>
					</tr>
					<tr>
						<th align="left">Date Of Birth<font color="red">*</font></th>
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
						<td>
							<%
                        if (bean.getId() > 0) {
                        	%> 
                        <input type="submit" name="operation" value="<%=UserCtl.OP_UPDATE%>"> 
                        <% } else{ %>

							<input type="submit" name="operation"
							value="<%=UserCtl.OP_SAVE%>">
							 <%} %>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;							  <%
	    				if (bean.getId() > 0) {
	 				%> <input type="submit" name="operation"
							value="<%=UserCtl.OP_CANCEL%>"> <%
				    	}
				    	else
				   		{
					%> <input type="submit" name="operation"
							value="<%=UserCtl.OP_RESET%>"> <%
			    		}
			        %>
						</td>

					</tr>
				</table>
			</div>
			<div>


				<%@ include file="Footer.jsp"%>
			</div>
		</form>
	</div>
</body>
</html>
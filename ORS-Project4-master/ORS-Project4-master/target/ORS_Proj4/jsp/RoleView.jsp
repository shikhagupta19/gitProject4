<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.RoleCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<style>
#tab {
	/* width:"99%"; */
	/* position: fixed; */
	table-layout: fixed;
}

th {
	text-align: left;
}

input[type=submit] {
	width: 88px;
	height: 30px;
}

input[type=text], [type=password] {
	width: 200px;
	height: 26px;
}

::placeholder {
	text-align: left;
	padding: 8px 10px;
	font-size: 14px;
}

textarea {
	width: 200px;
	height: 70px;
}
</style>
<meta charset="ISO-8859-1">
<title>RoleView</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RoleBean"
		scope="request"></jsp:useBean>
	<div style="height: 503px; width: 100%" align="center">
		<form action="<%=ORSView.ROLE_CTL%>" method="post">
			<% 
	        	if(bean.getId() > 0)
        		{
        	%>
			<h1 align="center">Update Role</h1>
			<%
        		}
        		else
        		{
            %>
			<h1 align="center">Add Role</h1>
			<%
				}
			%>

			<H2 align="center">
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2 align="center">
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table id="tab" style="position: absolute; left: 520px;">
				<tr>
					<th>Name<font color="red">*</font></th>
					<td><input type="text" name="name" placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th>Description<font color="red">*</font></th>
					<td><input type="text" name="description"
							placeholder="Enter Description"
							value="<%=DataUtility.getStringData(bean.getDescription())%>"></textarea>
							<font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<%
	    					if (bean.getId() > 0) {
	 					%> <input type="submit" name="operation"
						value="<%=RoleCtl.OP_UPDATE%>"> <%
				     		}
				    		else
				    		{
						%> <input type="submit" name="operation"
						value="<%=RoleCtl.OP_SAVE%>"> <%
			    			}
			            %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%
			          	    				if (bean.getId() > 0) {
	 				%> <input type="submit" name="operation"
						value="<%=RoleCtl.OP_CANCEL%>"> <%
				    	}
				    	else
				   		{
					%> <input type="submit" name="operation"
						value="<%=RoleCtl.OP_RESET%>"> <%
			    		}
			        %>
					
				</tr>
			</table>
		</form>
	</div>
	<div>
        <%@ include file="Footer.jsp"%>
        </div>	
	
</body>
</html>
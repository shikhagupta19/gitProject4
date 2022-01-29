<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@page import="in.co.rays.proj4.model.UserModel"%>
<%@page import="in.co.rays.proj4.controller.UserListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility" %>
<%-- <%@include file="Header.jsp"%> --%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UserList</title>
<style>
input[type=submit] {
	width: 90px;
	height: 30px;
}

input[type=text] {
	width: 200px;
	height: 26px;
}

#customers {
	border-collapse: collapse;
	width: 100%;
}

#customers td, #customers th {
	border: 1px solid #A9A9A9;
	padding: 8px;
	text-align: center;
}

/* #customers tr:nth-child(even){background-color: #f2f2f2;}
 */
#customers th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	font-size: 20px;
	/*  background-color: #4CAF50; */
	color: black;
}

.disabledLink {
	color: #333;
	text-decoration: none;
	cursor: default;
}

.isDisabled {
	color: currentColor;
	cursor: not-allowed;
	opacity: 0.5;
	text-decoration: none;
}
</style>

</head>
<body>
	<%@include file="Header.jsp"%>
	
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
	<div style="height: 500px; width: 100%">
	<div align="center">

		<h1 align="center">User List</h1>
	
		<form action="<%=ORSView.USER_LIST_CTL%>" method="post">
	
		
		
			<script type="text/javascript"
				src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
			<script language="javascript">
				$(function() {

					// add multiple select / deselect functionality
					$("#selectall").click(function() {
						$('.case').attr('checked', this.checked);
					});

					// if all checkbox are selected, check the selectall checkbox
					// and viceversa
					$(".case").click(function() {

						if ($(".case").length == $(".case:checked").length) {
							$("#selectall").attr("checked", "checked");
						} else {
							$("#selectall").removeAttr("checked");
						}

					});
				});

				function disablelink(linkID) {
					var hlink = document.getElementById(linkID);
					if (!hlink)
						return;
					hlink.href = "#";
					hlink.className = "disableLink";
				}
			</script>

			<h4 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>

			</h4>


			<br>
			<%
				List list2 = ServletUtility.getList(request);

				if (list2 == null || list2.size() == 0) {
			%>


			<td><input type="submit" name="operation"
				value=" <%=UserListCtl.OP_BACK%>"></td>
			<%
				}

				else {
			%>
			
			<table id="rolelisttop" width="100%">
			
				<!-- <table width="100%"> -->
				<tr >
					<td align="center"><label>FirstName :</label> <input
						type="text" name="firstName" placeholder="Enter Name"
						value="<%=ServletUtility.getParameter("firstName", request)%>">
						&nbsp; <label>LoginId:</label> <input type="text" name="login"
						placeholder="Enter LoginId"
						 value="<%=ServletUtility.getParameter("login", request)%>">
						&nbsp;
						<% List roleList =(List)request.getAttribute("rlist");%>
						<label>Role :</label>
						<%= HTMLUtility.getList("roleId",String.valueOf(ServletUtility.getParameter("roleId", request)),roleList)%> 
						
						&nbsp;<input type="submit" name="operation"
						value="<%=UserListCtl.OP_SEARCH%>">
					
						&nbsp;<input type="submit" name="operation"
						value="<%=UserListCtl.OP_RESET%>">
						
				</td>
				</tr>

			</table>
			<br>
			<table id="customers">
				<!-- <table border="1" width="100%"> -->
				<tr>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>
					<th><input type="checkbox" id="selectall"  />Select All</th>
					
					<%
					
						}
					%>
					<th>S.No</th>
					<th>FirstName</th>
					<th>LastName</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>DOB</th>
					<th>Role</th>

					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>
					<th>Edit</th>
					<%
						}
					%>
				</tr>


				<%
					List list1 = null;
						UserModel model = new UserModel();
						list1 = model.list();

						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						//System.out.println("starting value of index -------------->" + index);

						List list = ServletUtility.getList(request);
						Iterator<UserBean> it = list.iterator();
						while (it.hasNext()) {
							UserBean bean1 = it.next();
				%>

				<tr>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>
					<td align="center"><input type="checkbox" name="ids"
						class="case" value="<%=bean1.getId()%>"<%if(bean1.getRoleId() == RoleBean.ADMIN ){
						
						%><%= "disabled"%><%} %>></td>
					<%
						}
					%>
					<%
						// get Role Name
								RoleModel cModel = new RoleModel();
								RoleBean Bean = cModel.findByPK(bean1.getRoleId());
								bean1.setRoleName(Bean.getName());
					%>

					<td><%=index++%></td>
					<td><%=bean1.getFirstName()%></td>
					<td><%=bean1.getLastName()%></td>
					<td><%=bean1.getLogin()%></td>
					<td><%=bean1.getGender()%></td>
					<td><%=bean1.getDob()%></td>
					<td><%=bean1.getRoleName()%></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>

					<%
						if (bean1.getRoleId() != 1) {
					%>
					<td><a  href="<%=ORSView.USER_CTL %>?id=<%=bean1.getId()%>" id="linkID" >Edit</a></td>
					<%
						}
					%>


					<%
						if (bean1.getRoleId() == 1) {
					%>
						<td >
							<a href="" style="text-decoration: none; cursor: not-allowed; opacity: 0.5; color: current-color" >Edit</a>
						</td>
					<%
						}
					%>


					<%
						}
					%>





				</tr>
				<%
					}
				%>
			</table>
			<!-- <table width="100%"> -->
			<table id="rolelistbottom" width="100%">
				<tr>
					<input type="hidden" name="pageNo" value="<%=pageNo%>">
					<input type="hidden" name="pageSize" value="<%=pageSize%>">

					<td><input type="submit" name="operation"
						value="<%=UserListCtl.OP_PREVIOUS%>"
						<%if (pageNo == 1) {%>
						disabled="disabled"
						<%}%>></td>
					<%
						if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
					%>
					<td align="center"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_DELETE%>"></td> &emsp; &emsp;
					<td align="center"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEW%>"></td> &emsp; &emsp; &emsp;
					<%
						}
					%>

					<%
						UserModel model1 = new UserModel();
							UserBean b = new UserBean();
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=UserListCtl.OP_NEXT%>"
						<%=((list.size() < pageSize) || model1.nextPK() - 1 == b.getId()) ? "disabled" : ""%>></td>

				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
			<%
				}
			%>
			
	
	
		</form></div>	




	
		
</div>
<div>
	<br>
	<br>
	<br>
	<br>
		<br>
	<br>	
	<%@include file="Footer.jsp"%>
</div>
</body>
</html>
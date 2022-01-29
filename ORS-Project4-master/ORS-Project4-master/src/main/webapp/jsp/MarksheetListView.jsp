<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.MarksheetModel"%>
<%@page import="in.co.rays.proj4.controller.MarksheetListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MarkSheet List</title>
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
	padding: 12px;
	text-align: center;
	font-size: 20px;
	/* 	background-color: #A9A9A9;  */
	color: black;
}

.ss {
	height: auto;
}
</style>


</head>
<body>
	<%@include file="Header.jsp"%>
	<div style="height: 600px; width: 100%">
		<h1 align="center">Marksheet List</h1>
		
	
	<jsp:useBean id="sbean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
	<%-- <h1><%= sbean.getRoleId() %></h1> --%>
	
		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">

			<div>
				
					
						<h3 colspan="8" align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
							<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
						</h3>
					

</div>
				<br>




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
			</script>


				<%
					List list1 = ServletUtility.getList(request);

					if (list1 == null || list1.size() == 0) {
				%>
				
					
				
				
				<td><input type="submit" name="operation"
					value=" <%=MarksheetListCtl.OP_BACK%>"></td>
				<%
					} else {
				%>

				<table width="100%">
				
				

					<tr>
										
						<td align="center">
						
						
							<%-- <label><b>Name: </label>
							 <input	type="text" name="name" placeholder="Enter Student name"
							value="<%=ServletUtility.getParameter("name", request)%>"> --%>
							
							
							&nbsp; <label>RollNo: </label> <input type="text" name="rollNo"
							placeholder="Enter Roll No"
							value="<%=ServletUtility.getParameter("rollNo", request)%>">
							&nbsp;
							
							
							<% List slist=(List)request.getAttribute("slist");%>
							<label>Student Name:</label>
							<%=HTMLUtility.getList("sname",String.valueOf(ServletUtility.getParameter("sname", request)), slist)%>
							&nbsp;
							 <input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_SEARCH%>">
							&nbsp;
							 <input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_RESET%>">
							
							</td>
							
					</tr>
				</table>
<br>
				<table id="customers">

					<!--  <table border="1" width="100%"> -->
					<tr>

						<%
							if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
						%>
						<th><input type="checkbox" id="selectall" />Select All</th>
						<%
							}
						%>

						<th>S.No</th>
						<th>RollNo</th>
						<th>Student Name</th>
						<th>Physics</th>
						<th>Chemistry</th>
						<th>Maths</th>
						<!--   <th>Edit</th> -->
						<%
							if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
						%>
						<th>Edit</th>
						<%
							}
						%>
					</tr>

					<%
						int pageNo = ServletUtility.getPageNo(request);
							int pageSize = ServletUtility.getPageSize(request);
							/*  System.out.println(pageNo);
							 System.out.println(pageSize); */
							int index = ((pageNo - 1) * pageSize) + 1;
							//System.out.println(index);

							List list = ServletUtility.getList(request);//request,getAttribute("list");
							Iterator<MarksheetBean> it = list.iterator();

							while (it.hasNext()) {

								MarksheetBean bean = it.next();
					%>
					<tr>
						<%
							if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
						%>

						<td align="center"><input type="checkbox" class="case"
							name="ids" value="<%=bean.getId()%>"></td>
						<%
							}
						%>

						<td><%=index++%></td>
						<td><%=bean.getRollNo()%></td>
						<td><%=bean.getName()%></td>
						<td><%=bean.getPhysics()%></td>
						<td><%=bean.getChemistry()%></td>
						<td><%=bean.getMaths()%></td>
						<%-- <td><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td> --%>
						<%
							if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
						%>
						<td><a href="<%=ORSView.MARKSHEET_CTL%>?id=<%=bean.getId()%>">Edit</a></td>
						<%
							}
						%>

					</tr>
					<%
						}
					%>
				</table>
				<table width="99%">
					<br>

					<tr>

						<td><input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_PREVIOUS%>"
							<%=(pageNo == 1) ? "disabled" : ""%>></td>
						<%
							if (userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY) {
						%>

						<td align="center"><input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_NEW%>"></td>
						<%
							}
						%>

						<%
							MarksheetModel model = new MarksheetModel();
								MarksheetBean b = new MarksheetBean();
								/* System.out.println("nexy pk id= "+model.nextPK());
								System.out.println("b id= "+b.getId()); */
						%>
						<td align="right"><input type="submit" name="operation"
							value="<%=MarksheetListCtl.OP_NEXT%>"
							<%=((list.size() < pageSize) || model.nextPK() - 1 == b.getId()) ? "disabled" : ""%>></td>

					</tr>

				</table>

				<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
					type="hidden" name="pageSize" value="<%=pageSize%>">
				<%
					}
				%>
			
		</form>

	</div>
	<div>
		<br> <br>




		<%@include file="Footer.jsp"%>
	</div>



</body>
</html>
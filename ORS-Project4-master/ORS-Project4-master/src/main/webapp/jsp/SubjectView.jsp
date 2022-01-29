<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.model.CourseModel"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.SubjectCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Subject VIew</title>
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

input[type=text], [type=password] {
	width: 200px;
	height: 26px;
}

::placeholder {
	text-align: left;
	padding: 8px 10px;
	font-size: 14px;
}
</style>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubjectBean"
		scope="request"></jsp:useBean>
	<div style="height: 503px; width: 100%">
		<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

			<%
				List l = (List) request.getAttribute("courseList");
			%>



			<!-- <div id="user"> -->
			<%
				if (bean.getId() > 0) {
			%>
			<h1 align="center">Update Subject</h1>
			<%
				} else {
			%>

			<h1 align="center">Add Subject</h1>
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
					<th>Course Name<font color="red">*</font></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l)%>
						<font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
				<tr>
					<th>Subject Name<font color="red">*</font></th>
					<td><input type="text" name="name"
						placeholder="Enter Subject Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"<%=bean.getId() > 0 ? "readonly" : "" %>>
						 
						<font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<%
							if (bean.getId() > 0)
							{
						%> <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_UPDATE%>"> <%
 	} 
							else
							{
 %>
                      <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_SAVE%>"> <%
 	}
 %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%
 	if (bean.getId() > 0)
 	{
 %> <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_CANCEL%>"> <%
 	}
 	else
 	{
 %> <input type="submit" name="operation"
						value="<%=SubjectCtl.OP_RESET%>"> <%
 	}
 %>

					</td>
				</tr>

			</table>
		</form>
	</div>
	<div>
		<%@ include file="Footer.jsp"%>
	</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.StudentCtl"%>
<%@page import="in.co.rays.proj4.controller.UserCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student View</title>
<style>
#tab{
	/* width:"99%"; */
	/* position: fixed; */
	table-layout: fixed;
} 
th
{
	text-align: left;
}
input[type=submit] {
    width: 90px;  height: 30px;
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

</head>
<body>
        <%@ include file="Header.jsp"%>
        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.StudentBean" scope="request"></jsp:useBean>
<div style="height: 445px; width: 100%">
      <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
	        yearRange: '1980:2020', //set the range of years
	        dateFormat: 'dd-mm-yy' //set the format of the date
		});
	});
</script>
    
<form action="<%=ORSView.STUDENT_CTL%>" method="POST">
   
        <%
            List l = (List) request.getAttribute("collegeList");
        %>

        
         	<input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
        	
        	<% 
        	
	        	if(bean.getId() > 0)
	        		{
        	%>
            	<h1 align="center">Update Student</h1>
            <%
	        		}
	        		else
	        		{
            %>
            
				<h1 align="center">Add Student</h1>
			<%
				}
			%>

            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <H2 align="center">
                <font color="green" > <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>

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
                    <th>College Name<font color="red">*</font></th>
                    <td>
                    <%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), l)%>
                    <font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
                </tr>
                <tr>
                    <th>Email Id<font color="red">*</font></th>
                    <td><input type="text" name="email" placeholder="Enter Email Id"
                        value="<%=DataUtility.getStringData(bean.getEmail())%>"<%=bean.getId() > 0 ? "readonly" : "" %>><font
                        color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
                </tr>
                <tr>
                    <th>Date Of Birth<font color="red">*</font></th>
                    	<td><input type="text" size="20px" name="dob" id="datepicker"
						readonly="readonly" placeholder="DD/MM/YYYY"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
                <tr>
                    <th>Mobile No<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo" placeholder="Enter Mobile No"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>
                <tr>
                    <th></th>
                    <td>	<%
	    					if (bean.getId() > 0) {
	 					%>
	 						<input type="submit" name="operation" value="<%=StudentCtl.OP_UPDATE%>"> 
	 					<%
				     		}
				    		else
				    		{
						%>
							<input type="submit" name="operation" value="<%=StudentCtl.OP_SAVE%>">
			            <%
			    			}
			            %>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;			<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>">
			        <%
			    		}
			        %>
				   
                </tr>
            </table>
    </form>
    </div>
 <div>
 <br>
 <br>
 <br>
    <%@ include file="Footer.jsp"%>
    </div>
</body>
</html>
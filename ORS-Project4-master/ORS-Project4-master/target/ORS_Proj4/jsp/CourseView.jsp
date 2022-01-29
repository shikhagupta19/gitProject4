<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="in.co.rays.proj4.controller.CourseCtl"%>
<%@page import="in.co.rays.proj4.controller.RoleCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Course</title>
<style>
input[type=submit] {
    width: 90px;  height: 30px;
}
input[type=text] {
    width: 200px;  height: 26px;
}
textarea{
   width: 200px;  height: 70px;
}

::placeholder { 
    text-align: left;
        padding: 8px 10px;
        font-size: 14px;
}
</style>

</head>
<body>
    <form action="<%=ORSView.COURSE_CTL%>" method="post">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourseBean" scope="request"></jsp:useBean>
<div style="height: 503px; width: 100%">
        
			<% 
        		if(bean.getId() > 0)
	        		{
        	%>
            	<h1 align="center">Update Course</h1>
            <%
	        		}
	        		else
	        		{
            %>
            
				<h1 align="center">Add Course</h1>
			<%
				}
			%>
            <h2 align="center">
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </h2>
            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table id="tab" style="position: absolute; left: 520px;">
                <tr>
                    <th align="left">Name<font color="red">*</font></th>
                    <td><input type="text" name="name" placeholder="Enter Course Name"
                        value="<%=DataUtility.getStringData(bean.getName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
                <tr>
                    <th align="left"">Description<font color="red">*</font></th>
                    <td><input type="text" name="description" placeholder="Enter Description"
                        value="<%=DataUtility.getStringData(bean.getDescription())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                    	<%
	    					if (bean.getId() > 0) {
	 					%>
	 						<input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE%>"> 
	 					<%
				     		}
				    		else
				    		{
						%>
							<input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">
			            <%
			    			}
			            %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>">
			        <%
			    		}
			        %>
				    
				                </tr>
            </table>
    </div>
    </form>
   
    <div>
    <%@ include file="Footer.jsp"%>
    </div>


</body>
</html>
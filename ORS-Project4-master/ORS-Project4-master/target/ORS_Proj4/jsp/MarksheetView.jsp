<%@ page language="java" contentType="text/html; charset=ISO-8859-1" 
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.MarksheetCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MarkSheet View</title>
<style>
input[type=submit] {
	width: 95px;
	height: 30px;
}

input[type=text] {
	width: 200px;
	height: 26px;
}

input[type=number] {
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
    <form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
        

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarksheetBean"
            scope="request"></jsp:useBean>
<div style="height:503px ;width:100%">
        <%
            List l = (List) request.getAttribute("studentList");
        %>

  
        
            <% 
	        	if(bean.getId() > 0)
        		{
        	%>
            	<h1 align="center">Update Marksheet</h1>
            <%
        		}
        		else
        		{
            %>
				<h1 align="center">Add Marksheet</h1>
			<%
				}
			%>
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
             
             <% 
	        	if(bean.getId() > 0)
        		{
        	%>
            	 <input type="hidden" name="studentId" value="<%=bean.getStudentId()%>">
            <%
        		}
            %>
             
              
                      <table id="tab" style="position: absolute; left: 550px";> 
                      
                	<tr>
                  <th align="left"> Student Name<font color="red">*</font></th>
                  <% if (bean.getId()>0) {%>
                  <td>          <input type="text" name="name"
                        value="<%=DataUtility.getStringData(bean.getName())%>" placeholder="Enter Name"
                        <%=bean.getId() > 0 ? "readonly" : "" %>> 
                        <font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font>
                </td>
                  <%} else{ %>
			   	<td>
			   		<%=HTMLUtility.getList("studentId",
                    String.valueOf(bean.getStudentId()), l)%>
              		  <font color="red"> <%=ServletUtility.getErrorMessage("studentId", request)%></font>
       			 </td>
       			 <%} %>
        
      		  </tr>
        
                <tr>
                    <th align="left">Roll No<font color="red">*</font></th>
				<td>          <input type="text" name="rollNo"
                        value="<%=DataUtility.getStringData(bean.getRollNo())%>" placeholder="Enter Roll No"
                       > <font
                        color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font>
                </td>
                </tr>
       	
        
        <tr>
                    <th align="left">Physics<font color="red">*</font></th>
				<td><input type="number" name="physics" placeholder="Enter physics marks"
                        value="<%=(DataUtility.getStringData(bean.getPhysics()).equals("0"))?"":DataUtility.getStringData(bean.getPhysics())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font>
            <tr>
                    <th align="left">Chemistry<font color="red">*</font></th>
				<td><input type="number" name="chemistry" placeholder="Enter chemistry marks"
                        value="<%=(DataUtility.getStringData(bean.getChemistry()).equals("0"))?"":DataUtility.getStringData(bean.getChemistry())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font>
  </td></tr>
  
  			<tr>
                    <th align="left">Maths<font color="red">*</font></th>
				<td> 		  <input type="number" name="maths" placeholder="Enter maths marks"
                        value="<%=(DataUtility.getStringData(bean.getMaths()).equals("0"))?"":DataUtility.getStringData(bean.getMaths())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font>
</td></tr>
  <tr>
                    <th></th>
                    	<td>
					<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=MarksheetCtl.OP_UPDATE%>">
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE%>">
			        <%
			    		}
			        %>
&nbsp;&nbsp;        
	<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET%>">
			        <%
			    		}
			        %>                  
				</td>
                                 

     </tr>                </table> 
      
 </div>
                    </form>
             
         <%@ include file="Footer.jsp"%>	
</body>
</html>
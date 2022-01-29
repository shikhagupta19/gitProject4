<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>CollegeView JSP</title>
<style>
#tab{
	/* width:"99%"; */
	/* position: fixed; */
	table-layout: fixed;
} 
input[type=submit] {
    width: 88px;  height: 30px;
}
input[type=text] {
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
<form action="<%= ORSView.COLLEGE_CTL %>" method="POST">
        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.CollegeBean"
            scope="request"></jsp:useBean>
<div style="height: 503px; width: 100%">
		
        <center>
        <%
				if (bean.getId() > 0) {
			%>
			<h1>Update College</h1>
			<%
				} else {
			%>
			<h1>Add College</h1>
			<%
				}
			%>
        
        <!--     <h1>Add College</h1>

 -->            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
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

            <table id="tab" style="position: absolute; left: 560px;">
                <tr>
                    <th align="left">Name<font color="red">*</font></th>
                    <td><input type="text" name="name" placeholder="College name"
                        value="<%=DataUtility.getStringData(bean.getName())%>"<%=bean.getId() > 0 ? "readonly" : "" %>><font
                        color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Address<font color="red">*</font></th>
                    <td><input type="text" name="address" placeholder="Enter address"
                        value="<%=DataUtility.getStringData(bean.getAddress())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">State<font color="red">*</font></th>
                    <td><input type="text" name="state" placeholder="Enter state"
                        value="<%=DataUtility.getStringData(bean.getState())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">City<font color="red">*</font></th>
                    <td><input type="text" name="city" placeholder="Enter city"
                        value="<%=DataUtility.getStringData(bean.getCity())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">MobileNo<font color="red">*</font></th>
                    <td><input type="text" name="phoneNo" placeholder="Mobile no"
                        value="<%=DataUtility.getStringData(bean.getPhoneNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
                </tr>


                <tr>
                    <th></th>
                    <td >
                    	<%
	    					if (bean.getId() > 0) {
	 					%>
	 						<input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>"> 
	 					<%
				     		}
				    		else
				    		{
						%>
							<input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">
			            <%
			    			}
			            %>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>">
			        <%
			    		}
			        %>                </tr>
            </table>
    </form>
    </div>
    <div>
    	<%@ include file="Footer.jsp"%>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
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
      height: 30px;
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
        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
          <div style="height: 516px; width: 100%" align="center">

        <form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
           <br><br><br><br><br>
            <h1>Change Password</h1>


            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<H3>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H3>
            
            <table id="tab" style="position: absolute; left: 575px; height: 200px;">



                <tr>
                    <th>Old Password<font color="red">*</font></th>
                    <td><input type="password" name="oldPassword" placeholder="Enter Old Password"
                        value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
                            : DataUtility.getString(request.getParameter("oldPassword")))%>>
                        <font color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
                </tr>

                <tr>
                    <th>New Password<font color="red">*</font></th>
                    <td><input type="password" name="newPassword" placeholder="Enter New Password"
                        value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
                            : DataUtility.getString(request.getParameter("newPassword")))%>>
                        <font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
                </tr>

                <tr>
                    <th>Confirm Password<font color="red">*</font></th>
                    <td><input type="password" name="confirmPassword" placeholder="Enter Confirm Password"
                        value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" 
                        	: DataUtility.getString(request.getParameter("confirmPassword")))%>>
                        <font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
                </tr>

                <tr>
                    <th></th>
                    <td>
                    	<input type="submit" name="operation" value="<%= ChangePasswordCtl.OP_SAVE%>">
                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
                    </td>
                </tr>

            </table>
            
    </form>
    </div>
    <div align="bottom">
    <%@ include file="Footer.jsp"%>
    </div>


</body>
</html>
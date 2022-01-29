<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@page import="in.co.rays.proj4.controller.MyProfileCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Profile View</title>
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
<!-- <div align="center" style="height:60%;"> -->
<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">

        
         <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
       <!--  <link rel="stylesheet" href="/resources/demos/style.css"> -->
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <!--  <script type="text/javascript" src="src/main/webapp/js/calendar.js"></script> -->
  <script>
	var d = new Date(90,0,1);
	$(function() {
		$( "#datepicker" ).datepicker({
	        //defaultDate:d, //set the default date to Jan 1st 1990
			changeMonth: true,
			changeYear: true,
	        yearRange: '1980:2020', //set the range of years
	        dateFormat: 'MM-dd-yy' //set the format of the date
		});
	});
</script> 
        
        
        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean" scope="request"></jsp:useBean>
<div style="height: 516px; width: 100%" align="center">
        
           <br><br> <h1>My Profile</h1>
            
            <H3>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H3>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

           <table id="tab" style="position: absolute; left: 560px; height: 320px" > 
            
                <tr>
                    <th>LoginId<font color="red">*</font></th>
                    <td><input type="text" name="login"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"readonly="readonly"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>

                <tr>
                    <th>First Name<font color="red">*</font></th>
                    <td><input type="text" name="firstName"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th>Last Name<font color="red">*</font></th>
                    <td><input type="text" name="lastName"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th>Gender<font color="red">*</font></th>
                    <td>
                        <%
                        	HashMap map = new HashMap();
                                                    map.put("M", "Male");
                                                    map.put("F", "Female");

                                                    String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                                            map);
                        %> <%=htmlList%>
                    </td>
                </tr>
                <tr>
                    <th>Mobile No<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>

                <tr>
                    <th>Date Of Birth<font color="red">*</font></th>
                    
                	<td><input type="text" size="20px" name="dob" id="datepicker"
						readonly="readonly" placeholder="DD/MM/YYYY"
						value="<%=DataUtility.getDateString(bean.getDob())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                
            <h2><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h2>
                
                <tr>
                    <th></th>
                    <td>
                        <input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE %>"> &nbsp; &nbsp; &nbsp; &nbsp; 
	                    <input type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>">
                    </td>
                </tr>
            </table>
            </div>
    </form> 
   <!--  </div> -->
    
    <%@ include file="Footer.jsp"%>
    
</body>
</html>
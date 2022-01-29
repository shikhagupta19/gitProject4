
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<style>
.header {
   position: relative;
   left: 0;
   bottom: 0;
   width: 100%;
   color: black;
  /*  background-color:#efefef; */
  background-color: #f5f5f5;
  
<<<<<<< HEAD
   /* height: 25%; */
=======
   height: 25%;
>>>>>>> 59f793d437a2b8fa345466a54bce9893925f1ab7
}
</style>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
       <!--   <script type="text/javascript" src="src/main/webapp/js/calendar.js"></script> -->
<script>
var d = new Date(90,0,1);
$(function() {
	$( "#datepicker" ).datepicker({
                defaultDate:d, //set the default date to Jan 1st 1990
		changeMonth: true,
		changeYear: true,
		 yearRange: '-40:-18', //set the range of years
        dateFormat: 'dd-mm-yy' //set the format of the date
	});
});</script>

<div class="header" style=";">
<hr>
	<%
UserBean userBean=(UserBean)session.getAttribute("user");
boolean userLoggedIn = (userBean!=null);
String welcomeMsg="Hi,";
//String r=userBean.getFirstName();
if(userLoggedIn)
{
	String role = (String) session.getAttribute("role");
    welcomeMsg += userBean.getFirstName() + " (" + role + ")";

}
else 
{
    welcomeMsg += "Guest" ;
}
%>
	<table style="width: 100%; border: 0; height: 10%">
		<tr>
		<td width="90%"><a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a>|
				<%
if(userLoggedIn)
{
%> <a href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>
				<%
}
else {
 %> 
 <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> 
 <%
     }
 %>
 </td>
			<td rowspan="2"><img src="<%=ORSView.APP_CONTEXT%>/img/customLogo.png" width="309px"
				height="88px" align="Right"></td>
		</tr>
		<tr>
			<td>
				<h3><%=welcomeMsg%></h3>
			</td>
		</tr>

		<% 
		if (userLoggedIn) {
        %>
       
		<tr>
		
			<td colspan="2"><a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a>
				| <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> | <a
				href="<%=ORSView.GET_MARKSHEET_CTL%>">GetMarksheet</b></a>| <a
				href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">MarksheetMerit List</b></a>|


			<%
                  if (userBean.getRoleId() == RoleBean.ADMIN) {
   		    %> 
   		    <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> | 
   		    <a	href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
   		    <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
   		    <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> | 
   		    <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> | 
   		    <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
   		    <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | 
   		    <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | 
   		    <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> | 
   		    <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
   		    <a href="<%=ORSView.COURSE_CTL%>"> Add Course</b></a> | 
   		<a href="<%=ORSView.COURSE_LIST_CTL%>">Course List</b></a> | 
   		
   		<a href="<%=ORSView.FACULTY_CTL%>"> Add Faculty</a> | 
   		<a href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List </b></a> |
   		<a href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> | 
   		<a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>|
   		<a href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</b></a> | 
   		<a	href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a>| 
   		<%-- <a	href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a> | --%> 
   		<a	href="/ORS_Proj4/java_doc/index.html" target="blank">Java Doc</b></a> | 
   		
				<%
               }
             	%> 
 	
 	<%
                  if (userBean.getRoleId() == RoleBean.STUDENT) {
    %> 
               <a href="<%=ORSView.STUDENT_LIST_CTL%>">StudentList</b></a> | 
               <a href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List </b></a> |
               <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
               <a href="<%=ORSView.COURSE_LIST_CTL%>">Course List </b></a> | 
               <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> | 
				<a href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a> |  
			<%-- 	 <a href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a> --%> 

			   <%
                   }
               %> 
               <%
                  if (userBean.getRoleId() == RoleBean.FACULTY) {
              	%>
              	 <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a>
				| <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> | <a
				href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> | 
				<a
				href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>">Course List </b></a> | <a
				href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> | <a
				href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>|<a
				href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a>|<%--   <a
				href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a> | 
 --%>
				
				
			<% 
				 }
            %>
            
            
            
            <%
                  if (userBean.getRoleId() == RoleBean.College) {
   		    %> 
   		    <a  href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> | <a
				href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> | 
				<a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |
				<a
				href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |<a		
				href="<%=ORSView.COURSE_CTL%>"> Add Course</b></a> | <a
				href="<%=ORSView.COURSE_LIST_CTL%>">Course List </b></a> |<a
				href="<%=ORSView.FACULTY_CTL%>"> Add Faculty</b></a> | <a
				href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List </b></a> |<a
				href="<%=ORSView.SUBJECT_CTL%>">Add Subject</b></a> | <a
				href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a>|<a
				href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</b></a> | <a
				href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</b></a>|<%--  <a
				href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a> | 
				 --%><%
               }
             	%> 
            
            
             <%
                  if (userBean.getRoleId() == RoleBean.KIOSK) {
   		    %> 
   		    <a 	href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |<%-- <a
   		    	href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank">Java Doc</b></a> | 
			 --%>
			
				<%
               }
             	%> 
            
               </td>
		</tr>
		<%
        }
    	%>


	</table>


<hr>
</div>

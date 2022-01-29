<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
  
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.TimeTableCtl"%>
<%@page import="in.co.rays.proj4.controller.SubjectCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.RoleCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Time-Table View</title>
      <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
         <script type="text/javascript" src="src/main/webapp/js/calendar.js"></script>
  <script>
	//var d = new Date();
	function noSunday(day){
	return[day.getDay()!=0,false];
};
	$(function() {
		$( "#datepicker" ).datepicker({
	        defaultDate:new Date(), //set the default date
			changeMonth: true,
			changeYear: true,
			beforeShowDay: noSunday	,  
	        yearRange: '0:+5', //set the range of years
	        dateFormat: 'dd-mm-yy', //set the format of the date
	        minDate: '+1',//tomorrow date
		});
	});
</script>
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
    width: 95px;  height: 30px;
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

        <jsp:useBean id="bean" class="in.co.rays.proj4.bean.TimeTableBean" scope="request"></jsp:useBean>
<div style="height:503px; width: 100%">		
        <form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
		
		<%
            List l = (List) request.getAttribute("courseList");
		
        %>
		
		<%
        			List l1 = (List) request.getAttribute("subjectList");
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
            	<h1 align="center">Update TimeTable</h1>
            <%
        		}
        		else
        		{
            %>
				<h1 align="center">Add TimeTable</h1>
			<%
				}
			%>
			
            <H2 align="center">
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </H2>
            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            </H2>

            
		
		<table id="tab" style="position: absolute; left: 520px;">
			<tr>
				<th>Course Name<font color="red">*</font></th>
				<td><%=HTMLUtility.getList("courseName", String.valueOf(bean.getCourseId()), l)%>
					<font color="red"> <%=ServletUtility.getErrorMessage("courseName", request)%></font></td>
			</tr>
			
			<tr>
				<th>Semester<font color="red">*</font></th>
				<td>
					 <%
                            HashMap map = new HashMap();
                            map.put("1th", "1 semester");
                            map.put("2th", "2 semester");
                            map.put("3th", "3 semester");
                            map.put("4th", "4 semester");
                            map.put("5th", "5 semester");
                            map.put("6th", "6 semester");
                            map.put("7th", "7 semester");
                            map.put("8th", "8 semester");
                            
                            String htmlList = HTMLUtility.getList("semester", bean.getSemester(), map);
                        	%> <%=htmlList%>
                        	<font color="red"> <%=ServletUtility.getErrorMessage("semester", request)%></font>
                        </td>
			</tr>
			
			<tr>
				<th>Subject Name<font color="red">*</font></th>
				<td><%=HTMLUtility.getList("subjectName", String.valueOf(bean.getSubjectId()), l1)%>
					<font color="red"> <%=ServletUtility.getErrorMessage("subjectName", request)%></font></td>
			</tr>
			
			<tr>
				<th>Exam Time<font color="red">*</font></th>
				<td>
					<%
						HashMap map1 = new HashMap();
						map1.put("Slot1- 09:00 AM to 12:00 PM", "09:00 AM to 12:00 PM");
						map1.put("Slot2- 12:00 PM to 03:00 PM", "12:00 PM to 03:00 PM");
						map1.put("Slot3- 03:00 PM to 06:00 PM", "03:00 PM to 06:00 PM");

						String htmlList1 = HTMLUtility.getList("examTime", bean.getExamTime(), map1);
					%> <%=htmlList1%>
					<font color="red"> <%=ServletUtility.getErrorMessage("examTime", request)%></font>
				</td>
			</tr>
			
			<tr>
				<th>Exam Date<font color="red">*</font></th>
				<td><input type="text" size="20px" name="examDate"
					id="datepicker" readonly="readonly" placeholder="Enter Exam Date"
					value="<%=DataUtility.getDateString(bean.getExamDate())%>">

					<font color="red"> <%=ServletUtility.getErrorMessage("examDate", request)%></font>
					</td>
			</tr>
			
			<tr>
				<th></th>
				<td>
					<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=TimeTableCtl.OP_UPDATE%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=TimeTableCtl.OP_SAVE%>">
			        <%
			    		}
			        %>&nbsp;&nbsp;&nbsp;
					<%
	    				if (bean.getId() > 0) {
	 				%>
	 					<input type="submit" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>"> 
	 				<%
				    	}
				    	else
				   		{
					%>
						<input type="submit" name="operation" value="<%=TimeTableCtl.OP_RESET%>">
			        <%
			    		}
			        %>
				    
				</td>
			</tr>

		</table>
		</form>         
  
   <div>
   
    
   </div>
    </div>
    	 
</body>
<%@ include file="Footer.jsp"%>
</html>
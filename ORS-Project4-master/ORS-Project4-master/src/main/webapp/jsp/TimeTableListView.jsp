<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.controller.TimeTableCtl"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.model.CourseModel"%>
<%@page import="in.co.rays.proj4.model.TimeTableModel"%>
<%@page import="in.co.rays.proj4.controller.TimeTableListCtl"%>
<%@page import="in.co.rays.proj4.bean.TimeTableBean"%>
<%@page import="in.co.rays.proj4.model.SubjectModel"%>
<%@page import="in.co.rays.proj4.bean.SubjectBean"%>
<%@page import="in.co.rays.proj4.controller.SubjectListCtl"%>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@page import="in.co.rays.proj4.controller.RoleListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Time-Table List View</title>

<style>
input[type=submit] {
    width: 90px;  height: 30px;
}
input[type=text] {
    width: 200px;  height: 26px;
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
    padding-top: 12px;
    padding-bottom: 12px;
    text-align: center;
    font-size: 20px;
   /*  background-color: #4CAF50; */
    color: black;
}

</style>

<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">  
      <script src="http://code.jquery.com/jquery-1.10.2.js"></script>  
      <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>  
  

 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
	

        <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
         <!--  <script type="text/javascript" src="src/main/webapp/js/calendar.js"></script> -->
<script>
				function disablelink(linkID) {
					var hlink = document.getElementById(linkID);
					if (!hlink)
						return;
					hlink.href = "#";
					hlink.className = "disableLink";
				}
			</script>

<!-- 
<script type="text/javascript"
				src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
 -->
			
  <script language="javascript">
	var d = new Date();
	function noSunday(day){
	return[day.getDay()!=0,false];
};
	$(function() {
		$( "#datepicker1" ).datepicker({
	        defaultDate:new Date(), //set the default date
			changeMonth: true,
			changeYear: true,
			beforeShowDay: noSunday	,  
	        yearRange: '-0:+5', //set the range of years
	        dateFormat: 'dd-mm-yy', //set the format of the date
	        minDate: '+0',
		});
	});
</script>
  
 <!-- <script type="text/javascript">
 	
 	function checkedAll()
 	{
 		var totalElement=document.forms[0].elements.length;
 		for(var i=0;i<totalElement;i++){
 			var en=document.forms[0].elements[i].name;
 			
 			if(en!=undefined & en.indexOf("chk_")!=-1)
 				{
 					document.forms[0].elements[i].checked=document.frm.chk_all.checked;
 				}
 		}
 	}
 	function check(){
 		var en=document.forms[0].element[4].name;
 		if(en!=undefined & en.indexOf("chk_")!=-1)
 			{
 				document.forms[0].elements[4].checked=document.frm.chk_all.unchecked;
 			}
 	}
 
 
 </script>
 	-->		
</head>
<body>
   
<div style="height: 100%; width: 100%">
   
    <jsp:useBean id="bean1" class="in.co.rays.proj4.bean.TimeTableBean" scope="request"></jsp:useBean>
	<jsp:useBean id="model1" class="in.co.rays.proj4.model.TimeTableModel" scope="request"></jsp:useBean>
	<jsp:useBean id="model2" class="in.co.rays.proj4.model.RoleModel" scope="request"></jsp:useBean>
		
	

        <form action="<%=ORSView.TIMETABLE_LIST_CTL%>" method="post">
    <%@include file="Header.jsp"%>
    
    	
        <h1 align="center">TimeTable List</h1>
		<%
            List l = (List) request.getAttribute("courseList");
		
        %>
		
		<%
        	List l1 = (List) request.getAttribute("subjectList");
        %>
      
    
            
         <h4 align="center">
            <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
                    <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
         
         </h4>
<!-- 
<script type="text/javascript"
				src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
 -->
 			<!-- <script  >
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
 -->
 <script type="text/javascript"> 
$(document).ready(function() {
    $("#checkedAll").change(function() {
        if (this.checked) {
            $(".checkSingle").each(function() {
                this.checked=true;
            });
        } else {
            $(".checkSingle").each(function() {
                this.checked=false;
            });
        }
    });

    $(".checkSingle").click(function () {
        if ($(this).is(":checked")) {
            var isAllChecked = 0;

            $(".checkSingle").each(function() {
                if (!this.checked)
                    isAllChecked = 1;
            });

            if (isAllChecked == 0) {
                $("#checkedAll").prop("checked", true);
            }     
        }
        else {
            $("#checkedAll").prop("checked", false);
        }
    });
});
</script>

         
             <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=TimeTableCtl.OP_BACK %>"></td>
                <%}else{ %>
                
                            <table width="100%">
                <tr>
                    <td align="center">
                    	<label><b>Course Name :</b></label> 
                    	<%=HTMLUtility.getList("courseId", String.valueOf(ServletUtility.getParameter("courseId", request)), l)%>
						<font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
						&nbsp; 
                        <label><b>Subject Name :</b></label> 
                        <%=HTMLUtility.getList("subjectId", String.valueOf(ServletUtility.getParameter("subjectId", request)), l1)%>
						<font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
                        &nbsp;
                        
                      	
                      	<label><b>Exam Date :</b></label> 
                        <input type="text" size="20px" name="examDate" id="datepicker1"
						readonly="readonly" placeholder="DD/MM/YYYY"
						value="<%=DataUtility.getDateString(bean1.getExamDate())%>"> <font
						color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>
						&nbsp;
                        
                      
                        <input type="submit" name="operation" value="<%=TimeTableListCtl.OP_SEARCH %>">
                        <input type="submit" name="operation" value="<%=TimeTableListCtl.OP_RESET %>">

                    </td>
                </tr>
                
            </table>
                
                <br>
            <table id="customers">
                <tr>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th><input type="checkbox" id="checkedAll" id="checkedAll" />Select All</th>
                <%
                  	}
                %>    
                    <th>S.No</th>
                    <th>Course Name</th>
                    <th>Semester</th>
                    <th>Subject Name</th>
                    <th>Exam Date</th>
                    <th>Exam Time</th>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th>Edit</th>
                <%
                  	}
                %>
                </tr>
                <%-- <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr> --%>

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                   List list = ServletUtility.getList(request);
                    Iterator<TimeTableBean> it = list.iterator();
                    while (it.hasNext()) {
                    	TimeTableBean bean = it.next();
                %>
                
                <%
             // get Course Name
                CourseModel cModel = new CourseModel();
                CourseBean courseBean = cModel.findByPK(bean.getCourseId());
                bean.setCourseName(courseBean.getName());

                // get Subject Name
                SubjectModel sModel = new SubjectModel();
                SubjectBean subjectBean = sModel.findByPK(bean.getSubjectId());
                bean.setSubjectName(subjectBean.getName());
                
                
                

                %>
                <tr>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                	<td align="center"><input type="checkbox" class="checkSingle"
							name="checkAll" value="<%=bean.getId()%>"></td>
                <%
                  	}
                %>
                 <td align="center"><%=index++%></td>
                    <%-- <td><%=bean.getId()%></td> --%>
                    <td><%=bean.getCourseName()%></td>
                    <td><%=bean.getSemester()%></td>
                    <td><%=bean.getSubjectName()%></td>
                    <td><%=bean.getExamDate()%></td>
                    <td><%=bean.getExamTime()%></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <td><a href="TimeTableCtl?id=<%=bean.getId()%>">Edit</a></td>
                <%
                  	}
                %>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
            <br>
				<tr>
					<td><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_PREVIOUS%>"
						<%
                        	if(pageNo == 1)
                        	{
                        %>
						disabled="disabled"
						<%
                        	}
                        %>
                        ></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>        
					<td><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=TimeTableListCtl.OP_NEW%>"></td>
							
				<%
                 	}
                %>
                
                <%TimeTableModel model=new TimeTableModel(); 
                TimeTableBean b=new TimeTableBean(); %>		
				<td align="right">
					<input type="submit" name="operation" value="<%=TimeTableCtl.OP_NEXT%>"
					<%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
				</tr>
			</table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
            <%} %>
        </form>
</div>
	<div>
	<br>
<br>
<br>
<br>
	
    <%@include file="Footer.jsp"%>
    </div>
</body>
</html>
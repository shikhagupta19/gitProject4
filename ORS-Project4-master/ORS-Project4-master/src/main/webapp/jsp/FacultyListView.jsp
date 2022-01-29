<%@page import="in.co.rays.proj4.bean.CollegeBean"%>
<%@page import="in.co.rays.proj4.model.CollegeModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.FacultyModel"%>
<%@page import="in.co.rays.proj4.bean.SubjectBean"%>
<%@page import="in.co.rays.proj4.model.SubjectModel"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.model.CourseModel"%>
<%@page import="in.co.rays.proj4.bean.FacultyBean"%>
<%@page import="in.co.rays.proj4.controller.FacultyListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility" %>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Faculty List</title>
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
</head>
<body>
    <%@include file="Header.jsp"%>
    <div style="height: 600px; width: 100%">
        <h1 align="center">Faculty List</h1>

        <form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">

                
                    <h3 align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
                     <h3 align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>


<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<SCRIPT language="javascript">
$(function(){

	// add multiple select / deselect functionality
	$("#selectall").click(function () {
		  $('.case').attr('checked', this.checked);
	});

	// if all checkbox are selected, check the selectall checkbox
	// and viceversa
	$(".case").click(function(){

		if($(".case").length == $(".case:checked").length) {
			$("#selectall").attr("checked", "checked");
		} else {
			$("#selectall").removeAttr("checked");
		}

	});
});
</SCRIPT>
                
            <br>
            <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=FacultyListCtl.OP_BACK %>"></td>
                <%}else{ %>


            <table width="100%">
                <tr>
                    <td align="center">
                    <label> FirstName :</label> 
                    <input type="text" name="firstName" placeholder="Enter First Name" 
                    value="<%=ServletUtility.getParameter("firstName", request)%>">
                    <label>LastName :</label> 
                    <input type="text" name="lastName" placeholder="Enter Last Name"
                    value="<%=ServletUtility.getParameter("lastName", request)%>">
                   
                   <%--  <label>Email_Id:</label>
                    <input type="text" name="email" placeholder="Enter EmailID"
                    value="<%=ServletUtility.getParameter("email", request)%>">
                    --%> 
                   <% List collegeList =(List)request.getAttribute("clist");%>
					<label>College Name :</label>
					<%= HTMLUtility.getList("collegeId",String.valueOf(ServletUtility.getParameter("collegeId", request)),collegeList)%> 
                   
                   <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH %>">
                    &nbsp;
                    <input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET %>"></td>
                
                </tr>
            </table>

<br>
            <table id="customers">
                <tr>
                
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th><input type="checkbox" id="selectall"/>Select All</th>
                <%
                    }
                %>
                    
                    <th>S.No</th>
                    <th>Course Name</th>
                    <th>Subject Name</th>
                    <th>College Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Of Birth</th>
                    <th>Email ID</th>
                    <th>Mobile No</th>
                 <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>   
                    <th>Edit</th>
                <%
                    }
                %>
                    
                </tr>
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<FacultyBean> it = list.iterator();

                    while (it.hasNext()) {

                        FacultyBean bean = it.next();
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
 
                // get College Name
                  CollegeModel ccModel = new CollegeModel(); 
                CollegeBean collegeBean = ccModel.findByPK(bean.getCollegeId());
                bean.setCollegeName(collegeBean.getName());
  
                
                
                
                %>
                <tr align="center">
                  
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{ 
        		%>
                	<td align="center"><input type="checkbox" name="ids" class="case" value="<%=bean.getId()%>"></td>
                <%
                    }
                %>
                	 <td align="center"><%=index++%></td>
                    <%-- <td><%=bean.getId()%></td> --%>
                  	<td><%=bean.getCourseName()%></td>
                    <td><%=bean.getSubjectName()%></td>
                     <td><%=bean.getCollegeName()%></td>
 					<td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getEmail()%></td>
                    <td><%=bean.getMobileNo()%></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>  
                  
                    <td><a href="FacultyCtl?id=<%=bean.getId()%>">Edit</a></td>
                <%
                    }
                %>
                    
                  </tr>
                <%
                    }
                %>
            
            </table>
            
            <table width="100%">
       
                <tr>
                    <td><input type="submit" name="operation"
                        value="<%=FacultyListCtl.OP_PREVIOUS%>"
                        <%
                         	if(pageNo==1){
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
                          <td align="center"><input type="submit" name="operation"
                        value="<%=FacultyListCtl.OP_DELETE%>"></td>
                          <td align="center"><input type="submit" name="operation"
                        value="<%=FacultyListCtl.OP_NEW%>"></td>
                <%
                    }
                %>
                     
                     <%FacultyModel model=new FacultyModel(); 
                     FacultyBean b=new FacultyBean(); %>     
                    <td align="right"><input type="submit" name="operation"
                        value="<%=FacultyListCtl.OP_NEXT%>" <%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
                </tr>
            </table>
            
             
       <input type="hidden" name="pageNo" value="<%=pageNo%>"><input
                type="hidden" name="pageSize" value="<%=pageSize%>">

<%} %>


        </form>
  
   
   
        </div>
        
    
 <%@include file="Footer.jsp"%>
</body>
</html>
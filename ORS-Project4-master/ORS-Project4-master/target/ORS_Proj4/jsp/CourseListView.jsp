<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="in.co.rays.proj4.model.CourseModel"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.controller.CourseListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Course List</title>
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
   
        <h1 align="center">Course List</h1>

        <form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">
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

            <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=CourseListCtl.OP_BACK %>"></td>
                <%}else{ %>

            <table width="100%">
                <tr>
                    <td align="center">
                    <label><b>Course Name:<b></label>
                     <input type="text" name="name" placeholder="Enter Course Name"
                     value="<%=ServletUtility.getParameter("name", request)%>" placeholder="Enter Course Name">
                     &nbsp;
                     <input type="submit" name="operation" value="<%=CourseListCtl.OP_SEARCH %>">
                  &nbsp;
                     <input type="submit" name="operation" value="<%=CourseListCtl.OP_RESET %>">
                  
                    </td>
                    <tr>
                    <td colspan="8" align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                    <td colspan="8" align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
                </tr>
                    
                </tr>
            </table>
<br>

            <!-- <table border="1" width="100%"> -->
            <table id="customers">
                <tr>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th align="center"><input type="checkbox" id="selectall"/>Select All</th>
                <%
                    }
                %>
                    <!-- <th align="center">Id</th> -->
                    <th align="center">S.No</th>
                    <th align="center">Name</th>
                    <th align="center">Description</th>
                 <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th align="center">Edit</th>
                <%
                    }
                %>
                </tr>
<%--                 <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
 --%>
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<CourseBean> it = list.iterator();
                    while (it.hasNext()) {
                        CourseBean bean = it.next();
                %>
                <tr>
                    
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>    
                    <td align="center"><input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>"></td>
                <%
                    }
                %>    
                    <%-- <td align="center"><%=bean.getId()%></td> --%>
                    <td align="center"><%=index++%></td>
                    <td align="center"><%=bean.getName()%></td>
                    <td align="center"><%=bean.getDescription()%></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <td align="center"><a href="CourseCtl?id=<%=bean.getId()%>">Edit</a></td>
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
                        value="<%=CourseListCtl.OP_PREVIOUS%>" 
                         <%=(pageNo==1)?"disabled":"" %>></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>        
                    <td align="center"><input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_DELETE%>"></td>
                        
                    <td align="center"><input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_NEW%>" width="58" height="100"></td>
                <%
                    }
                %>        
                        <%CourseModel model=new CourseModel(); %>
                         <%CourseBean b=new CourseBean(); %>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=CourseListCtl.OP_NEXT%>"<%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
                <%} %>
    
        </form>
    
    <br>
    <br>
    
    
    
    </div>
<%@include file="Footer.jsp"%>
</body>
</html>
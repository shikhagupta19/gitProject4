<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.model.SubjectModel"%>
<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.model.CourseModel"%>
<%@page import="in.co.rays.proj4.controller.SubjectListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.SubjectBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student ListView</title>
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
         

</head>
<body>
    <%@include file="Header.jsp"%>
 		<div style="height: 600px; width: 100%">
    <jsp:useBean id="bean1" class="in.co.rays.proj4.bean.SubjectBean" scope="request"></jsp:useBean>
        <h1 align="center">Subject List</h1>

        <form action="<%=ORSView.SUBJECT_LIST_CTL%>" method="post">
 		
         <%
            List l = (List) request.getAttribute("courseList");
        %>

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
        
        
           <h3 align="center">
            <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
                    <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
         
         </h3>
        
        
        

            <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=SubjectListCtl.OP_BACK %>"></td>
                <%}else{ %>
            <table width="100%">
                <tr>
                    <td align="center">
                    <label><b>Name</label> 
                    <input type="text" name="name" placeholder="Enter Subject Name"
                    value="<%=ServletUtility.getParameter("name", request)%>">
                    &nbsp; 
                <label><b>Course Name :</b></label> 
                    	<%=HTMLUtility.getList("courseId", String.valueOf(ServletUtility.getParameter("courseId", request)), l)%>
						<font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
						
                    <input type="submit" name="operation" value="<%=SubjectListCtl.OP_SEARCH %>">
                     <input type="submit" name="operation" value="<%=SubjectListCtl.OP_RESET %>">
                    
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
                    <th><input type="checkbox" id="selectall"/>Select All</th>
                <%
                    }
                %>
                    <th>S.No</th>
                    <th>Course Name</th>
                    <th>Subject Name</th>
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
                    Iterator<SubjectBean> it = list.iterator();
                    while (it.hasNext()) {
                        SubjectBean bean = it.next();
                %>
                 <%
             // get Course Name
                CourseModel cModel = new CourseModel();
                CourseBean courseBean = cModel.findByPK(bean.getCourseId());
                bean.setCourseName(courseBean.getName());

                %>
                
                <tr>
                    <%-- <td><%=index++%></td> --%>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <td align="center"><input type="checkbox" name="ids" class="case" value="<%=bean.getId()%>"></td>
                <%
                    }
                %>
                <td><%=index++%></td>
                    <%-- <td><%=bean.getId()%></td> --%>
                    <td><%=bean.getCourseName()%></td>
                    <td><%=bean.getName()%></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <td><a href="SubjectCtl?id=<%=bean.getId()%>">Edit</a></td>
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
                        value="<%=SubjectListCtl.OP_PREVIOUS%>"
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
                        value="<%=SubjectListCtl.OP_DELETE%>"></td>
                        
                    <td align="center"><input type="submit" name="operation"
                        value="<%=SubjectListCtl.OP_NEW%>"></td>
                <%
                    }
                %>  
                
                <%
                	SubjectModel model=new SubjectModel(); 
                	SubjectBean b=new SubjectBean(); 
                %>      
                    <td align="right">
                    <input type="submit" name="operation" value="<%=SubjectListCtl.OP_NEXT%>" 
                    <%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
                <%} %>
        </form>
      
    

    
    
</div>
<%@include file="Footer.jsp"%>  
</body>
</html>
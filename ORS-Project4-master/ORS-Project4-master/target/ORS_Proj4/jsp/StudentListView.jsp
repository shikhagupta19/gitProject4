<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.StudentModel"%>
<%@page import="in.co.rays.proj4.controller.StudentListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.StudentBean"%>
<%@page import="in.co.rays.proj4.model.UserModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility" %>
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
 /* 	background-color: #A9A9A9;  */
    color: black;
    
}

</style>

</head>
<body>
    <%@include file="Header.jsp"%>


    <div style="height: 600px; width: 100%">
        <h1 align="center">Student List</h1>

        <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">


<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
			<script  type="text/javascript">
				$(function() {

					// add multiple select / deselect functionality
					$("#selectall").click(function() {
						$('.case').attr('checked', this.checked);
					});

					// if all checkbox are selected, check the selectall checkbox
					// and viceversa
					$('.case').click(function() {

						if ($('.case').length == $(".case:checked").length) {
							$("#selectall").attr("checked", "checked");
						} else {
							$("#selectall").removeAttr("checked");
						}

					});
				});
			</script>


                
                    <h3 align="center" colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </h3>
                
                    <h3 align="center" colspan="8"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
                </h3>
            <br>
            <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=StudentListCtl.OP_BACK %>"></td>
                <%}else{ %>
            <table width="100%">
                
                <tr>
                    <td align="center">
                    
                    	<%-- <label> FirstName :</label> <input
                        type="text" name="firstName" placeholder="Enter First Name"
                        value="<%=ServletUtility.getParameter("firstName", request)%>">
                        &nbsp;
                        <label>LastName :</label> <input type="text" name="lastName" placeholder="Enter Last Name"
                        value="<%=ServletUtility.getParameter("lastName", request)%>">  --%>
                        
                        
                        <% List studentList =(List)request.getAttribute("slist");%>
						<label>Student Name :</label>
						<%= HTMLUtility.getList("studentId",String.valueOf(ServletUtility.getParameter("studentId", request)),studentList)%> 
						
                 
                       
                        &nbsp;<label>Email_Id:</label> <input type="text" name="email" placeholder="Enter Email Id"
                        value="<%=ServletUtility.getParameter("email", request)%>">
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>">
                       &nbsp;
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET %>">
                        
						</td>
                </tr>
                
            </table>
<br>
            <table id="customers">
            <!-- <table border="1" width="100%"> -->
                <tr>
                  <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <th align="center"><input type="checkbox" id="selectall"/>Select All</th>
                <%
                    }
                %>
                   
                    <th>S.No.</th>
                    <th>College Name</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Of Birth</th>
                    <th>Mobile No</th>
                    <th>Email ID</th>
                    <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                   <th>Edit</th>
                <%
                    }
                %>
                </tr>
               <%--  <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
                --%> <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;
                    StudentBean bean=null;

                    List list = ServletUtility.getList(request);
                    Iterator<StudentBean> it = list.iterator();

                    while (it.hasNext()) {

                         bean = it.next();
                %>
                <tr>
                    <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%> 
                    <td><input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>"></td>
                   <%} %>
                    <td><%=index++%></td>
                    <td><%=bean.getCollegeName()%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><%=bean.getEmail()%></td>
                    <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY || userBean.getRoleId() == RoleBean.College)
	        		{
        		%>
                    <td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value="<%=StudentListCtl.OP_PREVIOUS%>"
						<% 
                          if(pageNo==1)
                          {
                        %>
						disabled="disabled"
						<%	
                          }
                        %>></td>
					<td align="center"> <input type="submit" name="operation" value="<%=StudentListCtl.OP_DELETE%>"></td>
					<td align="center"><input type="submit" name="operation" value="<%=StudentListCtl.OP_NEW%>"
					 <%if(list.size()==0) {%>
						disabled="disable" 
						<%} %>>
					</td>
					<td align="right">
					<input type="submit" name="operation" value="<%=StudentListCtl.OP_NEXT%>"
						<%StudentModel model=new StudentModel();%>
						<%if((list.size()<pageSize)||model.nextPK()-1==bean.getId()){
						%>
						disabled="disabled"
						<%
                        
                        }
                        
                        %>></td>
				</tr>
			</table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"><input
                type="hidden" name="pageSize" value="<%=pageSize%>">

		<%} %>
        </form>
   
   <div><br>
<br>


   
    
    </div>
    </div>
    
</body>
<%@include file="Footer.jsp"%>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.CollegeModel"%>
<%@page import="in.co.rays.proj4.controller.CollegeCtl"%>
<%@page import="in.co.rays.proj4.controller.CollegeListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.CollegeBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility" %>
 

<head>
<meta charset="ISO-8859-1">
<title>College List</title>


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


<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<script language="javascript">
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
</script>
        

</head>
<body>

     <%@include file="Header.jsp"%>
    <div style="height: 600px; width: 100%">
        <h1 align="center">College List</h1>

        <form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">
        
         
                    <h3 align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
                      <h3 align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
                


            <br>
             <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=CollegeListCtl.OP_BACK %>"></td>
                <%}else{ %>
                
                
                
            <table width="100%">
                <tr>
                    <td align="center" height="60%">
                    
                   <%--  <label> <b>Name: <b></label>
                    <input type="text" name="name" placeholder="Enter College Name"
                    value="<%=ServletUtility.getParameter("name", request)%>">
                    &nbsp; --%>
                    
                    <% List collegeList =(List)request.getAttribute("clist");%>
					<label>College Name :</label>
					<%= HTMLUtility.getList("collegeId",String.valueOf(ServletUtility.getParameter("collegeId", request)),collegeList)%> 
                    
                    
                    &nbsp;
                    <label>City: </label> 
                    <input type="text" name="city" placeholder="Enter City"
                    value="<%=ServletUtility.getParameter("city", request)%>">
                    <input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH%>">
                   &nbsp;
                     <input type="submit" name="operation" value="<%=CollegeListCtl.OP_RESET%>">
                    
                    </td>
                    
                </tr>
                
                 
            </table>
                <br>
            <table id="customers">
            <!-- <table border="1" width="100%"> -->
                <tr>
                <%         
                	if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY)
	        		{
        		%>
                    <th><input type="checkbox" id="selectall"/>Select All</th>
                <%
	        		}
	        	%>
                    <th>S.No</th>
                    <th>College Name</th>
                    <th>Address</th>
                    <th>State</th>
                    <th>City</th>
                    <th>PhoneNo</th>
                    <!-- <th>Edit</th> -->
                <%         	
                	if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY)
	        		{
        		%>
            		<th>Edit</th>
            	<%
	        		}
	        		
				%>
                </tr>
                <%-- <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr>
                 --%><%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<CollegeBean> it = list.iterator();

                    while (it.hasNext()) {

                        CollegeBean bean = it.next();
                %>
                <tr>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY)
	        		{
        		%>
                	<td align="center"><input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>"></td>
                <%
	        		}
	        	%>
                    <td><%=index++%></td>
                    <%-- <td><%=bean.getId()%></td> --%>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getAddress()%></td>
                    <td><%=bean.getState()%></td>
                    <td><%=bean.getCity()%></td>
                    <td><%=bean.getPhoneNo()%></td>
                    <%-- <td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td> --%>
            	<%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY)
	        		{
        		%>
            		<td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td> 
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
                    <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>"
                    <%=(pageNo==1)?"disabled":"" %>></td>
                <%         	
            		if(userBean.getRoleId() == RoleBean.ADMIN || userBean.getRoleId() == RoleBean.FACULTY)
	        		{
        		%>    
					<td align="center"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_DELETE%>"></td>
                    <td align="center"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>"></td>
                <%
	        		}
	        	%>
            
                     <%CollegeModel model=new CollegeModel(); %>
                         <%CollegeBean b=new CollegeBean(); %>
                    <td align="right"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>"
                    <%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
                    
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
        <%} %>
        
        
        </form>
        
        

</div>

</body>
<%@include file="Footer.jsp"%>
</html>


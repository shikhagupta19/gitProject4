<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.model.RoleModel"%>
<%@page import="in.co.rays.proj4.controller.RoleListCtl"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Role List</title>
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
	<div style="height: 427px; width: 100%">
        <h1 align="center">Role List</h1>

        <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<SCRIPT type="text/javascript">
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

            <table width="100%">
                <tr>
                    <td align="center">
                    <label>Name :</label> 
                    <input type="text" name="name" placeholder="Enter Role"
                    value="<%=ServletUtility.getParameter("name", request)%>">
                    &nbsp;
                     <input type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH %>">
                   
                    </td>
                </tr>
                <tr>
                    <td align="center"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                     <td align="center"><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></td>
                </tr>
            </table>
             <%
               
                List list2 = ServletUtility.getList(request);
                           
                if (list2 == null || list2.size() == 0){%>
                <td>  <input type="submit" name="operation" value=" <%=RoleListCtl.OP_BACK %>"></td>
                <%}else{ %>
            <table id="customers">
                <tr>
                    <th><input type="checkbox" id="selectall"/>Select All</th>
                    <th>S.No</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Edit</th>
                </tr>
                <%-- <tr>
                    <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </tr> --%>

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        RoleBean bean = (RoleBean)it.next();
                %>
                <tr>
                    
                    <td align="center"><input type="checkbox" name="ids" class="case" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                    
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getDescription()%></td>
                    <td><a href="RoleCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
            <br>
                <tr>
                    <td><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_PREVIOUS%>"
                        <%
                         	if(pageNo==1){
                       	%>
                       		disabled="disabled"
                        <%
                         	}
                        %>
                        ></td>
                        
                    <td align="center"><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_DELETE%>"></td>
                        
                    <td align="center"><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_NEW%>"></td>
                
                <%RoleModel model=new RoleModel(); 
                RoleBean b=new RoleBean(); %>          
                    <td align="right"><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_NEXT%>" <%=((list.size()<pageSize)||model.nextPK()-1==b.getId())?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
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
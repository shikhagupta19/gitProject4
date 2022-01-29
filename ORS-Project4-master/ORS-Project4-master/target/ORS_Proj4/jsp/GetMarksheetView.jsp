<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="in.co.rays.proj4.controller.GetMarksheetCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>GetMarksheet View</title>
<style>
#tab {
	/* width:"99%"; */
	/* position: fixed; */
	align: center;
}

input[type=submit] {
	width: 88px;
	height: 30px;
}

input[type=text] {
	width: 200px;
	height: 26px;
}

#customers {
	border-collapse: collapse;
	width: 70%;
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

#custom {
	border: 0px;
	width: 70%;
	text-align: left;
}

#custom td, #custom th {
	padding: 8px;
	text-align: left;
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
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarksheetBean"
		scope="request"></jsp:useBean>

	<div style="height: 600px; width: 100%" align="center">
		<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
			<br><br><br><h2 align="center">Get Marksheet</h2>
			<H2 align="center">
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2 align="center">
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>



			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table id="tab" style="position: absolute; left: 590px; height: 50px;" >
			
			<tr>
			    <th>Roll No<font color="red">*</font>&nbsp;&nbsp;&nbsp;</th>
				<td><input type="text" name="rollNo" placeholder="Enter roll No" value="<%=ServletUtility.getParameter("rollNo", request)%>">
				<font color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
                
            </tr>
                        
			<tr>
			<th></th>
			<td colspan="2">
		         <input type="submit" name="operation"	value="<%=GetMarksheetCtl.OP_GO%>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="operation"	value="<%=GetMarksheetCtl.OP_RESET%>">
				</td>
				
           </tr>
			</table>
			<br>
			<%
				if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {

					int p = bean.getPhysics();
					int c = bean.getChemistry();
					int m = bean.getMaths();
					int total = p + c + m;
					double per = total * 100 / 300;
					//per /= 100;

					String grade = "";
					String result = "";
					if (p >= 35 && c >= 35 && m >= 35) {
						if (total >= 250) {
							grade = "A";
							result = "PASS";
						} else if (total < 250 && total >= 200) {
							grade = "B";
							result = "PASS";
						} else if(total < 200 && total >= 120) {
							grade = "C";
							result = "PASS";
						}
					} else {
						grade = "D";
						result = "FAIL";

					}
			%>
			<br>
			<table id="custom">
              <tr><td></td></tr>
             <tr><td></td></tr>
             <tr><td></td></tr>
             <tr><td></td></tr>
             	<tr>
					<td><label><b>Name:</b></label>&emsp;&emsp;&emsp;<b><%=bean.getName()%></b></td>

				</tr>
				<tr>
					<td align="Left" width="20%"><label><b>Roll No:</b></label>&emsp;&emsp;&emsp;<b><%=bean.getRollNo()%></b></td>
				</tr>

			</table>
			<br>
			<table id="customers">

				<tr>
					<th>Subject</th>
					<th>Maximum Mark</th>
					<th>Minimum Mark</th>
					<th>Obtained Mark</th>
				</tr>
				<tr>
					<td align="center">Physics</td>
					<td align="center"><%=100%></td>
					<td align="center"><%=35%></td>
					<td align="center"><%=bean.getPhysics()%><font color="red"><%=(bean.getPhysics() >= 35) ? "" : "*"%></font></td>
				</tr>
				<tr>
					<td align="center">Chemistry</td>
					<td align="center"><%=100%></td>
					<td align="center"><%=35%></td>
					<td align="center"><%=bean.getChemistry()%><font color="red"><%=(bean.getChemistry() >= 35) ? "" : "*"%></font></td>
				</tr>
				<tr>
					<td align="center">Maths</td>
					<td align="center"><%=100%></td>
					<td align="center"><%=35%></td>
					<td align="center"><%=bean.getMaths()%><font color="red"><%=(bean.getMaths() >= 35) ? "" : "*"%></font></td>
				</tr>
				<tr>
					<td align="center"><b>Total</b></td>
					<td align="center"><b><%=300%></b></td>
					<td></td>
					<td align="center"><b><%=total%></b></td>
				</tr>
			</table>
			<br>
			<table id="customers">
				<tr>
					<th>Result:</th>
					<%
						if ("PASS".equalsIgnoreCase(result)) {
					%><td align="center"><font color="green"><%=result%></font></td>
					<th>Grade:</th>
					<td align="center"><font color="green"><%=grade%></font></td>
					<%
						} else {
					%>
					<td align="center"><font color="red"><%=result%></font></td>
					<th>Grade:</th>
					<td align="center"><font color="red"><%=grade%></font></td>

					<%
						}
					%>
					<th>Percentage:</th>
					<td align="center"><%=per%>%</td>
				</tr>
			</table>
			<%
				}
			%>

		</form>
	
	</div>
	<div align="bottom">
	<%@ include file="Footer.jsp"%>
	</div>
	
</body>
</html>
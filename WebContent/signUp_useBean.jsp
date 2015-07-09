
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="crudclasses.User"%>
<%@page import=" java.util.Date"%>
<%@page import="listiners.Connect_db"%>
<%@page import="crudclasses.User_CRUDOperation"%>
<%@page import="org.neo4j.graphdb.GraphDatabaseService"%>
<%@page import="javax.mail.Session"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>creating user</title>
</head>
<body>
	<jsp:useBean id="userBean" class="crudclasses.User" scope="request"></jsp:useBean>
	<jsp:setProperty property="*" name="userBean" />
	<%  GraphDatabaseService gds=null;%>
	<% DateFormat dateFormate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");%>
	<% Date date = new Date();%>
	<%
		Object object = getServletContext().getAttribute(
				Connect_db.KEY_GRAPH_DATABASE);
	%>

	<%
		if (object instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) object;
		}
	%>
	  <%
		String firstName =request.getParameter("firstName").trim().toString();
	%>
	<%
		String lastName = userBean.getLastName().trim().toString();
	%>
	<%
		String age = userBean.getAge().trim().toString();
	%>
	<%
		String gender = userBean.getGender().trim().toString();
	%>
	<%
		String dateOfBirth = userBean.getDateOfBirth().trim().toString();
	%>
	<%
		String password = userBean.getPassword().trim().toString();
	%>
	<%
		String reEnteredPassword = request.getParameter("reenterPassword").trim()
				.toString();
	%>


	<%
		User user = new User(firstName, lastName, password, gender,
				dateOfBirth, age, dateFormate.format(date));
	%>
	<%
		String errorMessage = User_CRUDOperation.nullCheck(gds, userBean,
				request.getParameter("reenterPassword").toString());
		if (errorMessage == null) {
			
			getServletContext().getRequestDispatcher("/signUp2.html")
					.include(request, response);

		} else {
			getServletContext().getRequestDispatcher("/submit.html")
					.include(request, response);
		}
	%>

</body>
</html>
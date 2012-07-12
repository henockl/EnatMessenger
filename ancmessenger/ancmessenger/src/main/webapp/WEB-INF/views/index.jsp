<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Data Clerk's Home Page</title>
</head>
<body>
	<h1>Data Clerk's Home Page</h1>
	<p>
		Welcome <b>${username}</b> to the data clerk's home page. You have logged in with
		a data clerk account and with the privilege you have, you can:
	</p>
	<ul>
		<li>Manage Mothers' Data</li>
		<li>View Unconfirmed Visits of Mothers with 1 Month to EDD</li>
		<li>View Unconfirmed Visits of Mothers with 1 Week to EDD</li>
	</ul>
	<p>
		You can <i><a href="/ancmessenger/mother">Start Here</a></i> or alternatively,
		click on one of the menu items above or at the side bar.
	</p>
</body>
</html>

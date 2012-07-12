<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Administrator's Home Page</title>
</head>
<body>
	<h1>Administrator's Home Page</h1>
	<p>
		Welcome <b>${username}</b> to the administrator's home page. You have logged in with
		an administrative account and with the privilege you have, you can:
	</p>
	<ul>
		<li>Manage Health Center Data</li>
		<li>Manage Health Posts Under Health Center</li>
		<li>Manage Health Workers' Data</li>
		<li>Manage Gotts Under Each Health Post</li>
		<li>Manage Transporters' Data</li>
        <li>Send Quarterly Report</li>
	</ul>
	<p>
		You can <i><a href="/ancmessenger/admin/healthpost?pid=1">Start Here</a></i> or alternatively,
		click on one of the menu items above or at the side bar.
	</p>
</body>
</html>
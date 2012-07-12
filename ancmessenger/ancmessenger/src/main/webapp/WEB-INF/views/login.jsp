<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>
</head>
<body onload='document.f.j_username.focus();'>
	
	<div style="width: 300px; margin: 0 auto">
		<div id="titleBar" style="width: 300px">Login</div>
		<div id="inputArea" style="width: 300px">
			<form name="f" method="POST" action="<c:url value='j_spring_security_check' />">
	
				<label for="j_username">Username: </label>
				<div><input type='text' name='j_username' value='' style="width: 250px"></div>
				
				<label for="j_password">Password: </label>
				<div><input type='password' name='j_password' style="width: 250px" /></div>
				
				<label style="color: red">${error}</label>
				<br/>
				<input type="submit" value="Login" id="loginBtn" />&nbsp;&nbsp;
				<input type="reset" value="Reset" id="resetBtn" />
			</form>
		</div>
	</div>
</body>
</html>
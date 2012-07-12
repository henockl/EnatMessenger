<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title><sitemesh:write property="title" /></title>
	<sitemesh:write property="head" />
	 
	<link rel="stylesheet" href='<s:url value="/resources" />/style.css' type="text/css" media="screen" />
	 
</head>
<body>
	<div id="container">
		<div id="page_header">
			<div id="page_heading">
				<h1>ENAT Messenger System</h1>
			</div>
			
			<div class="clearthis">&nbsp;</div>
		</div>
		
		<div id="page_menu">
			<ul>
				
			</ul>	
		</div>
		
		<div id="main_content">
			<sitemesh:write property="body" />
			<div class="clearthis">&nbsp;</div>
			
		</div>
		
		<div id="page_footer"/>
			<div id="product_brands">
				<div id="copyright">Copyright &copy; Clinton Health Access Initiative</div>
			</div>
			
			<div class="clearthis">&nbsp;</div>
		</div>	
	</div>
</body>
</html>
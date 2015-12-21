<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="offerList">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Offers - Home</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.8/angular.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-rc.0/angular-route.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/utils.js"></script>
<script
	src="${pageContext.request.contextPath}/static/js/angular/app.js"></script>
</head>
<body>
	<div ng-view></div>
</body>
</html>
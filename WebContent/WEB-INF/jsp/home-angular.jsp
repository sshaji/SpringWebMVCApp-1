<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Offers - Home</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/angular/css/styles.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.8/angular.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0-rc.0/angular-route.min.js"></script>
<script
	src="${pageContext.request.contextPath}/static/angular/src/app.js"></script>
</head>
<body>
	<jsp:include page="logout_include.jsp"></jsp:include>
	<div class="container">
		<div class="header clearfix">
			<nav>
				<ul class="nav nav-pills pull-right">
					<li role="presentation"><a href="#">Home</a></li>
					<li role="presentation"><a href="javascript:logout()">Logout</a></li>
				</ul>
			</nav>
			<h3>Offers</h3>
			<h5 class="text-muted">GUI - using AngularJS and Bootstrap CSS</h5>
		</div>
		<div ng-view ng-app="offersApp"></div>
		<footer class="footer">
			<p>&copy; 2015 Shaji.</p>
		</footer>
	</div>
</body>
</html>
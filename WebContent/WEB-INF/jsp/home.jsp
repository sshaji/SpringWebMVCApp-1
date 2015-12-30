<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
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
</head>
<body>
	<sql:query var="rs" dataSource="jdbc/testDb">
		select name, email, offerdetails from offers
	</sql:query>
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
			<h5 class="text-muted">GUI - using JSP</h5>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Offers</div>
			<div class="panel-body">
				<table class="table table-striped">
					<c:forEach var="row" items="${rs.rows}">
						<tr>
							<td>${row.name}</td>
							<td>${row.email}</td>
							<td>${row.offerdetails}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<footer class="footer">
			<p>&copy; 2015 Shaji.</p>
		</footer>
	</div>
</body>
</html>
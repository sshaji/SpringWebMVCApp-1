<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<br>
		<div class="jumbotron">
			<h1>Offers - Sample</h1>
			<h4>Using Spring framework, Hibernate, AngularJS and Bootstrap
				CSS</h4>
		</div>
		<c:if test="${param.error != null}">
			<div class="label label-danger">
				<label>Invalid User Name / Password. Please try again.</label>
			</div>
		</c:if>
		<c:if test="${param.logout != null}">
			<div class="label label-success">
				<label>You have been logged out successfully.</label>
			</div>
		</c:if>
		<h4 class="form-signin-heading">Please sign in..</h4>
		<div style="width: 250px">
			<form name='f' action='${pageContext.request.contextPath}/login'
				method='POST' class="form-signin">
				<input type='text' name='username' value='' placeholder="User Name"
					class="form-control" required autofocus> <br> <input
					type='password' name='password' placeholder="Password"
					class="form-control" /> <br> <input name="submit"
					type="submit" value="Login"
					class="btn btn-lg btn-primary btn-block" /> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</body>
</html>
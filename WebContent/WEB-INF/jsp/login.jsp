<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="includes/meta.jsp"></jsp:include>
<title>Login</title>
<jsp:include page="includes/css.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<br>
		<div class="jumbotron">
			<h1>Offers</h1>
			<p>J2EE application using Spring framework, Hibernate, MySQL,
				AngularJS and Bootstrap CSS</p>
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
			<form class="form-signin" name='f'
				action='${pageContext.request.contextPath}/login' method='POST'
				class="form-signin">
				<input type='text' name='username' value='' placeholder="User Name"
					class="form-control" required autofocus> <br> <input
					type='password' name='password' placeholder="Password"
					class="form-control" />
				<div class="checkbox">
					<label> <input type="checkbox" name="remember-me">
						Remember me
					</label>
				</div>
				<input name="submit" type="submit" value="Login"
					class="btn btn-sm btn-primary btn-block" /> <input type="hidden"
					name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</body>
</html>
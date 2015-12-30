<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="logoutUrl" value="/logout" />
<form action="${logoutUrl}" method="post">
	<input class="btn btn-sm btn-warning" type="submit" value="Log out" /> <input
		type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
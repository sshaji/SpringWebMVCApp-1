<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
</head>
<body>
	<b>Offers</b>
	<p />
	<sql:query var="rs" dataSource="jdbc/testDb">
		select name, email, offerdetails from offers
	</sql:query>
	<table border="1">
		<c:forEach var="row" items="${rs.rows}">
			<tr>
				<td>${row.name}</td>
				<td>${row.email}</td>
				<td>${row.offerdetails}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
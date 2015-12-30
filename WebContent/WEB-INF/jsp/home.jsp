<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="includes/meta.jsp"></jsp:include>
<title>Offers - Home</title>
<jsp:include page="includes/css.jsp"></jsp:include>
</head>
<body>
	<sql:query var="rs" dataSource="jdbc/testDb">
		select name, email, offerdetails from offers
	</sql:query>
	<div class="container">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="panel panel-default">
			<div class="panel-heading">Offers - using JSP</div>
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
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>
</body>
</html>
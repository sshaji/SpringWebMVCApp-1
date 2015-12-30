<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="includes/meta.jsp"></jsp:include>
<title>Offers - Home</title>
<jsp:include page="includes/css.jsp"></jsp:include>
<jsp:include page="includes/js.jsp"></jsp:include>
</head>
<body>
	<div class="container">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div ng-view ng-app="offersApp"></div>
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Offers - Home</title>
<base href="${pageContext.request.contextPath}/">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.2.3/backbone-min.js"></script>
<script src="static/js/backbone/app.js"></script>
<script src="static/js/backbone/collections/offer.js"></script>
<script src="static/js/backbone/views/offer_list.js"></script>
<script src="static/js/backbone/views/offer_edit.js"></script>
<script src="static/js/backbone/models/offer.js"></script>
<script src="static/js/backbone/routers/offer.js"></script>
<script src="static/js/backbone/templates.js"></script>
</head>
<body>
	<!-- BEGIN: Backbone code. -->
	<div class="panel panel-default">
		<div class="panel-heading">Offers - using REST API and
			Backbone.js</div>
		<div class="panel-body"></div>
	</div>
	<script>
		App.init();
		var router = new App.Routers.Offer();
		var listView = new App.Views.OfferListView();
		var editView = new App.Views.OfferEditView()
		with (router) {
			on('route:home', function() {
				listView.render();
			});
			on('route:editOffer', function(id) {
				editView.render({
					router : router,
					id : id
				});
			});
		}
		Backbone.history.start();
	</script>
	<!-- END: Backbone code. -->
</body>
</html>
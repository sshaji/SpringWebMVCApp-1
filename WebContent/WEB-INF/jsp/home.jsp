<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.8.3/underscore-min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.2.3/backbone-min.js"></script>
</head>
<body>
	<!-- BEGIN: Backbone code. -->
	<div class="panel panel-default">
		<div class="panel-heading">Offers - using REST API and
			Backbone.js</div>
		<div class="panel-body"></div>
	</div>

	<script type="text/template" id="offer-list-template">
	<a class="btn btn-primary" href="#/new">Create New</a>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Id</th>
				<th>Name</th>
				<th>Email</th>
				<th>Offer</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<@ _.each(rc.offers, function(offer) { @>
			<tr>
				<td><@= offer.get('id') @></td>
				<td><@= offer.get('name') @></td>
				<td><@= offer.get('email') @></td>
				<td><@= offer.get('offerDetails') @></td>
				<td><a class="btn btn-info btn-sm" href="#/edit/<@= offer.get('id') @>">Edit</a></td>
			</tr>
			<@ }); @>
		</tbody>
	</table>
	</script>
	<script type="text/template" id="edit-offer-template">
	<form class="edit-user-form">
		<legend><@= rc.offer ? 'Edit' : 'New' @> User</legend>
		<label>Name</label>
		<input name="name" type="text" value="<@= rc.offer ? rc.offer.get('name') : '' @>" class="form-control">
		<label>Email</label>
		<input name="email" type="text" value="<@= rc.offer ? rc.offer.get('email') : '' @>" class="form-control">
		<label>Offer</label>
		<input name="offerDetails" type="text" value="<@= rc.offer ? rc.offer.get('offerDetails') : '' @>" class="form-control">
		<hr />
		<button type="submit" class="btn btn-success"><@= rc.offer ? 'Update' : 'Create' @></button>
		<@ if (rc.offer) { @>
			<input type="hidden" name="id" value="<@= rc.offer ? rc.offer.get('id') : '' @>">
			<button class="btn btn-danger delete">Delete</button>
		<@ } @>
	</form>
	</script>

	<script>
		$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
			options.url = 'http://localhost:8080/SpringWebMVCApp-1/rest/v1'
					+ options.url;
		});

		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@(.+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};
		_.templateSettings.variable = "rc";

		var Offers = Backbone.Collection.extend({
			url : '/offers'
		});
		var Offer = Backbone.Model.extend({
			urlRoot : '/offers',
			defaults : {
				name : '',
				email : '',
				offerDetails : ''
			},
		});

		var OfferListView = Backbone.View.extend({
			el : '.panel-body',
			render : function() {
				var offers = new Offers();
				var that = this;
				offers.fetch({
					success : function(offers) {
						var template = _.template($('#offer-list-template')
								.html());
						var templateData = {
							offers : offers.models
						};
						that.$el.html(template(templateData));
					}
				})
			}
		});
		var offerListView = new OfferListView();

		var OfferEditView = Backbone.View
				.extend({
					el : '.panel-body',
					render : function(options) {
						if (options.id) {
							this.offer = new Offer({
								id : options.id
							});
							var that = this;
							this.offer.fetch({
								success : function(offer) {
									var template = _.template($(
											'#edit-offer-template').html());
									var templateData = {
										offer : offer
									};
									that.$el.html(template(templateData));
								}
							});
						} else {
							var template = _.template($('#edit-offer-template')
									.html());
							var templateData = {
								offer : null
							};
							this.$el.html(template(templateData));
						}
					},
					events : {
						'submit .edit-user-form' : 'saveOffer',
						'click .delete' : 'deleteOffer'
					},
					saveOffer : function(e) {
						e.preventDefault();

						var offer = this.offer ? this.offer : new Offer();

						this.$el.find('input[name]').each(function() {
							offer.set(this.name, this.value);
						});

						offer.save(null, {
							success : function(offer) {
								alert("Offer saved. Id: " + offer.get('id'));
								router.navigate('', {
									trigger : true
								})
							},
							error : function(offer, response) {
								alert("Error saving offer : "
										+ response.statusText);
							}
						});
					},
					deleteOffer : function(e) {
						e.preventDefault();

						var offer = this.offer;

						offer.destroy({
							success : function() {
								alert("Offer deleted");
								router.navigate('', {
									trigger : true
								})
							},
							error : function(response) {
								alert("Error deleting offer : "
										+ response.statusText);
							}
						});
					}
				});
		var offerEditView = new OfferEditView();

		var Router = Backbone.Router.extend({
			routes : {
				'' : 'home',
				'new' : 'editOffer',
				'edit/:id' : 'editOffer'
			}
		});
		var router = new Router();
		router.on('route:home', function() {
			offerListView.render();
		});
		router.on('route:editOffer', function(id) {
			offerEditView.render({
				id : id
			});
		});
		Backbone.history.start();
	</script>
	<!-- END: Backbone code. -->
</body>
</html>
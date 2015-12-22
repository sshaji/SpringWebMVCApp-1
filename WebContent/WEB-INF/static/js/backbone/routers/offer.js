(function(app) {
	"use strict";

	app.Routers.Offer = Backbone.Router.extend({
		routes : {
			'' : 'home',
			'new' : 'editOffer',
			'edit/:id' : 'editOffer'
		}
	})
})(App);
(function(app) {
	"use strict";

	app.Collections.Offers = Backbone.Collection.extend({
		url : '/offers',
	})
})(App);
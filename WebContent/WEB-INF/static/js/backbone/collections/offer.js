(function(app) {
	app.Collections.Offers = Backbone.Collection.extend({
		url : '/offers',
	})
})(App);
(function(app) {
	app.Views.OfferListView = Backbone.View.extend({
		el : '.panel-body',
		render : function() {
			var offers = new app.Collections.Offers();
			var that = this;
			offers.fetch({
				beforeSend : app.setAccessToken,
				success : function(offers) {
					var template = _.template(templates.offer_list_template);
					var templateData = {
						offers : offers.models
					};
					that.$el.html(template(templateData));
				}
			})
		}
	})
})(App);
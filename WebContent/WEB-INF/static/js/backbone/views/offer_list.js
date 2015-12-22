(function(app) {
	"use strict";

	app.Views.OfferListView = Backbone.View.extend({
		el : '.panel-body',
		render : function() {
			var offers = new app.Collections.Offers();
			var that = this;
			offers.fetch({
				beforeSend : app.setAccessToken,
				success : function(offers) {
					var template = _.template(Templates.offer_list_template);
					var templateData = {
						offers : offers.models
					};
					that.$el.html(template(templateData));
				}
			})
		},
		events : {
			'click .search-btn' : 'searchOffers'
		},
		searchOffers : function(searchString) {
			var offers = new app.Collections.Offers();
			var that = this;
			offers.fetch({
				data : {
					'search' : $('.search-fld').val()
				},
				beforeSend : app.setAccessToken,
				success : function(offers) {
					var template = _.template(Templates.offer_list_template);
					var templateData = {
						offers : offers.models
					};
					that.$el.html(template(templateData));
				}
			})
		}
	})
})(App);
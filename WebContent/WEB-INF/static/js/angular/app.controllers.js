(function() {
	"use strict";

	app.controller('OfferListController', function(OfferFactory) {
		var that = this;
		var promise = OfferFactory.getOffers();
		promise.then(function(response) {
			that.offers = response.data;
		}, function(response) {
			Utils.showStatus("Error retrieving offers : " + response.statusText, false);
		});
		this.searchOffers = function(searchString) {
			var promise = OfferFactory.getOffers(searchString);
			promise.then(function(response) {
				that.offers = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offers : " + response.statusText, false);
			});
		};
	});

	app.controller('OfferEditController', function(OfferFactory, $routeParams, $location) {
		var id = $routeParams.id;
		if (id) {
			var that = this;
			var promise = OfferFactory.getOffer(id);
			promise.then(function(response) {
				that.offer = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offer : " + response.statusText, false);
			});
		}

		this.submitOffer = function() {
			var promise = OfferFactory.saveOffer(this.offer);
			promise.then(function(response) {
				Utils.showStatus("Offer updated! : " + response.data.id, true);
				$location.path('/');
			}, function(response) {
				Utils.showStatus("Error! updating offer : " + response.statusText, false);
			});
		};

		this.deleteOffer = function() {
			var promise = OfferFactory.deleteOffer(id);
			promise.then(function(response) {
				Utils.showStatus("Offer deleted!", true);
				$location.path('/');
			}, function(response) {
				Utils.showStatus("Error! deleting offer : " + response.statusText, false);
			});
		};
	});

})();
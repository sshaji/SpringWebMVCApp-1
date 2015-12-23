(function() {
	"use strict";

	app.controller('offerListController', function(offerFactory) {
		var that = this;
		var promise = offerFactory.getOffers();
		promise.then(function(response) {
			that.offers = response.data;
		}, function(response) {
			Utils.showStatus("Error retrieving offers : " + response.statusText, false);
		});
		this.searchOffers = function(searchString) {
			var promise = offerFactory.getOffers(searchString);
			promise.then(function(response) {
				that.offers = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offers : " + response.statusText, false);
			});
		};
	});

	app.controller('offerEditController', function(offerFactory, $routeParams, $location) {
		var id = $routeParams.id;
		if (id) {
			var that = this;
			var promise = offerFactory.getOffer(id);
			promise.then(function(response) {
				that.offer = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offer : " + response.statusText, false);
			});
		}

		this.submitOffer = function() {
			var promise = offerFactory.saveOffer(this.offer);
			promise.then(function(response) {
				Utils.showStatus("Offer updated! : " + response.data.id, true);
				$location.path('/');
			}, function(response) {
				Utils.showStatus("Error! updating offer : " + response.statusText, false);
			});
		};

		this.deleteOffer = function() {
			var promise = offerFactory.deleteOffer(id);
			promise.then(function(response) {
				Utils.showStatus("Offer deleted!", true);
				$location.path('/');
			}, function(response) {
				Utils.showStatus("Error! deleting offer : " + response.statusText, false);
			});
		};
	});

})();
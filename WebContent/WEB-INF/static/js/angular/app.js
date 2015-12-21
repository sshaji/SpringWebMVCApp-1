(function() {

	var app = angular.module('offerList', [ 'ngRoute' ]);

	buildUrl = function(url) {
		return 'rest/v1/' + url;
	}

	app.controller('OfferListController', function($http) {
		this.offers = [];
		var that = this;
		$http.get(buildUrl('/offers')).success(function(data) {
			that.offers = data;
		});
	});

	app.controller('OfferEditController', function($routeParams, $location,
			$http) {
		this.offer = {};
		var id = $routeParams.id;
		if (id) {
			var that = this;
			$http.get(buildUrl('/offers/' + id)).success(function(data) {
				that.offer = data;
			});
		}

		this.submitOffer = function() {
			if (id) {
				$http.put(buildUrl('/offers/' + id), this.offer).success(
						function(data) {
							Utils.showStatus("Offer created! : " + data.id, true);
							$location.path("/");
						}).error(function(data, status) {
							Utils.showStatus("Error! creating offer : " + status, false);
				})
			} else {
				$http.post(buildUrl('/offers'), this.offer).success(
						function(data) {
							Utils.showStatus("Offer updated! : " + data.id, true);
							$location.path("/");
						}).error(function(data, status) {
							Utils.showStatus("Error! updating offer : " + status, false);
				})
			}
		};

		this.deleteOffer = function() {
			$http.delete(buildUrl('/offers/' + id)).success(function(data) {
				Utils.showStatus("Offer deleted!", true);
				$location.path("/");
			}).error(function(data, status) {
				Utils.showStatus("Error deleting! offer : " + status, false);
			})
		};
	});

	app.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'static/js/angular/templates/offerlist.html',
			controller : 'OfferListController'
		}).when('/new', {
			templateUrl : 'static/js/angular/templates/offeredit.html',
			controller : 'OfferEditController'
		}).when('/edit/:id', {
			templateUrl : 'static/js/angular/templates/offeredit.html',
			controller : 'OfferEditController'
		});

	});

})();
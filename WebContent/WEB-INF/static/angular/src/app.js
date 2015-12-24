(function() {
	"use strict";

	angular.module('offersApp', [ 'ngRoute' ])

	.controller('offerListController', function(offerFactory, $scope) {
		this.searchOffers = function(searchString) {
			offerFactory.getOffers(searchString).then(function(response) {
				$scope.offers = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offers : " + response.statusText, false);
				$scope.offers = [];
			});
		};
		this.searchOffers();
	})

	.controller('offerEditController', function(offerFactory, $scope, $routeParams, $location) {
		this.getOffer = function(id) {
			var promise = offerFactory.getOffer(id);
			promise.then(function(response) {
				$scope.offer = response.data;
			}, function(response) {
				Utils.showStatus("Error retrieving offer : " + response.statusText, false);
				$scope.offer = {};
			});
		};
		this.submitOffer = function() {
			var promise = offerFactory.saveOffer($scope.offer);
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
		var id = $routeParams.id;
		if (id) {
			this.getOffer(id);
		}
	})

	.factory('offerFactory', function(httpRequest) {
		return {
			getOffers : function(searchString) {
				return httpRequest.send({
					method : 'GET',
					url : '/offers' + (angular.isDefined(searchString) ? '?search=' + searchString : '')
				});
			},
			getOffer : function(id) {
				return httpRequest.send({
					method : 'GET',
					url : '/offers/' + id
				});
			},
			saveOffer : function(offer) {
				if (angular.isDefined(offer.id)) {
					return httpRequest.send({
						method : 'PUT',
						url : '/offers/' + offer.id,
						data : offer
					});
				} else {
					return httpRequest.send({
						method : 'POST',
						url : '/offers',
						data : offer
					});
				}
			},
			deleteOffer : function(id) {
				return httpRequest.send({
					method : 'DELETE',
					url : '/offers/' + id
				});
			}
		}
	})

	.factory('httpRequest', function(authenticationFactory, $q, $http) {
		var buildUrl = function(url) {
			return 'rest/v1' + url;
		}

		var access_token = null;
		var retryWithNewToken = function(config, deferred) {
			access_token = authenticationFactory.refreshToken();
			access_token.then(function(token) {
				config.headers.access_token = token;
				$http(config).then(function successCallback(response) {
					deferred.resolve(response);
				}, function errorCallback(response) {
					deferred.reject(response);
				});
			});
		}
		return {
			send : function(options) {
				var deferred = $q.defer();
				if (!access_token) {
					access_token = authenticationFactory.getToken();
				}
				access_token.then(function(token) {
					$http({
						method : options.method,
						url : buildUrl(options.url),
						headers : {
							'access_token' : token
						},
						data : options.data || {}
					}).then(function successCallback(response) {
						deferred.resolve(response);
					}, function errorCallback(response) {
						if (response.status == 401) {
							retryWithNewToken(response.config, deferred);
						} else {
							deferred.reject(response);
						}
					});
				});
				return deferred.promise;
			}
		};
	})

	.factory('authenticationFactory', function($q, $http) {
		var saveToken = function(access_token) {
			window.localStorage.clear();
			window.localStorage.setItem('access_token', access_token);
			return true;
		};
		var buildUrl = function(url) {
			return 'rest/v1' + url;
		}
		return {
			isExpired : function() {
				return (window.localStorage.getItem('access_token') == '');
			},
			getToken : function() {
				var deferred = $q.defer();
				if (!this.isExpired()) {
					deferred.resolve(window.localStorage.getItem('access_token'));
				} else {
					deferred.resolve(this.refreshToken());
				}
				return deferred.promise;
			},
			refreshToken : function() {
				var deferred = $q.defer();
				this.sendTokenRequest().then(function(response) {
					saveToken(response.data.access_token);
					deferred.resolve(response.data.access_token);
				}, function errorCallback(response) {
					deferred.reject();
				});
				return deferred.promise;
			},
			sendTokenRequest : function() {
				var deferred = $q.defer();
				$http({
					method : 'GET',
					url : buildUrl('/login')
				}).then(function successCallback(response) {
					deferred.resolve(response);
				}, function errorCallback(response) {
					deferred.reject();
				});
				return deferred.promise;
			}
		}
	})

	.filter('strLimit', function($filter) {
		return function(input, limit) {
			if (!input) {
				return;
			}
			if (input.length <= limit) {
				return input;
			}
			return $filter('limitTo')(input, limit) + '...';
		};
	})

	.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'static/angular/templates/offerlist.html'
		}).when('/new', {
			templateUrl : 'static/angular/templates/offeredit.html'
		}).when('/edit/:id', {
			templateUrl : 'static/angular/templates/offeredit.html'
		});
	})

})();
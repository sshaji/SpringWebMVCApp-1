(function() {
	"use strict";

	angular.module('offersApp', [ 'ngRoute' ])

	.controller('offerListController', function(offerFactory, userFactory, messageHandler, $scope) {
		$scope.searchOffers = function(searchString) {
			offerFactory.getOffers(searchString).then(function(response) {
				$scope.offers = response.data;
			}, function(response) {
				messageHandler.showStatus("Error retrieving offers : " + response.statusText + " - " + response.data.error, false);
				$scope.offers = [];
			});
		};
		$scope.setUser = function() {
			var promise = userFactory.me();
			promise.then(function(response) {
				$scope.user = response.data;
			}, function(response) {
				messageHandler.showStatus("Error retrieving user info : " + response.statusText + " - " + response.data.error, false);
				$scope.user = {};
			});
		};
		$scope.setUser();
		$scope.searchOffers();
	})

	.controller('offerEditController', function(offerFactory, userFactory, messageHandler, $scope, $routeParams, $location) {
		$scope.getOffer = function(id) {
			var promise = offerFactory.getOffer(id);
			promise.then(function(response) {
				$scope.offer = response.data;
			}, function(response) {
				messageHandler.showStatus("Error retrieving offer : " + response.statusText + " - " + response.data.error, false);
				$scope.offer = {};
			});
		};
		$scope.submitOffer = function() {
			var promise = offerFactory.saveOffer($scope.offer);
			promise.then(function(response) {
				messageHandler.showStatus("Offer updated! : " + response.data.id, true);
				$location.path('/');
			}, function(response) {
				messageHandler.showStatus("Error! updating offer : " + response.statusText + " - " + response.data.error, false);
			});
		};
		$scope.deleteOffer = function() {
			var promise = offerFactory.deleteOffer(id);
			promise.then(function(response) {
				messageHandler.showStatus("Offer deleted!", true);
				$location.path('/');
			}, function(response) {
				messageHandler.showStatus("Error! deleting offer : " + response.statusText + " - " + response.data.error, false);
			});
		};
		$scope.setUser = function() {
			var promise = userFactory.me();
			promise.then(function(response) {
				$scope.offer.user = response.data;
			}, function(response) {
				messageHandler.showStatus("Error retrieving user info : " + response.statusText + " - " + response.data.error, false);
				$scope.offer.user = {};
			});
		};
		var id = $routeParams.id;
		if (id) {
			$scope.getOffer(id);
		} else {
			$scope.offer = {};
			$scope.setUser();
		}
	})

	.factory('offerFactory', function(httpRequest) {
		return {
			getOffers : function(searchString) {
				var config = {
					method : 'GET',
					url : '/offers' + (angular.isDefined(searchString) ? '?search=' + searchString : '')
				}
				return httpRequest.send(config);
			},
			getOffer : function(id) {
				var config = {
					method : 'GET',
					url : '/offers/' + id
				}
				return httpRequest.send(config);
			},
			saveOffer : function(offer) {
				var config = {};
				if (angular.isDefined(offer.id)) {
					config = {
						method : 'PUT',
						url : '/offers/' + offer.id,
						data : offer
					}
				} else {
					config = {
						method : 'POST',
						url : '/offers',
						data : offer
					}
				}
				return httpRequest.send(config);
			},
			deleteOffer : function(id) {
				var config = {
					method : 'DELETE',
					url : '/offers/' + id
				}
				return httpRequest.send(config);
			}
		}
	})

	.factory('userFactory', function(httpRequest) {
		return {
			me : function() {
				var config = {
					method : 'GET',
					url : '/me'
				}
				return httpRequest.send(config);
			}
		}
	})

	.factory('httpRequest', function(authenticationFactory, urlBuilder, $q, $http) {
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
						url : urlBuilder.buildRestEndPoint(options.url),
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

	.factory('authenticationFactory', function(tokenFactory, urlBuilder, $q, $http) {
		var sendTokenRequest = function() {
			var deferred = $q.defer();
			$http({
				method : 'GET',
				url : urlBuilder.buildRestEndPoint('/login')
			}).then(function successCallback(response) {
				deferred.resolve(response);
			}, function errorCallback(response) {
				deferred.reject();
			});
			return deferred.promise;
		};
		return {
			getToken : function() {
				var deferred = $q.defer();
				if (!tokenFactory.isExpired()) {
					deferred.resolve(tokenFactory.getToken());
				} else {
					deferred.resolve(this.refreshToken());
				}
				return deferred.promise;
			},
			refreshToken : function() {
				var deferred = $q.defer();
				sendTokenRequest().then(function(response) {
					tokenFactory.saveToken(response.data.access_token);
					deferred.resolve(response.data.access_token);
				}, function errorCallback(response) {
					deferred.reject();
				});
				return deferred.promise;
			}
		}
	})

	.factory('tokenFactory', function() {
		return {
			isExpired : function() {
				return (this.getToken() == '');
			},
			getToken : function() {
				return window.localStorage.getItem('access_token');
			},
			saveToken : function(access_token) {
				window.localStorage.setItem('access_token', access_token);
			}
		}
	})

	.factory('urlBuilder', function($q, $http) {
		return {
			buildRestEndPoint : function(url) {
				return 'rest/v1' + url;
			}
		}
	})

	.factory('messageHandler', function($q, $http) {
		return {
			showStatus : function(message, isSuccess) {
				$("<div />", {
					class : isSuccess ? 'alert alert-success' : 'alert alert-danger',
					text : message
				}).hide().appendTo("body").fadeIn('slow').delay(2000).fadeOut('slow', function() {
					$(this).remove();
				});
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
(function() {
	"use strict";
	
	var app = angular.module('offersApp', [ 'ngRoute' ]);

	var buildUrl = function(url) {
		return 'rest/v1' + url;
	}

	var access_token = null;

	app.controller('OfferListController', function($injector) {
		var that = this;
		var promise = $injector.get('OfferFactory').getOffers();
		promise.then(function(data) {
			that.offers = data;
		});
		this.searchOffers = function(searchString) {
			var promise = $injector.get('OfferFactory').getOffers(searchString);
			promise.then(function(data) {
				that.offers = data;
			});
		};
	});

	app.controller('OfferEditController', function($injector, $routeParams, $location) {
		var id = $routeParams.id;
		if (id) {
			var that = this;
			var promise = $injector.get('OfferFactory').getOffer(id);
			promise.then(function(data) {
				that.offer = data;
			});
		}

		this.submitOffer = function() {
			var promise = $injector.get('OfferFactory').saveOffer(this.offer);
			promise.then(function(data) {
				Utils.showStatus("Offer updated! : " + data.id, true);
				$location.path('/');
			}, function(error) {
				Utils.showStatus("Error! updating offer : " + error, false);
			});
		};

		this.deleteOffer = function() {
			var promise = $injector.get('OfferFactory').deleteOffer(id);
			promise.then(function(data) {
				Utils.showStatus("Offer deleted!", true);
				$location.path('/');
			}, function(error) {
				Utils.showStatus("Error! deleting offer : " + error, false);
			});
		};
	});

	app.config(function($routeProvider, $httpProvider) {
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

	app.factory('OfferFactory', function($injector, $q, $http) {
		return {
			getOffers : function(searchString) {
				var deferred = $q.defer();
				if (!access_token) {
					access_token = $injector.get('AuthenticationFactory').getToken();
				}
				access_token.then(function(token) {
					$http.defaults.headers.common.access_token = token;
					$http.get(buildUrl('/offers' + (typeof (searchString) == 'undefined' ? '' : '?search=' + searchString))).success(function(data) {
						deferred.resolve(data);
					}).error(function(data, status) {
						if (status == 401) {
							alert(status);
						} else {
							deferred.reject(status);
						}
					});
				});
				return deferred.promise;
			},
			getOffer : function(id) {
				var deferred = $q.defer();
				if (!access_token) {
					access_token = $injector.get('AuthenticationFactory').getToken();
				}
				access_token.then(function(token) {
					$http.defaults.headers.common.access_token = token;
					$http.get(buildUrl('/offers/' + id)).success(function(data) {
						deferred.resolve(data);
					}).error(function(data, status) {
						if (status == 401) {
							alert(status);
						} else {
							deferred.reject(status);
						}
					});
				});
				return deferred.promise;
			},
			saveOffer : function(offer) {
				var deferred = $q.defer();
				if (!access_token) {
					access_token = $injector.get('AuthenticationFactory').getToken();
				}
				access_token.then(function(token) {
					if (typeof(offer.id) != 'undefined') {
						$http.defaults.headers.common.access_token = token;
						$http.put(buildUrl('/offers/' + offer.id), offer).success(function(data) {
							deferred.resolve(data);
						}).error(function(data, status) {
							if (status == 401) {
								alert(status);
							} else {
								deferred.reject(status);
							}
						})
					} else {
						$http.defaults.headers.common.access_token = token;
						$http.post(buildUrl('/offers'), offer).success(function(data) {
							deferred.resolve(data);
						}).error(function(data, status) {
							if (status == 401) {
								alert(status);
							} else {
								deferred.reject(status);
							}
						})
					}
				});
				return deferred.promise;
			},
			deleteOffer : function(id) {
				var deferred = $q.defer();
				if (!access_token) {
					access_token = $injector.get('AuthenticationFactory').getToken();
				}
				access_token.then(function(token) {
					$http.defaults.headers.common.access_token = token;
					$http.delete(buildUrl('/offers/' + id)).success(function(data) {
						deferred.resolve(data);
					}).error(function(data, status) {
						if (status == 401) {
							alert(status);
						} else {
							deferred.reject(status);
						}
					})
				});
				return deferred.promise;
			}
		}
	});

	app.factory('AuthenticationFactory', function($q, $http) {
		var cacheSession = function(access_token) {
			window.localStorage.clear();
			window.localStorage.setItem('access_token', access_token);
			return true;
		};

		return {
			isExpired : function() {
				return (window.localStorage.getItem('access_token') == '');
			},
			getToken : function() {
				var deferred = $q.defer();
				if (!this.isExpired()) {
					deferred.resolve(window.localStorage.getItem('access_token'));
				} else {
					this.refreshToken().then(function(data) {
						cacheSession(data.data.access_token);
						deferred.resolve(window.localStorage.getItem('access_token'));
					});
				}
				return deferred.promise;
			},
			refreshToken : function() {
				return $http.get(buildUrl('/login'));
			}
		}
	});

})();
(function() {
	"use strict";

	var buildUrl = function(url) {
		return 'rest/v1' + url;
	}

	var access_token = null;

	app.factory('offerFactory', function(httpRequest) {
		return {
			getOffers : function(searchString) {
				return httpRequest.send({
					method : 'GET',
					url : buildUrl('/offers' + (angular.isDefined(searchString) ? '?search=' + searchString : ''))
				});
			},
			getOffer : function(id) {
				return httpRequest.send({
					method : 'GET',
					url : buildUrl('/offers/' + id)
				});
			},
			saveOffer : function(offer) {
				if (angular.isDefined(offer.id)) {
					return httpRequest.send({
						method : 'PUT',
						url : buildUrl('/offers/' + offer.id),
						data : offer
					});
				} else {
					return httpRequest.send({
						method : 'POST',
						url : buildUrl('/offers'),
						data : offer
					});
				}
			},
			deleteOffer : function(id) {
				return httpRequest.send({
					method : 'DELETE',
					url : buildUrl('/offers/' + id)
				});
			}
		}
	});

	app.factory('httpRequest', function(authenticationFactory, $q, $http) {
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
					$http.defaults.headers.common.access_token = token;
					$http({
						method : options.method,
						url : options.url,
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
	});

	app.factory('authenticationFactory', function($q, $http) {
		var saveToken = function(access_token) {
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
	});

})();
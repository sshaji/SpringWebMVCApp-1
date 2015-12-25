(function() {

	var mockedOfferFactory = function($q) {
		return {
			getOffers : jasmine.createSpy('getOffers').and.callFake(function(any) {
				var deferred = $q.defer();
				deferred.resolve({
					data : [ 'test1', 'test2' ]
				});
				return deferred.promise;
			}),
			getOffer : jasmine.createSpy('getOffer').and.callFake(function(any) {
				var deferred = $q.defer();
				deferred.resolve({
					data : 'test'
				});
				return deferred.promise;
			}),
			saveOffer : jasmine.createSpy('getOffer').and.callFake(function(any) {
				var deferred = $q.defer();
				deferred.resolve({
					data : 'test'
				});
				return deferred.promise;
			}),
			deleteOffer : jasmine.createSpy('getOffer').and.callFake(function(any) {
				var deferred = $q.defer();
				deferred.resolve({});
				return deferred.promise;
			})
		};
	};

	var mockedMessageHandler = function() {
		return {
			showStatus : jasmine.createSpy('showStatus')
		};
	};

	describe("testing offerListController with offers exist in server", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('offerFactory', mockedOfferFactory);
			$provide.factory('messageHandler', mockedMessageHandler);
		}));
		beforeEach(inject(function(_$controller_, _$rootScope_, _offerFactory_, _messageHandler_) {
			$controller = _$controller_;
			$rootScope = _$rootScope_;
			offerFactory = _offerFactory_;
			messageHandler = _messageHandler_;
		}));
		describe('offerListController', function() {
			beforeEach(inject(function($controller, $rootScope) {
				$scope = $rootScope.$new();
				$scope.offers = [];
				$controller('offerListController', {
					offerFactory : offerFactory,
					messageHandler : messageHandler,
					$scope : $scope
				});
			}));
			it('should call getOffers function on load', function() {
				expect(offerFactory.getOffers).toHaveBeenCalled();
			});
			it('should get some offers on load', function() {
				$scope.$apply();
				expect($scope.offers[0]).toEqual('test1');
			});
			it('search function should get some offers', function() {
				$scope.searchOffers('test');
				$scope.$apply();
				expect($scope.offers[1]).toEqual('test2');
			});
		});
	});

	describe("testing offerEditController with Server connection is Ok", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('offerFactory', mockedOfferFactory);
			$provide.factory('messageHandler', mockedMessageHandler);
		}));
		beforeEach(inject(function(_$controller_, _$rootScope_, _$location_, _offerFactory_, _messageHandler_) {
			$controller = _$controller_;
			$rootScope = _$rootScope_;
			$location = _$location_;
			offerFactory = _offerFactory_;
			messageHandler = _messageHandler_;
		}));
		describe('offerListController', function() {
			beforeEach(inject(function($controller, $rootScope) {
				$scope = $rootScope.$new();
				$scope.offers = [];
				$controller('offerEditController', {
					offerFactory : offerFactory,
					messageHandler : messageHandler,
					$scope : $scope,
					$routeParams : {
						id : 1
					},
					$location : $location
				});
			}));
			it('should call getOffer function on load', function() {
				expect(offerFactory.getOffer).toHaveBeenCalled();
			});
			it('should get an offer on load', function() {
				$scope.$apply();
				expect($scope.offer).toEqual('test');
			});
			it('should save an offer and set location to /', function() {
				$scope.submitOffer();
				expect($location.path()).toEqual('/');
			});
			it('should delete an offer and set location to /', function() {
				$scope.deleteOffer();
				expect($location.path()).toEqual('/');
			});
		});
	});

})();
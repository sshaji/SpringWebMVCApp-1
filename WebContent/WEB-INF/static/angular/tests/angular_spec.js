(function() {

	describe("testing offerListController with Server connection is Ok", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('offerFactory', function($q) {
				return {
					getOffers : jasmine.createSpy('getOffers').and.callFake(function(any) {
						var deferred = $q.defer();
						deferred.resolve({
							data : [ 'test' ]
						});
						return deferred.promise;
					})
				};
			});
			$provide.factory('messageHandler', function() {
				return {
					showStatus : jasmine.createSpy('showStatus')
				};
			});
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
				controller = $controller('offerListController', {
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
				expect($scope.offers).toEqual([ 'test' ]);
			});
			it('search function should get some offers', function() {
				controller.searchOffers('test');
				$scope.$apply();
				expect($scope.offers).toEqual([ 'test' ]);
			});
		});
	});

	describe("testing offerListController with Server connection failure", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('offerFactory', function($q) {
				return {
					getOffers : jasmine.createSpy('getOffers').and.callFake(function(any) {
						var deferred = $q.defer();
						deferred.reject({
							statusText : 'error'
						});
						return deferred.promise;
					})
				};
			});
			$provide.factory('messageHandler', function() {
				return {
					showStatus : jasmine.createSpy('showStatus')
				};
			});
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
				controller = $controller('offerListController', {
					offerFactory : offerFactory,
					messageHandler : messageHandler,
					$scope : $scope
				});
			}));
			it('should call getOffers function on load', function() {
				expect(offerFactory.getOffers).toHaveBeenCalled();
			});
			it('should not load any offers on load', function() {
				$scope.$apply();
				expect($scope.offers).toEqual([]);
			});
			it('search function should not get any offers', function() {
				controller.searchOffers('test');
				$scope.$apply();
				expect($scope.offers).toEqual([]);
			});
		});
	});

})();
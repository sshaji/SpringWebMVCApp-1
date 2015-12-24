(function() {

	describe("offerListController", function() {
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
		}));
		beforeEach(inject(function(_$controller_, _offerFactory_, _$rootScope_) {
			$controller = _$controller_;
			$rootScope = _$rootScope_;
			offerFactory = _offerFactory_;
		}));
		describe('testing offerListController', function() {
			beforeEach(inject(function($controller, $rootScope) {
				$scope = $rootScope.$new();
				$scope.offers = [];
				controller = $controller('offerListController', {
					offerFactory : offerFactory,
					$scope : $scope
				});
			}));
			it('should call getOffers function on load', function() {
				expect(offerFactory.getOffers).toHaveBeenCalled();
			});
			it('should get offers on load', function() {
				expect(offerFactory.getOffers).toHaveBeenCalled();
				$scope.$apply();
				expect($scope.offers).toEqual([ 'test' ]);
			});
			it('should search function get offers', function() {
				controller.searchOffers('test');
				$scope.$apply();
				expect($scope.offers).toEqual([ 'test' ]);
			});
		});
	});

})();
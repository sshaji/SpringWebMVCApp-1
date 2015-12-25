(function() {

	var mockedHttpRequest = function($q) {
		return {
			send : jasmine.createSpy('send').and.callFake(function(options) {
				var deferred = $q.defer();
				deferred.resolve(options);
				return deferred.promise;
			})
		};
	};

	describe("testing offerFactory", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('httpRequest', mockedHttpRequest);
		}));
		beforeEach(inject(function(_$controller_, _httpRequest_) {
			$controller = _$controller_;
			httpRequest = _httpRequest_;
		}));
		describe('offerFactory', function() {
			beforeEach(inject(function(offerFactory) {
				factory = offerFactory;
			}));
			it('should xxxx', function() {
				expect(factory.getOffers().$$state.value).toEqual({
					method : 'GET',
					url : '/offers'
				});
			});
		});
	});

})();
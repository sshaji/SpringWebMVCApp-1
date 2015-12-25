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
		describe('offerFactory', function() {
			beforeEach(inject(function(offerFactory) {
				factory = offerFactory;
			}));
			it('should send a get request to /offers to get offers', function() {
				expect(factory.getOffers().$$state.value).toEqual({
					method : 'GET',
					url : '/offers'
				});
			});
			it('should send a get request to /offers with search param to search offers', function() {
				expect(factory.getOffers('test').$$state.value).toEqual({
					method : 'GET',
					url : '/offers?search=test'
				});
			});
			it('should send a get request to /offers/:id to get an offer', function() {
				expect(factory.getOffer('123').$$state.value).toEqual({
					method : 'GET',
					url : '/offers/123'
				});
			});
			it('should send a post request to /offers to create an offer', function() {
				expect(factory.saveOffer({
					'testField' : 'testData'
				}).$$state.value).toEqual({
					method : 'POST',
					url : '/offers',
					data : {
						'testField' : 'testData'
					}
				});
			});
			it('should send a put request to /offers/:id to update an offer', function() {
				expect(factory.saveOffer({
					'id' : '123',
					'testField' : 'testData'
				}).$$state.value).toEqual({
					method : 'PUT',
					url : '/offers/123',
					data : {
						'id' : '123',
						'testField' : 'testData'
					}
				});
			});
			it('should send a delete request to /offers/:id to delete an offer', function() {
				expect(factory.deleteOffer('123').$$state.value).toEqual({
					method : 'DELETE',
					url : '/offers/123'
				});
			});
		});
	});

	var mockedAuthenticationFactory = function($q) {
		return {
			getToken : jasmine.createSpy('getToken').and.callFake(function() {
				var deferred = $q.defer();
				deferred.resolve('token123');
				return deferred.promise;
			}),
			refreshToken : jasmine.createSpy('refreshToken').and.callFake(function() {
				var deferred = $q.defer();
				deferred.resolve('token321');
				return deferred.promise;
			})
		};
	};

	describe("testing httpRequest", function() {
		beforeEach(module('offersApp', function($provide) {
			$provide.factory('authenticationFactory', mockedAuthenticationFactory);
		}));
		beforeEach(inject(function(_authenticationFactory_) {
			httpRequest = _authenticationFactory_;
		}));
		describe('offerFactory', function() {
			beforeEach(inject(function(httpRequest) {
				factory = httpRequest;
			}));
			it('should xxxxx', function() {
				
			});
		});
	});

})();
(function() {
	"use strict";

	app.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'static/js/angular/templates/offerlist.html',
			controller : 'offerListController'
		}).when('/new', {
			templateUrl : 'static/js/angular/templates/offeredit.html',
			controller : 'offerEditController'
		}).when('/edit/:id', {
			templateUrl : 'static/js/angular/templates/offeredit.html',
			controller : 'offerEditController'
		});
	});

})();
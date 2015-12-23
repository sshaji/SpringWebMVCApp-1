(function() {
	"use strict";

	app.config(function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'static/js/angular/templates/offerlist.html'
		}).when('/new', {
			templateUrl : 'static/js/angular/templates/offeredit.html'
		}).when('/edit/:id', {
			templateUrl : 'static/js/angular/templates/offeredit.html'
		});
	});

})();
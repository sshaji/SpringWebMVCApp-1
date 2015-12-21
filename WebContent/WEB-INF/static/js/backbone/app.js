(function() {
	window.App = {

		Models : {},
		Collections : {},
		Views : {},
		Routers : {},

		init : function() {
			_.templateSettings = {
				variable : "rc",
				interpolate : /\<\@\=(.+?)\@\>/gim,
				evaluate : /\<\@(.+?)\@\>/gim,
				escape : /\<\@\-(.+?)\@\>/gim
			};

			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				options.url = 'rest/v1' + options.url;
			});
		}

	};
})();
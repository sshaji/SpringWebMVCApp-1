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
		},

		showStatus : function(message, isSuccess) {
			$(
					"<div />",
					{
						class : isSuccess ? 'alert alert-success'
								: 'alert alert-danger',
						text : message
					}).hide().appendTo("body").fadeIn('slow').delay(2000)
					.fadeOut('slow', function() {
						$(this).remove();
					});
		}

	};
})();
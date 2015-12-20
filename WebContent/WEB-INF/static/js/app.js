(function() {
	window.App = {

		Models : {},
		Collections : {},
		Views : {},
		Routers : {},

		/**
		 * Initialize page
		 */
		init : function() {
			_.templateSettings.variable = "rc";

			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				options.url = 'http://localhost:8080/SpringWebMVCApp-1/rest/v1'
						+ options.url;
			});
		},

		/**
		 * Display message bar at top of the page Will close automatically
		 */
		showStatus : function(message, isSuccess) {
			$(
					"<div />",
					{
						'class' : isSuccess ? 'alert alert-success'
								: 'alert alert-danger',
						'role' : 'alert',
						text : message
					}).hide().prependTo("body").slideDown('fast').delay(4000)
					.slideUp(function() {
						$(this).remove();
					});
		}
	};
})();
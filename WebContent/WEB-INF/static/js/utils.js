(function() {
	window.Utils = {

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
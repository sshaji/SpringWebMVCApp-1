(function() {
	app = {

		init : function() {
			_.templateSettings.variable = "rc";

			$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
				options.url = 'http://localhost:8080/SpringWebMVCApp-1/rest/v1'
						+ options.url;
			});
		},

		Offers : Backbone.Collection.extend({
			url : '/offers'
		}),

		Offer : Backbone.Model.extend({
			urlRoot : '/offers',
			defaults : {
				name : '',
				email : '',
				offerDetails : ''
			}
		}),

		OfferListView : Backbone.View.extend({
			el : '.panel-body',
			render : function() {
				var offers = new app.Offers();
				var that = this;
				offers.fetch({
					success : function(offers) {
						var template = _
								.template(templates.offer_list_template);
						var templateData = {
							offers : offers.models
						};
						that.$el.html(template(templateData));
					}
				})
			}
		}),

		OfferEditView : Backbone.View.extend({
			el : '.panel-body',
			render : function(options) {
				if (options.id) {
					this.offer = new app.Offer({
						id : options.id
					});
					var that = this;
					this.offer.fetch({
						success : function(offer) {
							var template = _
									.template(templates.edit_offer_template);
							var templateData = {
								offer : offer
							};
							that.$el.html(template(templateData));
						}
					});
				} else {
					var template = _.template(templates.edit_offer_template);
					var templateData = {
						offer : null
					};
					this.$el.html(template(templateData));
				}
			},
			events : {
				'submit .edit-user-form' : 'saveOffer',
				'click .delete' : 'deleteOffer'
			},
			saveOffer : function(e) {
				e.preventDefault();

				var offer = this.offer ? this.offer : new app.Offer();

				this.$el.find('input[name]').each(function() {
					offer.set(this.name, this.value);
				});

				offer.save(null, {
					success : function(offer) {
						app.showStatus("Offer saved! Id: " + offer.get('id'),
								true);
						router.navigate('', {
							trigger : true
						})
					},
					error : function(offer, response) {
						app.showStatus("Error! saving offer : "
								+ response.statusText, false);
					}
				});
			},
			deleteOffer : function(e) {
				e.preventDefault();

				var offer = this.offer;

				offer.destroy({
					success : function() {
						app.showStatus("Offer deleted!", true);
						router.navigate('', {
							trigger : true
						})
					},
					error : function(response) {
						app.showStatus("Error! deleting offer : "
								+ response.statusText, false);
					}
				});
			}
		}),

		Router : Backbone.Router.extend({
			routes : {
				'' : 'home',
				'new' : 'editOffer',
				'edit/:id' : 'editOffer'
			}
		}),

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
(function(app) {
	app.Views.OfferEditView = Backbone.View.extend({
		el : '.panel-body',
		render : function(options) {
			this.router = options.router;
			if (options.id) {
				this.offer = new app.Models.Offer({
					id : options.id
				});
				var that = this;
				this.offer.fetch({
					beforeSend : app.setAccessToken,
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

			var offer = this.offer ? this.offer : new app.Models.Offer();

			this.$el.find('input[name]').each(function() {
				offer.set(this.name, this.value);
			});

			var that = this;
			offer.save(null, {
				beforeSend : app.setAccessToken,
				success : function(offer) {
					Utils.showStatus("Offer saved! Id: " + offer.get('id'),
							true);
					that.router.navigate('', {
						trigger : true
					})
				},
				error : function(offer, response) {
					Utils.showStatus("Error! saving offer : "
							+ response.statusText, false);
				}
			});
		},
		deleteOffer : function(e) {
			e.preventDefault();

			var offer = this.offer;

			var that = this;
			offer.destroy({
				beforeSend : app.setAccessToken,
				success : function() {
					Utils.showStatus("Offer deleted!", true);
					that.router.navigate('', {
						trigger : true
					})
				},
				error : function(response) {
					Utils.showStatus("Error! deleting offer : "
							+ response.statusText, false);
				}
			});
		}
	})
})(App);
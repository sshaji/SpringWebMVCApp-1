App.Views.OfferEditView = Backbone.View.extend({
	el : '.panel-body',
	render : function(options) {
		if (options.id) {
			this.offer = new App.Models.Offer({
				id : options.id
			});
			var that = this;
			this.offer.fetch({
				success : function(offer) {
					var template = _.template(templates.edit_offer_template);
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

		var offer = this.offer ? this.offer : new App.Models.Offer();

		this.$el.find('input[name]').each(function() {
			offer.set(this.name, this.value);
		});

		offer.save(null, {
			success : function(offer) {
				App.showStatus("Offer saved! Id: " + offer.get('id'), true);
				router.navigate('', {
					trigger : true
				})
			},
			error : function(offer, response) {
				App.showStatus("Error! saving offer : " + response.statusText,
						false);
			}
		});
	},
	deleteOffer : function(e) {
		e.preventDefault();

		var offer = this.offer;

		offer.destroy({
			success : function() {
				App.showStatus("Offer deleted!", true);
				router.navigate('', {
					trigger : true
				})
			},
			error : function(response) {
				App
						.showStatus("Error! deleting offer : "
								+ response.statusText, false);
			}
		});
	}
})
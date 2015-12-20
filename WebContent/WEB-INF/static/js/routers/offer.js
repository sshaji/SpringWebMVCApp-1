App.Routers.Offer = Backbone.Router.extend({
	routes : {
		'' : 'home',
		'new' : 'editOffer',
		'edit/:id' : 'editOffer'
	}
})
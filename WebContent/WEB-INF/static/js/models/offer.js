App.Models.Offer = Backbone.Model.extend({
	urlRoot : '/offers',
	defaults : {
		name : '',
		email : '',
		offerDetails : ''
	}
})
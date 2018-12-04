(function(module) {

	module.service('productEndpoint', [ '$q', '$http', 'api', 'partnerDetails',
		function($q, $http, api, partnerDetails) {
		
			this.searchProducts = function(params) {
				var listBookingURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/product';
				return $http.get(listBookingURL, {
					params : params
				});
			};
		}
	]);

})(angular.module('aet.endpoints'));

(function(module) {
    
    module.service('featureEndpoint', ['$q', '$http', 'api', 'partnerDetails', '$log',
        function($q, $http, api, partnerDetails, $log) {

			this.searchFeatures = function (params) {
				$log.debug("feature - Endpoint - searchFeatures", params);

                var url = api('admin') + /partner/ + partnerDetails.getSelectedPartner().id + '/feature';
                return $http.get(url, {params:params});
            };
            
            this.getFeature = function (id) {
            	$log.debug("feature - Endpoint - getFeature", id);
                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/feature/' + id;
				
                return $http.get(url);
            };
			
        }
    ]);

})(angular.module('aet.endpoints'));
(function(module) {
    
    module.service('featureService', ['genericTransformer', 'Feature', 'featureEndpoint', '$q', 'featureTransformer',
        function(genericTransformer, Feature, featureEndpoint, $q, featureTransformer) {

			this.searchFeatures = function(searchObject) {
            	
                return featureEndpoint.searchFeatures(searchObject).then(function(dto) {
					return featureTransformer.searchDTOToSearchResults(dto.data);
				}, function(err) {
					console.error("Could not search Features");
                    return $q.reject(err);
				});

            };
			
			this.getFeature = function(id) {
            	$log.debug("featureService::getFeature", id);
            	
                return featureEndpoint.getFeature(id).then(function(dto) {
                    return featureTransformer.DTOToFeature(dto.data);
                }, function(err) {
                    console.error("Could not get feature");
                    return $q.reject(err);
                });

            };
			
        }
    ]);

})(angular.module('aet.domain.feature'));


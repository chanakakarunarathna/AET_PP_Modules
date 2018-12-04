(function(module) {
    
    module.service('featureTransformer', ['genericTransformer', 'Feature', '$log',
        function(genericTransformer, Feature, $log) {

			this.getDTOToFeature = function(dto) {
				
				var feature = genericTransformer.DTOToObject(dto, Feature);
				
				return feature;
			};
			
			this.searchDTOToSearchResults = function(dto) {
				var searchResults = genericTransformer.DTOToSearchResults(dto);
				searchResults.results = dto.features;
				return searchResults;
			};
           
        }
]);

})(angular.module('aet.domain.field'));


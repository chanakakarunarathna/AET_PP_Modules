(function(module) {
    
    module.service('masterfieldTransformer', ['genericTransformer', 'CreateMasterfieldRequestDTO', 'UpdateMasterfieldRequestDTO', 'MasterField',
        function(genericTransformer, CreateMasterfieldRequestDTO, UpdateMasterfieldRequestDTO, MasterField) {

            this.masterfieldToUpdateDTO = function(masterfield) {
                return genericTransformer.objectToDTO(masterfield, UpdateMasterfieldRequestDTO);
            };
			
			this.masterfieldToCreateDTO = function(masterfield) {
                return genericTransformer.objectToDTO(masterfield, CreateMasterfieldRequestDTO);
            };
			
			this.getDTOToMasterField = function(dto) {
				return genericTransformer.DTOToObject(dto.data, MasterField);
			}
			
			this.searchDTOToSearchResults = function(dto) {
				var searchResults = genericTransformer.DTOToSearchResults(dto.data);
				searchResults.results = dto.data.partners; //'partners' is not the correct name and needs to be changed server side
				
				return searchResults;
			}

        }
]);

})(angular.module('aet.domain.masterfield'));


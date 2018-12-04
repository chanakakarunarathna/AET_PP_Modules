(function(module) {

  module.service('partnerPropertyTransformer', ['genericTransformer', 'CreatePartnerPropertyRequestDTO', 'UpdatePartnerPropertyRequestDTO', 'PartnerProperty',
    function(genericTransformer, CreatePartnerPropertyRequestDTO, UpdatePartnerPropertyRequestDTO, PartnerProperty) {

      this.partnerPropertyToCreateDTO = function(partnerProperty) {
        var partnerPropertyObj=genericTransformer.objectToDTO(partnerProperty, CreatePartnerPropertyRequestDTO);
        return partnerPropertyObj;
      };

      this.getDTOToPartnerProperty = function(dto) {
        var partnerProperty = genericTransformer.DTOToObject(dto, PartnerProperty);
        return partnerProperty;
      };

      this.partnerPropertyToUpdateDTO = function(dto) {
        var partnerPropertyObj= genericTransformer.objectToDTO(dto, UpdatePartnerPropertyRequestDTO);
        return partnerPropertyObj;
      };

      this.searchDTOToSearchResults = function(dto) {
        var self = this;
        var searchResults = genericTransformer.DTOToSearchResultsPage(dto.data);
        searchResults.results = _.map(dto.data.partnerProproperties, function(PartnerProperty) {
          return self.getDTOToPartnerProperty(PartnerProperty);
        });

        return searchResults;
      };
      
      this.partnerPropertyParamConvert = function(params) {
          if(params != undefined  && params.sortby != undefined){
            if(params.sortby == 'propertyCode'){
              params.sortby = 'code'
            }else if(params.sortby == 'propertyName'){
              params.sortby = 'name'
            }else if(params.sortby == 'brand'){
              params.sortby = 'brand'
            }else if(params.sortby == 'subBrand'){
              params.sortby = 'subbrand'
            }
          }
          return params;
        };
    }
  ]);

})(angular.module('aet.domain.partnerProperty'));

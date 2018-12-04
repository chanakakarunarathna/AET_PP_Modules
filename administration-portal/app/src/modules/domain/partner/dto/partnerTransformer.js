(function(module) {

  module.service('partnerTransformer', ['genericTransformer', 'CreatePartnerRequestDTO', 'Partner', 'UpdatePartnerRequestDTO',
    function(genericTransformer, CreatePartnerRequestDTO, Partner, UpdatePartnerRequestDTO) {

      this.partnerToCreateDTO = function(partner) {
        var partnerObj=genericTransformer.objectToDTO(partner, CreatePartnerRequestDTO);
        partnerObj.state=partner.state.key;
        partnerObj.subLevel=partner.subLevel.key;
        return partnerObj;
      };

      this.getDTOToPartner = function(dto) {
        //convert sub level number to string
        dto.subLevel=dto.subLevel.toString();
        var partner = genericTransformer.DTOToObject(dto, Partner);

        //convert createdDate to Date object
        var createdDate = new Date(dto.createdDate);
        partner.createdDate = createdDate.getMonth() + 1 + "/" + createdDate.getDate() + "/" + createdDate.getFullYear();

        return partner;
      };

      this.partnerToUpdateDTO = function(partner) {
        var partnerObj= genericTransformer.objectToDTO(partner, UpdatePartnerRequestDTO);
        partnerObj.state=partner.state.key;
        partnerObj.subLevel=partner.subLevel.key;
        return partnerObj;
      };

      this.searchDTOToSearchResults = function(dto) {
        var self = this;

        var searchResults = genericTransformer.DTOToSearchResults(dto.data);
        searchResults.results = _.map(dto.data.partners, function(partner) {
          return self.getDTOToPartner(partner);
        });

        return searchResults;
      };

    }
  ]);

})(angular.module('aet.domain.partner'));

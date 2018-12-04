(function(module) {

  module.service('partnerConfigTransformer', ['PartnerConfig', 'genericTransformer', 'partnerRoleTransformer', '$log', 'CreatePartnerConfigDTO', 'UpdatePartnerConfigDTO','SearchResults',
    function(PartnerConfig, genericTransformer, partnerRoleTransformer, $log, CreatePartnerConfigDTO, UpdatePartnerConfigDTO, SearchResults) {

      this.DTOToPartnerConfig = function(dto) {
    	var partnerConfig = genericTransformer.DTOToObject(dto, PartnerConfig);
        partnerConfig.featuredProductIds = dto.featuredProductIds;
        partnerConfig.featuredProducts = dto.featuredProducts;
        partnerConfig.destinations = dto.destinations;
        partnerConfig.hero = dto.hero;
        partnerConfig.partnerId = dto.partnerId;
        return partnerConfig;
      };

      this.DTOToPartnerConfigs = function(dto) {
        return _.map(dto, this.DTOToPartnerConfig);
      };

      this.searchDTOToSearchResults = function(dto) {
		var partnerConfigsList = [];
		var pc =  new PartnerConfig();
		pc.id = dto.data.id;
		pc.partnerId = dto.data.partnerId;
		pc.hero = dto.data.hero;
		pc.featuredProductIds = dto.data.featuredProductIds;
		pc.destinations = dto.data.destinations;
		pc.customMD = dto.data.customMD;
		partnerConfigsList.push(pc);
  
		angular.forEach(partnerConfigsList, function(partnerConfig, partnerConfigKey) {
			partnerConfig.partnerId = partnerConfig.partnerId;
		});

		var searchResults = new SearchResults();
		searchResults.pagination.count = "1";
		searchResults.pagination.currentPage = "1";
		searchResults.pagination.total = "1";
		searchResults.pagination.totalPages = "1";

		searchResults.results = partnerConfigsList;
		return searchResults;
      };

      this.loginDTOToPartnerConfig = function(dto) {
        var partnerConfig = genericTransformer.DTOToObject(dto, PartnerConfig);
        partnerConfig.id = dto.userId;
        partnerConfig.email = dto.email;
        return partnerConfig;
      };

      this.partnerConfigToCreateDTO = function(partnerConfig) {
        var dto = genericTransformer.objectToDTO(partnerConfig, CreatePartnerConfigDTO);
        dto.featuredProductIds = partnerConfig.featuredProductIds;
        dto.destinations = partnerConfig.destinations;
        dto.hero = partnerConfig.hero;
        dto.partnerId = partnerConfig.partnerId;
        return dto;
      };

      
      this.partnerConfigToUpdateDTO = function(partnerConfig) {
        var dto = genericTransformer.objectToDTO(partnerConfig, UpdatePartnerConfigDTO);
        dto.featuredProductIds = partnerConfig.featuredProductIds;
        dto.destinations = partnerConfig.destinations;
        dto.hero = partnerConfig.hero;
        dto.partnerId = partnerConfig.partnerId;
        return dto;
      }



    }
  ]);

})(angular.module('aet.domain.partnerConfig'));

(function(module) {

  module.service('partnerConfigService', ['partnerConfigEndpoint', 'partnerConfigTransformer', '$q', '$state', '$log', 'partnerDetails', 'PartnerRole','alertsService',
    function(partnerConfigEndpoint, partnerConfigTransformer, $q, $state, $log, partnerDetails, PartnerRole,alertsService) {

      this.createPartnerConfig = function(partnerConfig) {
        var dto = partnerConfigTransformer.partnerConfigToCreateDTO(partnerConfig);
        return partnerConfigEndpoint.createPartnerConfig(dto).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not create partner config", err);
          return $q.reject(err);
        });

      };

      this.deletePartnerConfig = function(partnerConfigId) {
        return partnerConfigEndpoint.deletePartnerConfig(partnerConfigId).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not delete partner config", err);
          return $q.reject(err);
        });
      };

      this.updatePartnerConfig = function(partnerConfig) {
        var dto = partnerConfigTransformer.partnerConfigToUpdateDTO(partnerConfig);
        return partnerConfigEndpoint.updatePartnerConfig(dto).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not update partner config", err);
          return $q.reject(err);
        });
      };


      this.findPartnerConfig = function(id) {
        return partnerConfigEndpoint.findPartnerConfig(id).then(function(response) {
          return partnerConfigTransformer.DTOToPartnerConfig(response.data);
        }, function(err) {
          //console.error("Could not load partner config");
          return $q.reject(err);
        });
      };

      this.searchPartnerConfig = function(params) {
        return partnerConfigEndpoint.listPartnerConfig(params).then(function(dto) {
          	var partnerConfigSearchResults = partnerConfigTransformer.searchDTOToSearchResults(dto);
          	if(partnerConfigSearchResults.pagination.total>0){
          		$state.go('index.secured.partnerConfig.edit', {});
        	}
        }, function(err) {
          //check is config not found
          if(err.status == 400){
            alertsService.clear(); //clear the error message
          }
          //console.error("Could not search partner config", err);
          $state.go('index.secured.partnerConfig.create', {});
          return $q.reject(err);
        });

      };

      this.getPartnerConfigsBySelectedPartner = function(partner) {
        return partnerConfigEndpoint.listPartnerConfigsBySelectedPartner(partner).then(function(dto) {
          return partnerConfigTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          //console.error("Could not search partner config", err);
          return $q.reject(err);
        });

      };

      this.searchAllPartnerConfig = function(params) {
        return partnerConfigEndpoint.listAllPartnerConfig(params).then(function(dto) {
          return partnerConfigTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          //console.error("Could not search all partner config", err);
          return $q.reject(err);
        });

      };

    }
  ]);

})(angular.module('aet.domain.partnerConfig'));

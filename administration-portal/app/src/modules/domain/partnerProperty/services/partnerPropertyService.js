(function(module) {

  module.service('partnerPropertyService', ['partnerPropertyEndpoint', '$q', '$log', 'partnerPropertyTransformer',
    function(partnerPropertyEndpoint, $q, $log, partnerPropertyTransformer) {

      this.createPartnerProperty = function(partnerProperty) {
        $log.debug("partnerPropertyService::createpartnerProperty", partnerProperty)

        var createDTO = partnerPropertyTransformer.partnerPropertyToCreateDTO(partnerProperty);
        return partnerPropertyEndpoint.createPartnerProperty(createDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not create Partner Property");
          return $q.reject(err);
        });
      };

      this.searchPartnerProperties = function(params) {
        return partnerPropertyEndpoint.searchPartnerProperties(partnerPropertyTransformer.partnerPropertyParamConvert(params)).then(function(dto) {
          return partnerPropertyTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          console.error("Could not search Partner properties");
          return $q.reject(err);
        });
      };

      this.editPartnerProperty = function(partnerProperty) {
        var updateDTO = partnerPropertyTransformer.partnerPropertyToUpdateDTO(partnerProperty)

        return partnerPropertyEndpoint.editPartnerProperty(updateDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not update Partner Property");
          return $q.reject(err);
        });
      };

      this.getPartnerProperty = function(partnerPropertyId) {
        return partnerPropertyEndpoint.getPartnerProperty(partnerPropertyId).then(function(dto) {
          return partnerPropertyTransformer.getDTOToPartnerProperty(dto.data);
        }, function(err) {
          console.error("Could not get Partner Property");
          return $q.reject(err);
        });
      };

      this.deletePartnerProperty = function(partnerPropertyId) {
        $log.debug("You're in the deletepartnerProperty method (partnerPropertyService)");
        $log.debug(partnerPropertyId);
        return partnerPropertyEndpoint.deletePartnerProperty(partnerPropertyId).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not delete Partner Property");
          return $q.reject(err);
        });

      };

    }
  ]);

})(angular.module('aet.domain.partnerProperty'));

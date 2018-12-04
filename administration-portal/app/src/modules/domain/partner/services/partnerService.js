(function(module) {

  module.service('partnerService', ['partnerEndpoint', '$q', '$log', 'partnerTransformer',
    function(partnerEndpoint, $q, $log, partnerTransformer) {

      this.createPartner = function(partner) {
        $log.debug("partnerService::createPartner", partner)

        var createDTO = partnerTransformer.partnerToCreateDTO(partner);
        return partnerEndpoint.createPartner(createDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not create partner");
          return $q.reject(err);
        });

      };

      this.searchPartners = function(searchObject) {
        return partnerEndpoint.searchPartners(searchObject).then(function(dto) {
          return partnerTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          console.error("Could not search partners");
          return $q.reject(err);
        });

      };

      this.editPartner = function(partner) {
        var updateDTO = partnerTransformer.partnerToUpdateDTO(partner)

        return partnerEndpoint.editPartner(updateDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not update partner");
          return $q.reject(err);
        });
      };

      this.getPartner = function(partnerId) {
        return partnerEndpoint.getPartner(partnerId).then(function(dto) {
          return partnerTransformer.getDTOToPartner(dto.data);
        }, function(err) {
          console.error("Could not get partner");
          return $q.reject(err);
        });
      };

      this.deletePartner = function(partnerId) {
        $log.debug("You're in the deletePartner method (partnerService)");
        $log.debug(partnerId);
        return partnerEndpoint.deletePartner(partnerId).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not delete partner");
          return $q.reject(err);
        });

      };

    }
  ]);

})(angular.module('aet.domain.partner'));

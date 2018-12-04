(function(module) {

  module.service('partnerPlaceService', ['partnerPlaceEndpoint', 'partnerPlaceTransformer', '$q', '$log', 'ChangeChannelDTO', 'partnerDetails', 'PartnerRole', 'userDetails',
    function(partnerPlaceEndpoint, partnerPlaceTransformer, $q, $log, ChangeChannelDTO, partnerDetails, PartnerRole, userDetails) {

      this.createPartnerPlace = function(partnerPlace) {
        var dto = partnerPlaceTransformer.partnerPlaceToCreateDTO(partnerPlace);
        return partnerPlaceEndpoint.createPartnerPlace(dto).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not create partner place", err);
          return $q.reject(err);
        });

      };

      this.deletePartnerPlace = function(partnerPlaceId) {
        return partnerPlaceEndpoint.deletePartnerPlace(partnerPlaceId).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not delete partner place", err);
          return $q.reject(err);
        });
      };

      this.updatePartnerPlace = function(partnerPlace) {
        var dto = partnerPlaceTransformer.partnerPlaceToUpdateDTO(partnerPlace);

        return partnerPlaceEndpoint.updatePartnerPlace(dto).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not update partner place", err);
          return $q.reject(err);
        });
      };


      this.findPartnerPlace = function(id) {

        return partnerPlaceEndpoint.findPartnerPlace(id).then(function(response) {
          return partnerPlaceTransformer.DTOToPartnerPlace(response.data);
        }, function(err) {
          //console.error("Could not load partner place");
          return $q.reject(err);
        });
      };

      this.searchPartnerPlace = function(params) {
        return partnerPlaceEndpoint.listPartnerPlace( partnerPlaceTransformer.partnerPlaceParamConvert(params)).then(function(dto) {
          return partnerPlaceTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          //console.error("Could not search partner place", err);
          return $q.reject(err);
        });

      };

      this.getPartnerPlacesBySelectedPartner = function(partner) {
        return partnerPlaceEndpoint.listPartnerPlacesBySelectedPartner(partner).then(function(dto) {
          return partnerPlaceTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          //console.error("Could not search partner place", err);
          return $q.reject(err);
        });

      };

      this.searchAllPartnerPlace = function(params) {
        return partnerPlaceEndpoint.listAllPartnerPlace(partnerPlaceTransformer.partnerPlaceParamConvert(params)).then(function(dto) {
          return partnerPlaceTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          //console.error("Could not search all partner place", err);
          return $q.reject(err);
        });

      };

      this.changeSelectedChannel = function(channel, partnerPlaceId) {
        var dto = partnerPlaceTransformer.channelToChangeChannelDTO(channel);
        return partnerPlaceEndpoint.changeSelectedChannel(dto, partnerPlaceId).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not change channel title", err);
          return $q.reject(err);
        });

      };

      this.sendTestMail = function(testEmail) {
        var dto = partnerPlaceTransformer.testEmailToSendTestEmailDTO(testEmail);
        return partnerPlaceEndpoint.sendTestMail(dto).then(function(response) {
          return response.data;
        }, function(err) {
          $log.debug("Could not send test e-mail", err);
          return $q.reject(err);
        });

      };

      this.copyPartnerPlace = function(id, copyPartnerPlaceRequest) {

          return partnerPlaceEndpoint.copyPartnerPlace(id, copyPartnerPlaceRequest).then(function(response) {
            
            return partnerPlaceTransformer.DTOToPartnerPlace(response.data);
          }, function(err) {
            console.error("Could not copy partner place");
            return $q.reject(err);
          });
      };

      this.validateDuration = function(requestDTO) {

        return partnerPlaceEndpoint.validateDuration(requestDTO).then(function(response) {

          return partnerPlaceTransformer.DTOToValidateDurationResponse(response.data);
        }, function(err) {
          console.error("Could not validate duration");
          return $q.reject(err);
        });
      };


    }
  ]);

})(angular.module('aet.domain.partnerPlace'));

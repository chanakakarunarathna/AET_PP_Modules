(function(module) {

  module.service('partnerPlaceEndpoint', ['$q', '$http', 'api', 'partnerDetails', 'userDetails', 'CopyPartnerPlaceDTO',
    function($q, $http, api, partnerDetails, userDetails, CopyPartnerPlaceDTO) {

      this.createPartnerPlace = function(partnerPlace) {
        var createPartnerPlaceUrl = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place';
        return $http.post(createPartnerPlaceUrl, partnerPlace);
      };

      this.updatePartnerPlace = function(partnerPlace) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + partnerPlace.id;
        return $http.put(url, partnerPlace);
      };

      this.findPartnerPlace = function(id) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + id;
        return $http.get(url);
      };

      this.listPartnerPlace = function(params) {
        var listPartnerPlaceURL = api('admin') + '/adminuser/' + userDetails.getUserId() + '/partner/' + partnerDetails.getSelectedPartner().id + '/place';
        return $http.get(listPartnerPlaceURL, {
          params: params
        });
      };

      this.listPartnerPlacesBySelectedPartner = function(partner) {
        var listPartnerPlaceURL = api('admin') + '/adminuser/' + userDetails.getUserId() + '/partner/' + partner.id + '/place';
        return $http.get(listPartnerPlaceURL, {
          params: {
            count: 0
          }
        });
      };

      this.deletePartnerPlace = function(id) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + id;
        return $http.delete(url);
      };

      this.listAllPartnerPlace = function(params) {
        var listAllPartnerPlaceURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place';
        return $http.get(listAllPartnerPlaceURL, {
          params: params
        });
      };

      this.changeSelectedChannel = function(channel, partnerPlaceId) {
        var changeChannelUrl = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + partnerPlaceId + '/channel/' + channel.channelId;
        return $http.post(changeChannelUrl, channel);
      };

      this.sendTestMail = function(testEmail) {
        var sendtestemailUrl = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/sendtestemail';
        return $http.post(sendtestemailUrl, testEmail);
      };

      this.copyPartnerPlace = function(id, copyPartnerPlaceRequest) {
          var copyPartnerPlaceURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + id + '/copy';
          return $http.post(copyPartnerPlaceURL, copyPartnerPlaceRequest);
      };

      this.validateDuration = function(requestDTO) {

        var validateDurationURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/place/' + requestDTO.partnerPlaceId + '/validateduration';
        return $http.post(validateDurationURL, requestDTO);
      };
    }
  ]);

})(angular.module('aet.endpoints'));

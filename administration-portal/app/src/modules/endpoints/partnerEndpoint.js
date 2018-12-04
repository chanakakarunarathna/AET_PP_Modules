(function(module) {

  module.service('partnerEndpoint', ['$q', '$http', 'api',
    function($q, $http, api) {

      var controller = '/partner';

      this.createPartner = function(partnerRequestDTO) {
        var createPartnerURL = api('admin') + controller;

        return $http.post(createPartnerURL, partnerRequestDTO);
      };

      this.editPartner = function(dto) {

        var urlExtension = '/' + dto.id; // 1 is for partnerId
        var editPartnerURL = api('admin') + controller + urlExtension;

        return $http.put(editPartnerURL, dto);
      };

      this.getPartner = function(partnerId) {
        //console.log("partner - Endpoint - getPartners");
        var urlExtension = '/' + partnerId; // 1 is for partnerId
        var getPartnerURL = api('admin') + controller + urlExtension;
        ////console.log("URL " + getPartner);
        return $http.get(getPartnerURL);
      };

      this.searchPartners = function(searchQueryDTO) {
        var urlExtension = '1/partner'; // 1 is for partnerId
        var searchPartnersURL = api('admin') + controller;

        //console.log("partner - Endpoint - searchPartners", searchPartnersURL);

        return $http.get(searchPartnersURL, {
          params: searchQueryDTO
        });
      };

      this.deletePartner = function(partnerId) {
        //console.log("partner - Endpoint - deletePartner");
        var urlExtension = '/' + partnerId;
        var deletePartnerURL = api('admin') + controller + urlExtension;
        //console.log("URL " + deletePartnerURL);
        return $http.delete(deletePartnerURL);
      };
    }

  ]);

})(angular.module('aet.endpoints'));

(function(module) {

  module.service('partnerPropertyEndpoint', ['$q', '$http', 'api', 'partnerDetails',
    function($q, $http, api, partnerDetails) {

      var controller = '/property';

      this.createPartnerProperty = function(partnerPropertyRequestDTO) {
        var createPlacesURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + controller;
        return $http.post(createPlacesURL, partnerPropertyRequestDTO);
      };

      this.editPartnerProperty = function(dto) {
        var urlExtension = '/' + dto.id;
        var editPlacesURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + controller + urlExtension;
        return $http.put(editPlacesURL, dto);
      };

      this.getPartnerProperty = function(partnerPropertyId) {
        var urlExtension = '/' + partnerPropertyId;
        var getPlacesURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + controller + urlExtension;

        return $http.get(getPlacesURL);
      };

      this.searchPartnerProperties = function(searchQueryDTO) {
        var searchPlacessURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + controller;
        return $http.get(searchPlacessURL, {
          params: searchQueryDTO
        });
      };

      this.deletePartnerProperty = function(partnerPropertyId) {
        var urlExtension = '/' + partnerPropertyId;
        var deletePlacesURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + controller + urlExtension;
        return $http.delete(deletePlacesURL);
      };
    }

  ]);

})(angular.module('aet.endpoints'));

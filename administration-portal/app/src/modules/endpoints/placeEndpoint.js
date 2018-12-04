(function(module) {

  module.service('placesEndpoint', ['$q', '$http', 'api','partnerDetails',
    function($q, $http, api, partnerDetails) {

      var controller = '/places';

      this.createPlaces = function(placesRequestDTO) {
        var createPlacesURL = api('admin') + controller;

        return $http.post(createPlacesURL, placesRequestDTO);
      };

      this.editPlaces = function(dto) {

        var urlExtension = '/' + dto.id; // 1 is for placesId
        var editPlacesURL = api('admin') + controller + urlExtension;

        return $http.put(editPlacesURL, dto);
      };

      this.getPlaces = function(placesId) {
        //console.log("places - Endpoint - getPlacess");
        var urlExtension = '/' + placesId; // 1 is for placesId
        var getPlacesURL = api('admin') + controller + urlExtension;
        ////console.log("URL " + getPlaces);
        return $http.get(getPlacesURL);
      };

      this.searchPlacess = function(searchQueryDTO) {
        var searchPlacessURL = api('admin') + controller;
        //console.log("places - Endpoint - searchPlacess", searchPlacessURL);
        return $http.get(searchPlacessURL, {params: searchQueryDTO});
      };

      this.searchByQueryPlacess = function(searchQueryDTO) {
        var urlExtension = '?q=' + searchQueryDTO.q + '&searchby=' + searchQueryDTO.searchby; // 1 is for placesId
        var searchPlacessURL = api('admin') + controller + urlExtension;
        return $http.get(searchPlacessURL);
      };

      this.deletePlaces = function(placesId) {
        //console.log("places - Endpoint - deletePlaces");
        var urlExtension = '/' + placesId;
        var deletePlacesURL = api('admin') + controller + urlExtension;
        //console.log("URL " + deletePlacesURL);
        return $http.delete(deletePlacesURL);
      };
    }

  ]);

})(angular.module('aet.endpoints'));

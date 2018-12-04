(function(module) {

  module.service('placesService', ['placesEndpoint', '$q', '$log', 'placesTransformer',
    function(placesEndpoint, $q, $log, placesTransformer) {

      this.createPlaces = function(places) {
        $log.debug("placesService::createPlaces", places)

        var createDTO = placesTransformer.placesToCreateDTO(places);
        return placesEndpoint.createPlaces(createDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not create places");
          return $q.reject(err);
        });

      };

      this.searchPlacess = function(searchObject) {
    	return placesEndpoint.searchPlacess(placesTransformer.placeParamConvert(searchObject)).then(function(dto) {
          return placesTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          console.error("Could not search placess");
          return $q.reject(err);
        });

      };

      this.searchByQueryPlacess = function(searchObject) {
        return placesEndpoint.searchByQueryPlacess(searchObject).then(function(dto) {
          return placesTransformer.searchDTOToSearchResults(dto);
        }, function(err) {
          console.error("Could not search placess");
          return $q.reject(err);
        });
      };

      this.editPlaces = function(places) {
        var updateDTO = placesTransformer.placesToUpdateDTO(places)

        return placesEndpoint.editPlaces(updateDTO).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not update places");
          return $q.reject(err);
        });
      };

      this.getPlaces = function(placesId) {
        return placesEndpoint.getPlaces(placesId).then(function(dto) {
          return placesTransformer.getDTOToPlaces(dto.data);
        }, function(err) {
          console.error("Could not get places");
          return $q.reject(err);
        });
      };

      this.deletePlaces = function(placesId) {
        $log.debug("You're in the deletePlaces method (placesService)");
        $log.debug(placesId);
        return placesEndpoint.deletePlaces(placesId).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not delete places");
          return $q.reject(err);
        });

      };

    }
  ]);

})(angular.module('aet.domain.places'));

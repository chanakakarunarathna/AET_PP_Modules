(function(module) {

  module.service('UpdatePlacesRequestDTO', [function() {

    function UpdatePlacesRequestDTO() {

      this.id = undefined;
      this.city = undefined;
      this.continent = undefined;
      this.country = undefined;
      this.countryWebId = undefined;
      this.customMD = undefined;
      this.formattedAddress = undefined;
      this.placePassRegion = undefined;
      this.placePassRegionWebId = undefined;
      this.region = undefined;
      this.regionWebId = undefined;
      this.webId = undefined;
      this.geoLocation = undefined;
      this.locationType = undefined;
      this.addrsComponents = undefined;
      this.aliases = undefined;

    }

    return UpdatePlacesRequestDTO;

  }]);

})(angular.module('aet.domain.places'));

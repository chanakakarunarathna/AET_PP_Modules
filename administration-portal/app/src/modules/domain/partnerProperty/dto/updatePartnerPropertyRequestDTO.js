(function(module) {

  module.service('UpdatePartnerPropertyRequestDTO', [function() {

    function UpdatePartnerPropertyRequestDTO() {

      this.id = undefined;
      this.partnerId = undefined;
      this.propertyCode = undefined;
      this.propertyName = undefined;
      this.brand = undefined;
      this.subBrand = undefined;
      this.imageUrl = undefined;
      this.geoLocation = undefined;
      this.customMD = undefined;

    }

    return UpdatePartnerPropertyRequestDTO;

  }]);

})(angular.module('aet.domain.partnerProperty'));

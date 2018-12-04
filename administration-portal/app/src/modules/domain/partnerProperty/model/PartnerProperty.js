(function(module) {

  module.service('PartnerProperty', [function() {

    function PartnerProperty() {

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

    return PartnerProperty;

  }]);

})(angular.module('aet.domain.partnerProperty'));
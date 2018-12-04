(function(module) {

  module.service('CreatePartnerPropertyRequestDTO', [function() {

    function CreatePartnerPropertyRequestDTO() {
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

    return CreatePartnerPropertyRequestDTO;

  }]);

})(angular.module('aet.domain.partnerProperty'));

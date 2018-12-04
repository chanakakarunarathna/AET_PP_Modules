(function(module) {

  module.service('UpdatePartnerRequestDTO', [function() {

    function UpdatePartnerRequestDTO() {

      this.id = undefined;
      this.name = undefined;
      this.streetAddress1 = undefined;
      this.streetAddress2 = undefined;
      this.city = undefined;
      this.state = undefined;
      this.zipCode = undefined;
      this.subLevel = undefined;

    }

    return UpdatePartnerRequestDTO;

  }]);

})(angular.module('aet.domain.partner'));

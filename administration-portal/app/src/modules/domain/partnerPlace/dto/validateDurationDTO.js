(function(module) {

  module.service('ValidateDurationDTO', [function() {

    function ValidateDurationDTO() {

      this.partnerId = undefined;
      this.partnerPlaceId = undefined;
    }

    return ValidateDurationDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

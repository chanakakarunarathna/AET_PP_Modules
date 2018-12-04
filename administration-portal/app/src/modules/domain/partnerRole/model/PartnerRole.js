(function(module) {

  module.service('PartnerRole', [function() {

    function PartnerRole() {

      this.id = undefined;
      this.partner = undefined;
      this.role = undefined;

    }

    return PartnerRole;

  }]);

})(angular.module('aet.domain.partnerRole'));

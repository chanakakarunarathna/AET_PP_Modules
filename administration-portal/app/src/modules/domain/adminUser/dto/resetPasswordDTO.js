(function(module) {

  module.service('ResetPasswordDTO', [function() {

    function ResetPasswordDTO() {

      this.email = undefined;
      this.token = undefined;
      this.password = undefined;

    }

    return ResetPasswordDTO;

  }]);

})(angular.module('aet.domain.adminUser'));

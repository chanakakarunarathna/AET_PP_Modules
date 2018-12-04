(function(module) {

  module.service('ResetPasswordRequestDTO', [function() {

    function ResetPasswordRequestDTO() {

      this.email = undefined;

    }

    return ResetPasswordRequestDTO;

  }]);

})(angular.module('aet.domain.adminUser'));

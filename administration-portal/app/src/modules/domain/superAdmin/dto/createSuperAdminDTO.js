(function(module) {

  module.service('CreateSuperAdminDTO', [function() {

    function CreateSuperAdminDTO() {

      this.title = undefined;
      this.firstName = undefined;
      this.lastName = undefined;
      this.email = undefined;
      this.isSuperAdmin = undefined;
      this.pictureUrl = undefined;
      this.partnerRoles = [];

    }

    return CreateSuperAdminDTO;

  }]);

})(angular.module('aet.domain.superAdmin'));

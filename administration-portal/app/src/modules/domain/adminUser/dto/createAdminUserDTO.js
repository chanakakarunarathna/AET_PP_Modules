(function(module) {

  module.service('CreateAdminUserDTO', [function() {

    function CreateAdminUserDTO() {

      this.title = undefined;
      this.firstName = undefined;
      this.lastName = undefined;
      this.email = undefined; 
      this.isSuperAdmin = undefined;
      this.pictureUrl = undefined;    
      this.partnerRoles = [];

    }

    return CreateAdminUserDTO;

  }]);

})(angular.module('aet.domain.adminUser'));

(function(module) {

  module.service('UpdateAdminUserDTO', [function() {

    function UpdateAdminUserDTO() {

    	this.id=undefined;
    	this.title = undefined;
        this.firstName = undefined;
        this.lastName = undefined;
        this.email = undefined;
        this.isSuperAdmin = undefined;
        this.pictureUrl = undefined;
        this.partnerRoles = [];
        this.isActive=true;
        this.sendDiscussionDailyNotifications = undefined;

    }

    return UpdateAdminUserDTO;

  }]);

})(angular.module('aet.domain.adminUser'));

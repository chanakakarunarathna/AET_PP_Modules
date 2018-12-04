(function(module) {

  module.service('UpdateSelfAdminUserDTO', [function() {

    function UpdateSelfAdminUserDTO() {

    	this.id=undefined;
    	this.title = undefined;
      this.firstName = undefined;
      this.lastName = undefined;
      this.email = undefined;
      this.sendDiscussionDailyNotifications = undefined;

    }

    return UpdateSelfAdminUserDTO;

  }]);

})(angular.module('aet.domain.adminUser'));

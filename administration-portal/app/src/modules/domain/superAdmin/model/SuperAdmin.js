(function(module) {

  module.service('SuperAdmin', [function() {

    function SuperAdmin() {

      this.id = undefined;
      this.title = undefined;
      this.firstName = undefined;
      this.lastName = undefined;
      this.email = undefined;
      this.partnerId = undefined;
      this.roleId = undefined;
      this.pictureUrl = undefined;
      this.isSuperAdmin = undefined;
      this.createdDate = undefined;
      this.sendDiscussionDailyNotifications = undefined;
      this.showDiscussionDailyNotificationsCheckbox = undefined;

      this.partnerRoles = [];

    }

    return SuperAdmin;

  }]);

})(angular.module('aet.domain.superAdmin'));

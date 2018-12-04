(function(module) {

  module.service('AdminUser', [function() {

    function AdminUser() {

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

    return AdminUser;

  }]);

})(angular.module('aet.domain.adminUser'));

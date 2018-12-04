(function(module) {

    module.service('UpdateSuperAdminDTO', [function() {

        function UpdateSuperAdminDTO() {

            this.id = undefined;
            this.title = undefined;
            this.firstName = undefined;
            this.lastName = undefined;
            this.email = undefined;
            this.isSuperAdmin = undefined;
            this.pictureUrl = undefined;
            this.partnerRoles = [];
            this.isActive = true;
            this.sendDiscussionDailyNotifications = undefined;

        }

        return UpdateSuperAdminDTO;

    }]);

})(angular.module('aet.domain.superAdmin'));

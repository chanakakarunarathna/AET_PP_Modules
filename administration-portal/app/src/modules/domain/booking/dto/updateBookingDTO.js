(function(module) {

  module.service('UpdateBookingDTO', [function() {

    function UpdateBookingDTO() {

    	this.id=undefined;
    	this.title = undefined;
        this.firstName = undefined;
        this.lastName = undefined;
        this.email = undefined;
        this.isSuperAdmin = undefined;
        this.pictureUrl = undefined;
        this.createdTime = undefined;
        this.partnerRoles = [];
        this.isActive=true;
        this.sendDiscussionDailyNotifications = undefined;

    }

    return UpdateBookingDTO;

  }]);

})(angular.module('aet.domain.booking'));

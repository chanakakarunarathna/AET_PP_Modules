(function(module) {

  module.service('UpdatePartnerPlaceDTO', [function() {

    function UpdatePartnerPlaceDTO() {

      this.id = undefined;
      this.creatorId = undefined;
      this.location = undefined;
      this.activityCount = undefined;
      this.parentId = undefined;
      this.parentWebId = undefined;
      this.customMD = undefined;
      this.getToKnow = undefined;
      this.webId = undefined;
      this.hero = undefined;
      this.topTips = [];
      this.partnerCollection = [];
      this.partnerPlaceAdminIds = [];
      this.partnerPlaceTeamMemberIds = [];
      this.partnerPlaceSupportMemberIds = [];
      this.isTopDestination = undefined;
      this.hasCityGuide = undefined;
      this.vendorActivityCount = [];
    }

    return UpdatePartnerPlaceDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

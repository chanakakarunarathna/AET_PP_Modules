(function(module) {

  module.service('CreatePartnerPlaceDTO', [function() {

    function CreatePartnerPlaceDTO() {

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

    return CreatePartnerPlaceDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

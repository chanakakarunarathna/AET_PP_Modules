(function(module) {

  module.service('PartnerPlace', [function() {

    function PartnerPlace() {
      this.id = undefined;
      this.location = undefined;
      this.parentId = undefined;
      this.parentWebId = undefined;
      this.hero = undefined;
      this.partnerPlaceAdmins = [];
      this.partnerPlaceSupportMembers = [];
      this.partnerPlaceTeamMembers = [];
      this.topTips = [];
      this.partnerCollection = [];
      this.activityCount = [];
      this.customMD = undefined;
      this.getToKnow = undefined;
      this.isTopDestination = undefined;
      this.hasCityGuide = undefined;
      this.vendorActivityCount = [];
      this.parentAliases = undefined;
    }

    return PartnerPlace;

  }]);

})(angular.module('aet.domain.partnerPlace'));

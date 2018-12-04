(function(module) {

  module.service('CopyPartnerPlaceDTO', [function() {

    function CopyPartnerPlaceDTO() {

      this.creatorId = undefined;
      this.title = undefined;
      this.partnerPlaceLeaderIds = [];
    }

    return CopyPartnerPlaceDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

(function(module) {

  module.service('PartnerConfig', [function() {

    function PartnerConfig() {
      this.id = undefined;
      this.partnerId = undefined;
      this.hero = undefined;
      this.customMD = undefined;
	  this.featuredProductIds = undefined;
      this.destinations = [];
    }

    return PartnerConfig;

  }]);

})(angular.module('aet.domain.partnerConfig'));

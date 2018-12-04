(function(module) {

  module.service('UpdatePartnerConfigDTO', [function() {

    function UpdatePartnerConfigDTO() {
    	this.id = undefined;
		this.partnerId = undefined;
		this.hero = undefined;
		this.destinations = [];
		this.featuredProductIds = [];
		this.customMD = undefined;
    }

    return UpdatePartnerConfigDTO;

  }]);

})(angular.module('aet.domain.partnerConfig'));

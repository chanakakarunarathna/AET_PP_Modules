(function(module) {

  module.service('CreatePartnerConfigDTO', [function() {

    function CreatePartnerConfigDTO() {

    	this.partnerId = undefined;
        this.hero = undefined;
        this.destinations = [];
        this.featuredProductIds = [];
        this.customMD = undefined;
    }

    return CreatePartnerConfigDTO;

  }]);

})(angular.module('aet.domain.partnerConfig'));

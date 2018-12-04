(function(module) {

  module.service('SearchPartnerRequestDTO', [function() {

    function SearchPartnerRequestDTO() {

      this.q = undefined;
      this.page = undefined;
      this.count = undefined;
      this.sortby = undefined;
      this.sortdirection = undefined;

    }

    return SearchPartnerRequestDTO;

  }]);

})(angular.module('aet.domain.partnerProperty'));

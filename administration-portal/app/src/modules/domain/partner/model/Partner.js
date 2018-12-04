(function(module) {

  module.service('Partner', [function() {

    function Partner() {

      this.id = undefined;
      this.name = undefined;
      this.streetAddress1 = undefined;
      this.streetAddress2 = undefined;
      this.city = undefined;
      this.state = undefined;
      this.zipCode = undefined;
      this.subLevel = undefined;
      this.shortName = undefined;
      this.createdDate = undefined;
    }

    return Partner;

  }]);

})(angular.module('aet.domain.partner'));

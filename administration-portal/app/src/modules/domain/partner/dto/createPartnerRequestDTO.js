(function(module) {

  module.service('CreatePartnerRequestDTO', [function() {

    function CreatePartnerRequestDTO() {
      this.id = undefined;
      this.name = undefined;
      this.shortName = undefined;
      this.streetAddress1 = undefined;
      this.streetAddress2 = undefined;
      this.city = undefined;
      this.state = undefined;
      this.zipCode = undefined;
      this.subLevel = undefined;
    }

    return CreatePartnerRequestDTO;

  }]);

})(angular.module('aet.domain.partner'));

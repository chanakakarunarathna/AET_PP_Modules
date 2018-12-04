(function(module) {

  module.service('partnerDetails', ['localStorageService', '$q', 'partnerEndpoint',
    function(localStorageService, $q, partnerEndpoint) {

      var _partners = undefined;
      var _authorizedPartners = undefined;
      var _selectedPartner = undefined;
      var partnerStorageKey = 'partnerId';


      this.setPartners = function(admin, partners) {


        if (angular.isDefined(partners)) {
          _partners = partners;
          _authorizedPartners = partners.results;
        } else {
          _authorizedPartners = _.values(_.reduce(admin.partnerRoles, function(obj, partnerRole) {
            if (!obj.hasOwnProperty(partnerRole.partner.id))
              obj[partnerRole.partner.id] = partnerRole.partner;

            return obj;
          }, {}));
        }

        this.initSelectedPartner();

      };

      this.initSelectedPartner = function() {

        var storedId = localStorageService.get(partnerStorageKey);

        if (angular.isDefined(_selectedPartner)) {
          return;
        } else if (storedId) {
          this.setSelectedPartner(storedId);
        } else {
          this.setSelectedPartner(_authorizedPartners[0].id);
        }
      };

      this.getAuthorizedPartners = function() {
        return _authorizedPartners;
      };

      this.setSelectedPartner = function(partnerId) {
        var partner = _.find(_authorizedPartners, function(authPartner) {
          return authPartner.id === partnerId
        });
        if (angular.isDefined(partner)) {
          _selectedPartner = partner;
          localStorageService.set(partnerStorageKey, partner.id);
        }
      };

      this.getSelectedPartner = function() {
        return _selectedPartner;
      };

      this.clear = function() {
        _partners = undefined;
        _authorizedPartners = undefined;
        _selectedPartner = undefined;
        localStorageService.remove(partnerStorageKey);
      };

    }
  ]);

})(angular.module('aet.security'));

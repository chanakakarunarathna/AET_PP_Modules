(function(module) {

  module.service('partnerPlaceDetails', ['localStorageService', 'partnerDetails', '$q', 'partnerPlaceEndpoint',
    function(localStorageService, partnerDetails, $q, partnerPlaceEndpoint) {

      var _partnerPlace = undefined;
      var _authorizedPartnerPlaces = undefined;
      var _selectedPartnerPlace = undefined;
      var partnerPlaceStorageKey = 'partnerPlaceId';


      this.setPartnerPlaces = function(admin, partnerPlace) {
        if (admin.isSuperAdmin) {

          if (angular.isDefined(partnerPlace)) {
            _partnerPlace = partnerPlace;
            _authorizedPartnerPlaces = [];
            for (key in partnerPlace.results) {
              if (partnerPlace.results[key].partner.id === partnerDetails.getSelectedPartner().id) {
                _authorizedPartnerPlaces.push(partnerPlace.results[key]);
              }
            }
          }

        } else {
          _authorizedPartnerPlaces = [];
          for (key in admin.partnerPlaceRoles) {
            if (admin.partnerPlaceRoles[key].partnerPlace.partner.id === partnerDetails.getSelectedPartner().id) {
              _authorizedPartnerPlaces.push(admin.partnerPlaceRoles[key].partnerPlace);
            }
          }

        }
        this.initSelectedPartnerPlace();

      };

      this.initSelectedPartnerPlace = function() {

        var storedId = localStorageService.get(partnerPlaceStorageKey);

        if (angular.isDefined(_selectedPartnerPlace)) {
          return;
        } else if (storedId) {
          this.setSelectedPartnerPlace(storedId);
        }
        // else {
        //
        //   if (angular.isDefined(_authorizedPartnerPlaces) && _authorizedPartnerPlaces.length > 0) {
        //     //this.setSelectedPartnerPlace(_authorizedPartnerPlaces[0].id);
        //   }
        //
        // }
      };

      this.getAuthorizedPartnerPlaces = function() {
        if (angular.isDefined(_authorizedPartnerPlaces)) {
          return _authorizedPartnerPlaces;
        } else {
          return null; //if selected partner doesn't have partner places
        }
      };

      this.setSelectedPartnerPlace = function(partnerPlaceId) {
        var pro = _.find(_authorizedPartnerPlaces, function(authPro) {
          return authPro.id === partnerPlaceId
        });
        if (angular.isDefined(pro)) {
          _selectedPartnerPlace = pro;
          localStorageService.set(partnerPlaceStorageKey, pro.id);
        }
      };


      this.getSelectedPartnerPlace = function() {
        this.initSelectedPartnerPlace();
        return _selectedPartnerPlace;

      };

      this.clear = function() {
        _partnerPlace = undefined;
        _authorizedPartnerPlaces = undefined;
        _selectedPartnerPlace = undefined;
        localStorageService.remove(partnerPlaceStorageKey);
      };

    }
  ]);

})(angular.module('aet.security'));

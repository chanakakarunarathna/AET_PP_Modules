(function(module) {

  /**
   * @ngdoc object
   * @name aet.modals.modalService
   *
   * @description A service used to display modals in the application
   *
   *
   *
   */
  module.service('partnerPlaceSelectorService', ['$modal', '$log', '$q', '$rootScope',
    function($modal, $log, $q, $rootScope) {


      this.partnerPlaceSelector = function(selectedPartnerPlace) {

        var scope = $rootScope.$new(true);
        scope.name = name;
        scope.selectedPartnerPlace = selectedPartnerPlace;

        return $modal.open({

          templateUrl: 'src/modules/modal/templates/partnerPlaceSelector.html',
          controller: 'PartnerPlaceSelectorController',
          scope: scope

        });
      };

    }
  ]);

})(angular.module('aet.modals'));

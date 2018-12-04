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
  module.service('partnerSelectorService', ['$modal',  '$log', '$q', '$rootScope',
    function($modal,  $log, $q, $rootScope) {

      this.partnerSelector = function(partnerPlaceList, partner) {
        var scope = $rootScope.$new(true);
        scope.name = name;
        scope.partnerPlaces = partnerPlaceList;
        scope.selectedPartnerPlace = partnerPlaceList[0];
        scope.partner = partner;
        return $modal.open({
          templateUrl: 'src/modules/modal/templates/partnerSelector.html',
          controller: 'PartnerSelectorController',
          scope: scope
        });
      };

    }
  ]);

})(angular.module('aet.modals'));

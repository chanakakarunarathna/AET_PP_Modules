(function(module) {

  module.controller('partnerPlaceAndListWarningController', ['$scope', '$modalInstance', '$log', 'modalService',
    function($scope, $modalInstance, $log, modalService) {

      $log.debug("partnerPlaceAndListWarningController");

      $scope.ok = function() {
        $modalInstance.close();
      };

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };

      $scope.showPartnerPlaceList = function() {
        var modalInstance = modalService.viewPartnerPlaceList($scope.partnerPartnerPlaces);
        modalInstance.result.then(function() {

        });
      }

      $scope.showUserList = function() {

      }


    }
  ]);

})(angular.module('aet.modals'));

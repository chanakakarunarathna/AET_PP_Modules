(function(module) {

  module.controller('PartnerPlaceSelectorController', ['$scope', '$rootScope', '$state', '$modalInstance', '$log', 'userDetails', 'partnerDetails', 'partnerPlaceDetails',
    function($scope, $rootScope, $state, $modalInstance, $log, userDetails, partnerDetails, partnerPlaceDetails) {

      $scope.partnerDetails = partnerDetails;
      $scope.partnerPlaceDetails = partnerPlaceDetails;
      $scope.userDetails = userDetails;

      $scope.selectPartnerPlace = function(partnerPlace) {
        $scope.selectedPartnerPlace=partnerPlace;
      }

      $scope.ok = function() {
    	$rootScope.showLoader('Loading...');
        $state.go('index.secured.dashboard',{},{reload:true});
        $modalInstance.close();
      };

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };

    }
  ]);

})(angular.module('aet.modals'));

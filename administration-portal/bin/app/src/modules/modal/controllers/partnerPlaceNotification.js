(function(module) {

  module.controller('PartnerPlaceNotificationController', ['$scope', '$rootScope', '$state', '$modalInstance', '$log', 'userDetails', 'partnerDetails', 'partnerPlaceDetails', 'partnerPlaceService', 'localStorageService',
    function($scope, $rootScope, $state, $modalInstance, $log, userDetails, partnerDetails, partnerPlaceDetails, partnerPlaceService, localStorageService) {

      $scope.loadingPartnerPlaces = true;
      $scope.partnerDetails = partnerDetails;
      $scope.partnerPlaceDetails = partnerPlaceDetails;
      $scope.userDetails = userDetails;

      var refreshPartnerPlaces = function(partner) {
        return partnerPlaceService.getPartnerPlacesBySelectedPartner(partner).then(function(partnerPlaceList) {
          $scope.partnerPlaces = partnerPlaceList.results;
          $scope.partnerPlacesResult = partnerPlaceList;
          $scope.selectedPartnerPlace = partnerPlaceList.results[0];
        }).finally(function() {
          $scope.loadingPartnerPlaces = false;
        });
      };

      refreshPartnerPlaces($scope.partnerDetails.getSelectedPartner()).finally(function() {
        $scope.loadingPartnerPlaces = false;
      });
      $scope.selectedPartner = $scope.partnerDetails.getSelectedPartner();

      $scope.selectPartnerPlace = function(partnerPlace) {
        $scope.selectedPartnerPlace = partnerPlace;
      }

      $scope.selectPartner = function(partner) {
        $scope.selectedPartner = partner;
        $scope.loadingPartnerPlaces = true;
        refreshPartnerPlaces(partner).finally(function() {
          $scope.loadingPartnerPlaces = false;
        });
      };

      $scope.ok = function() {

        partnerDetails.setSelectedPartner($scope.selectedPartner.id);
        partnerPlaceDetails.clear();
        if(angular.isDefined($scope.selectedPartnerPlace)) {
          localStorageService.set('partnerPlaceId', $scope.selectedPartnerPlace.id);
        }

        $rootScope.showLoader('Loading...');
        $state.go('index.secured.dashboard', {}, {reload:true}).finally(function() {
          $rootScope.hideLoader();
        });

        $modalInstance.close();
      };

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };



    }
  ]);

})(angular.module('aet.modals'));

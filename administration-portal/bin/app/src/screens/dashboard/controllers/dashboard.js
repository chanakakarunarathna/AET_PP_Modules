(function(module) {

    module.controller('DashboardController', ['$scope','modalService', '$rootScope', '$state', 'partnerPlaceDetails', 'security', '$timeout', 'partnerDetails',
        function($scope,modalService, $rootScope, $state, partnerPlaceDetails, security, $timeout, partnerDetails) {
          // $rootScope.loader = false;

          $scope.partnerPlaceDetails = partnerPlaceDetails;
          $scope.security = security;
          $scope.partnerDetails = partnerDetails;

          $scope.hasPermissionForAddPartnerPlace = security.isAuthorized('ADD_PARTNER_PLACE')
          $scope.hasPermissionForPartnerPlace = security.isAuthorized('LIST_SEARCH_PARTNER_PLACE')

          /*var mapViewState = $state.get('index.secured.map.view');
          if(angular.isDefined(partnerPlaceDetails.getSelectedPartnerPlace()) && security.isAuthorized(mapViewState.data.feature)) {
            $timeout(function() {
              $rootScope.showLoader('Please Wait...');
            });
            $state.go(mapViewState.name).finally(function() {
              $timeout(function() {
                $rootScope.hideLoader();
              });
            });
          }*/
        }
    ]);

})(angular.module('aet.screens.dashboard'));

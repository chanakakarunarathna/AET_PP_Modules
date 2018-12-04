(function(module) {

  module.controller('HeaderController', ['$scope', 'partnerPlaceSelectorService', 'localStorageService', 'partnerSelectorService', 'userDetails', '$state', '$rootScope', 'loginService', 'partnerPlaceService', 'partnerDetails', 'partnerPlaceDetails','security',
    function($scope, partnerPlaceSelectorService, localStorageService, partnerSelectorService, userDetails, $state, $rootScope, loginService, partnerPlaceService, partnerDetails, partnerPlaceDetails, security) {
	  
	  $scope.hasPermissionForPartnerPlace = security.isAuthorized('LIST_SEARCH_PARTNER_PLACE')
      $scope.partnerDetails = partnerDetails;
      $scope.partnerPlaceDetails = partnerPlaceDetails;

      $scope.selectPartnerPlace = function(partnerPlace) {
        var addModalInstance = partnerPlaceSelectorService.partnerPlaceSelector(partnerPlace);
        addModalInstance.result.then(function() {
          partnerPlaceDetails.setSelectedPartnerPlace(partnerPlace.id);

        })
      };

      $scope.manage = function() {
        $state.go('index.secured.manageaccount', {}, {
          reload: true
        });
      };

      $scope.selectPartner = function(partner) {

        partnerPlaceService.getPartnerPlacesBySelectedPartner(partner).then(function(partnerPlacesResponse) {
          var partnerPlaceList = partnerPlacesResponse.results;
          var addModalInstance = partnerSelectorService.partnerSelector(partnerPlaceList, partner);
          addModalInstance.result.then(function(data) {

            partnerDetails.setSelectedPartner(data.selectedPartner.id);
            var partnerPlaceStorageKey = 'partnerPlaceId';
            if (angular.isDefined(data.selectedPartnerPlace)) {
              partnerPlaceDetails.clear();
              partnerPlaceDetails.setPartnerPlaces(userDetails.getUser(), partnerPlacesResponse);
              localStorageService.set(partnerPlaceStorageKey, data.selectedPartnerPlace.id);
            } else {
              partnerPlaceDetails.clear();
              $rootScope.hideLoader();
            }
            $rootScope.showLoader('Loading...');
            $state.go('index.secured.dashboard', {}, {reload:true}).finally(function() {
              $rootScope.hideLoader();
            });
          });
        })

      };

      $scope.user = userDetails.getUser();

      $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, rejection) {
        if (angular.isDefined(toState.data) && angular.isDefined(toState.data.screenName))
          $scope.screenName = toState.data.screenName;
      });

      $scope.logout = function() {
        $state.go('login', {
          action: 'logout'
        });

      };

       var envVar = '/* @echo NODE_ENV */';
       var visitorRole = "";

       if ($scope.user.isSuperAdmin) {
          visitorRole = "SA";
       } else if ($scope.user.partnerRoles[0].role.title == "Partner Admin"){
         visitorRole = "CA";
       } else if ($scope.user.partnerRoles[0].role.title == "General User"){
         visitorRole = "GU";
       } else if ($scope.user.partnerRoles[0].role.title == "SME User"){
         visitorRole = "SME";
       }

  	   var options = {
  	     visitor: {
  	       id: envVar.charAt(0).toUpperCase() + "_" + $scope.user.id,
           name: $scope.user.firstName + $scope.user.lastName.substring(0, 1),
           role: visitorRole,
           c_date: $scope.user.createdDate
  	     },
  	     account: {
  	       id: envVar.charAt(0).toUpperCase() + "_" + $scope.partnerDetails.getSelectedPartner().id,
           name: $scope.partnerDetails.getSelectedPartner().shortName,
           planLevel: "SL" + $scope.partnerDetails.getSelectedPartner().subLevel,
           c_date: $scope.partnerDetails.getSelectedPartner().createdDate
  	     }
  	   };
    }
  ]);

})(angular.module('aet.screens.index'));

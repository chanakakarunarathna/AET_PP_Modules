(function(module) {

  module.controller('EditPartnerPropertyController', ['$scope', 'partnerPropertyService', 'partnerProperty', 'alertsService', '$state', '$log', 'taOptions', '$rootScope', '$window', 'adminUserService', 'userDetails',
    function($scope, partnerPropertyService, partnerProperty, alertsService, $state, $log, taOptions, $rootScope, $window, adminUserService, userDetails) {

      $scope.stripFormat = function(text) {
        return text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

      $scope.partnerProperty = partnerProperty;

      $scope.latRegex = /^[-]?([1-8]?\d(\.\d+)?|90(\.0+)?)$/;
      $scope.longRegex = /^[-]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
      
      $scope.submitPartnerPropertyForm = function(params) {
        $rootScope.loader = true;
        partnerPropertyService.editPartnerProperty(partnerProperty).then(function(data) {
            alertsService.addAlert({
              title: 'Success',
              message: 'Partner Property "' + $scope.partnerProperty.propertyCode + '" successfully updated',
              type: 'success',
              removeOnStateChange: 2
            });
            $rootScope.loader = false;
            $state.go('index.secured.partnerProperty.search', {}, {
              reload: true
            }).then(function() {
              adminUserService.findSelfAdminUser(userDetails.getUserId()).then(function(adminUser) {
                userDetails.setUser(adminUser);

                if (adminUser.isSuperAdmin) {
                  partnerPropertyService.searchPartnerProperties({
                    count: 0
                  }).then(function(partnerProperty) {
                  }, function(err) {
                    $log.error(err);
                  });
                }
              });
            });
          },
          function(err) {
            $rootScope.loader = false;
            console.error('Could not edit partnerProperty', err);
          });

      };

      	$scope.preventPastingCharactersForLatLong = function(element, e){
			var val = e.originalEvent.clipboardData.getData('text/plain');
			if(element=='lat'){
				if (!($scope.latRegex.test(val))){
					e.preventDefault();
				} 
			}else{
				if (!($scope.longRegex.test(val))){
			        e.preventDefault();
				} 
			}
    	};
    }
  ]);

})(angular.module('aet.screens.partnerProperty'));

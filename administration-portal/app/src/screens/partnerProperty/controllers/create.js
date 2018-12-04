(function(module) {

  module.controller('CreatePartnerPropertyController', ['$scope', 'partnerPropertyService', 'PartnerPlace', 'alertsService', '$state', 'taOptions','$rootScope',
    function($scope, partnerPropertyService, PartnerPlace, alertsService, $state, taOptions,$rootScope) {

      $scope.partnerProperty = new PartnerPlace();

      $scope.latRegex = /^[-]?([1-8]?\d(\.\d+)?|90(\.0+)?)$/;
      $scope.longRegex = /^[-]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
      
      $scope.stripFormat = function (text) {
        return  text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

      $scope.submitPartnerPropertyForm = function() {
        $rootScope.loader = true;
        partnerPropertyService.createPartnerProperty($scope.partnerProperty).then(function (data) {
          alertsService.addAlert({
            title: 'Success',
            message: 'Partner Property "' + $scope.partnerProperty.propertyCode + '" successfully created',
            type: 'success',
            removeOnStateChange: 2
          });
          $rootScope.loader = false;
          $state.go('index.secured.partnerProperty.search', {}, {
            reload: true
          });
        }, function (err) {
          $rootScope.loader = false;
          console.error('Could not create partnerProperty', err);
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

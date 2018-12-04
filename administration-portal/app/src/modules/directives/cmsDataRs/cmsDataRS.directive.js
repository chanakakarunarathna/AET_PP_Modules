(function(module) {

  module.directive('cmsDataRs', ['$log', function($log) {
	var controller = ['$scope', function ($scope) {
		$scope.preventPastingCharacters = function(e){
			var val = e.originalEvent.clipboardData.getData('text/plain');
			if (!(/^\d+$/.test(val))){
				e.preventDefault();
			}
		};
	}];
    return {
      restrict: 'E',
      scope: {
        hero : "=ngModel"
      },
	  controller: controller,
      link: function(scope, element, attr) {
      },
      templateUrl: 'src/modules/directives/cmsDataRs/templates/cms-data.html',
    };
  }]);

})(angular.module('aet.directives.cmsDataRs'));
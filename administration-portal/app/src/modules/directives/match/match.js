(function(module) {
    
	module.directive('aetMatch', [function () {
		return {
			restrict: 'A',
			require: 'ngModel',
			link: function (scope, element, attrs, ctrl) {
				//console.log(attrs.aetMatch)
				scope.$watch(function () {
					return [scope.$eval(attrs.aetMatch), ctrl.$viewValue];
				}, function (values) {
					ctrl.$setValidity('match', values[0] === values[1]);
				}, true);
			}
		}
	}]);
    
})(angular.module('aet.directives.match'));
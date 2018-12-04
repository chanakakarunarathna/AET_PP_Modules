(function(module) {
    
	module.directive('aetNavbarNotifications', ['$log', 'alertsService', '$animate', function ($log, alertsService, $animate) {
		return {
			templateUrl: 'src/screens/secured/directives/navbarNotifications/navbarNotifications.html',
			replace: true,
			scope: {
				limit: '@'
			},
			link: function(scope, element, attrs) {
				
				var originalLimit = scope.limit;
				scope.alertsService = alertsService;
				
				scope.remaining = function() {
					var count = alertsService.getAlertsHistory().length;
					return count > scope.limit ? count - scope.limit : 0;
				};
				
				scope.clearAlertsHistory = function() {
					scope.alertsService.clearHistory();
				};
				
				scope.$watchCollection(function() {return alertsService.getAlertsHistory();}, function(nv, ov) {
					if(nv.length > ov.length) {
						$animate.addClass(element, 'added').then(function() {
							$animate.removeClass(element, 'added');
						})
					}
				});
				
				scope.toggled = function(open) {
					if(!open) {
						scope.limit = originalLimit;
					}
				};
				
				scope.showAll = function() {
//					scope.limit = 1;
					scope.limit = alertsService.getAlertsHistory().length;
				};
			}
		}
	}]);
    
})(angular.module('aet.screens.secured'));
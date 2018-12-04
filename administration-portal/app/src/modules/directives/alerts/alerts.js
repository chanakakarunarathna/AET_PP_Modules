(function(module) {

  module.directive('aetAlerts', ['$log', 'alertsService', '$timeout', function($log, alertsService, $timeout) {
    return {
      templateUrl: 'src/modules/directives/alerts/alerts.html',
      link: function(scope, element, attrs) {

        scope.alertsService = alertsService;

        scope.stopTimeout = function(alert) {
          $timeout.cancel(alert.timeout);
        };

        scope.startTimeout = function(alert) {
          alertsService.resetTimeout(alert);
        };
      }
    }
  }]);

})(angular.module('aet.directives.alerts'));

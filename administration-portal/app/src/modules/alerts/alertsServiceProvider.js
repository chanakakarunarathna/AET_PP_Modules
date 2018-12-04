/*
 alertsService.addAlert({
 title: 'info',
 message: 'this is the message',
 type: 'info'
 })

 alertsService.addAlert({
 title: 'message',
 message: 'this is the message this is the message this is the message this is the message this is the message this is the message this is the message this is the message this is the message this is the message this is the message this is the message',
 type: 'message' // default type
 removeOnStateChange: 1, // default removeOnStateChange count
 })

 alertsService.addAlert({
 title: 'error',
 message: 'this is the message',
 type: 'error',
 removeOnStateChange: 2, // won't be removed until you navigate to two different routes.
 // so, if you go from the landing page to sign up, this won't disappear. If you could then
 // go to a different screen, it would disappear. You can't test this on landing again because
 // all of the alerts will be generated again in the landing controller.

 })

 alertsService.addAlert({
 title: 'success',
 message: 'this is the message',
 type: 'success',
 removeOnStateChange: false, // never removed until the user dismisses it.
 // we should really be careful using this one because the alert bar has
 // not been thoroughly tested on different devices, which means it could
 // potentially get in the way of mobile users.
 })
 */


(function (module) {

  /**
   * @ngdoc object
   * @name aet.alerts.alertsServiceProvider
   *
   * @description The provider used to configure various alerts properties
   *
   *
   *
   */
  module.provider('alertsService', [function () {

    var _alertTimeout = 4000;
    var _localStorageName = 'alerts';
    var _maxStorage = 20;
    var modal = document.getElementsByClassName("modal-dialog");

    return {

      setAlertTimeout: function (alertTimeout) {
        _alertTimeout = alertTimeout;
      },

      setLocalStorageName: function (localStorageName) {
        _localStorageName = localStorageName;
      },

      setMaxStorage: function (maxStorage) {
        _maxStorage = maxStorage;
      },

      /**
       * @ngdoc object
       * @name aet.alerts.alertsService
       *
       * @description A service used to display alerts in the application
       *
       *
       *
       */
      $get: ['$rootScope', '$location', '$anchorScroll', 'localStorageService', '$timeout',
        function ($rootScope, $location, $anchorScroll, localStorageService, $timeout) {

          var alerts = [];
          var alertsHistory = [];

          if (localStorageService.get(_localStorageName) === null) {
            localStorageService.set(_localStorageName, alertsHistory);
          }
          else {
            alertsHistory = localStorageService.get(_localStorageName);
          }

          $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            _.remove(alerts, function (alert) {
              if (alert.removeOnStateChange !== false)
                return --alert.removeOnStateChange < 1;
            });
          });

          return {

            addAlert: function (alert) {
              if(modal.length > 0 ){
                modal[0].style.top = '60px';
              }
              var defaults = {
                title: undefined,
                message: undefined,
                removeOnStateChange: 1,
                type: 'message',
                important: false,
                data: undefined
              };

              alert = _.extend({}, defaults, alert);

              alert.timeout = $timeout(this.removeAlert.bind(null, alert), _alertTimeout);

              alerts.push(alert);
            },

            addECSErrorMessage: function (response) {
              if (response.headers('pp-message')) {
                this.addAlert({
                  title: response.statusText + ': ' + response.status,
                  message: response.headers('pp-message'),
                  type: 'error'
                });
              }
            },

            removeAlert: function (alert) {
              $timeout.cancel(alert.timeout);

              _.remove(alerts, function (al) {
                if(modal.length > 0 ){
                  $timeout(function() {
                    modal[0].style.top = 'inherit';
                  }, 300);
                }
                return alert === al;
              });

              alert.message += alert.message + alert.message + alert.message + alert.message;

              if (alertsHistory.length >= _maxStorage) {
                alertsHistory.pop();
              }

              alertsHistory.unshift(alert);

              localStorageService.set(_localStorageName, alertsHistory);
            },

            resetTimeout: function (alert) {
              alert.timeout = $timeout(this.removeAlert.bind(null, alert), _alertTimeout);
            },

            clear: function () {
              alerts = [];
            },

            clearHistory: function () {
              alertsHistory = [];
              localStorageService.set(_localStorageName, alertsHistory);
            },

            getAlerts: function () {
              return alerts;
            },

            getAlertsHistory: function () {
              return alertsHistory;
            }
          }
        }
      ]

    };
  }])

})(angular.module('aet.alerts'));
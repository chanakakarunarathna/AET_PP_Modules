(function() {

  /**
   * @ngdoc overview
   * @name aet.config
   *
   * @description
   * This module is responsible for providing all configuration for the applicaiton. This usually involves initialization of
   * providers used throughout the application. We also use the run block of this module to initialize some development
   * logging.
   *
   */

  var appConfig = angular.module('aet.config', [
    'aet.directives.navTab',
    'aet.security'
  ]);

  //new environment settings
  var env = {};

  // Import variables if present (from env.js)
  if(window){
    //Object.assign(env, window.__env);
    angular.extend(env, window.__env);
  }

  // Register environment in AngularJS as constant
  appConfig.constant('__env', env);

  appConfig.config(['$httpProvider', 'apiProvider', 'securityProvider', 'navTabServiceProvider', '$urlRouterProvider', 'localStorageServiceProvider', 'ChartJsProvider', '__env',
    function($httpProvider, apiProvider, securityProvider, navTabServiceProvider, $urlRouterProvider, localStorageServiceProvider, ChartJsProvider, __env) {

      // initialize apis
      var apiBaseUrl = __env.apiBaseUrl || 'http://localhost:8080';
      apiProvider.addApi('admin', apiBaseUrl);

      // initialize security settings
      securityProvider.setAuthenticationEnabled(true);
      securityProvider.setAuthorizationEnabled(true);
      securityProvider.setAuthCookieName('com.placepass.authcookie');
      securityProvider.setLoginState('login');
      securityProvider.setDefaultState('index.secured.dashboard');
      securityProvider.setUnauthorizedState('index.secured.unauthorized');
      securityProvider.setLoginUrl("/login");
      securityProvider.setLogoutUrl("/logout");

      // nav tab class
      navTabServiceProvider.setTabClass('active');

      // default route
      $urlRouterProvider.otherwise('/dashboard');
      $urlRouterProvider.when('/mapview', '/map/view');

      // local storage config
      localStorageServiceProvider.setPrefix('aet.adminportal');

      $httpProvider.defaults.withCredentials = true;
      $httpProvider.interceptors.push('csrf');

      angular.merge = function(s1, s2) {
        return $.extend(true, s1, s2);
      };

      ChartJsProvider.setOptions({
        chartColors: [{
          backgroundColor: "rgba(150,161,161, 1)",
          pointBackgroundColor: "rgba(150,161,161, 1)",
          pointHoverBackgroundColor: "rgba(150,161,161, 1)",
          borderColor: "rgba(150,161,161, 1)",
          pointBorderColor: '#fff',
          pointHoverBorderColor: "rgba(150,161,161, 1)"
        }, {
          backgroundColor: "rgba(134,97,168, 1)",
          pointBackgroundColor: "rgba(134,97,168, 1)",
          pointHoverBackgroundColor: "rgba(134,97,168, 1)",
          borderColor: "rgba(134,97,168, 1)",
          pointBorderColor: '#fff',
          pointHoverBorderColor: "rgba(134,97,168, 1)"
        }, {
          backgroundColor: "rgba(52,152,219, 1)",
          pointBackgroundColor: "rgba(52,152,219, 1)",
          pointHoverBackgroundColor: "rgba(52,152,219, 1)",
          borderColor: "rgba(52,152,219, 1)",
          pointBorderColor: '#fff',
          pointHoverBorderColor: "rgba(52,152,219, 1)"
        }, {
          backgroundColor: "rgba(164,221,122, 1)",
          pointBackgroundColor: "rgba(164,221,122, 1)",
          pointHoverBackgroundColor: "rgba(164,221,122, 1)",
          borderColor: "rgba(164,221,122, 1)",
          pointBorderColor: '#fff',
          pointHoverBorderColor: "rgba(164,221,122, 1)"
        }, {
          backgroundColor: "rgba(52,73,94, 1)",
          pointBackgroundColor: "rgba(52,73,94, 1)",
          pointHoverBackgroundColor: "rgba(52,73,94, 1)",
          borderColor: "rgba(52,73,94, 1)",
          pointBorderColor: '#fff',
          pointHoverBorderColor: "rgba(52,73,94, 1)"
        }]
      });

      /*ChartJsProvider.setOptions({
          colors: ['#FDB45C', '#FDB45C', '#FDB45C', '#FDB45C', '#FDB45C', '#FDB45C', '#FDB45C']
      });*/

      //            if(navigator.appName == 'Microsoft Internet Explorer' || navigator.appName == 'Netscape') {
      //
      //                $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache, no-store, must-revalidate';
      //                $httpProvider.defaults.headers.common['Pragma'] = 'no-cache';
      //                $httpProvider.defaults.headers.common['Expires'] = '0';
      //
      //            }

    }
  ]);

  appConfig.run(['$rootScope', '$templateCache', 'localStorageService', 'partnerPlaceDetails', '$state', '$log', 'modalService', 'userDetails', 'partnerDetails', function($rootScope, $templateCache, localStorageService, partnerPlaceDetails, $state, $log, modalService, userDetails, partnerDetails) {

    var pageTransitionStart;

    $templateCache.put("bootstrap/match.tpl.html", "<div class=\"ui-select-match\" ng-hide=\"$select.open\" ng-disabled=\"$select.disabled\" ng-class=\"{\'btn-default-focus\':$select.focus}\"><span tabindex=\"-1\" class=\"btn btn-default form-control ui-select-toggle\" aria-label=\"{{ $select.baseTitle }} activate\" ng-disabled=\"$select.disabled\" ng-click=\"$select.activate()\" style=\"outline: 0;\"><span ng-show=\"$select.isEmpty()\" class=\"ui-select-placeholder text-muted\">{{$select.placeholder}}</span> <span ng-hide=\"$select.isEmpty()\" class=\"ui-select-match-text pull-left\" ng-class=\"{\'ui-select-allow-clear\': $select.allowClear && !$select.isEmpty()}\" ng-transclude=\"\"></span> <i class=\"icon icon-placepass-icons-pp-caret-down pull-right\" ng-click=\"$select.toggle($event)\"></i> <a ng-show=\"$select.allowClear && !$select.isEmpty()\" aria-label=\"{{ $select.baseTitle }} clear\" style=\"margin-right: 10px\" ng-click=\"$select.clear($event)\" class=\"btn btn-xs btn-link pull-right\"><i class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\"></i></a></span></div>");

    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState) {
      pageTransitionStart = +new Date();
    });

    //		$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, rejection) {
    //
    //            //console.log('State change to: ', toState, " - from: ", fromState);
    //
    //
    //			setTimeout(function () {
    //				(function () {
    //					var watchers, elementsWithScope, scope, i, _len;
    //					watchers = 0;
    //					elementsWithScope = document.querySelectorAll('.ng-scope');
    //					for (i = 0, _len = elementsWithScope.length; i < _len; i++) {
    //						scope = angular.element(elementsWithScope[i]).scope();
    //						if (scope.$$watchers != null) {
    //							watchers += scope.$$watchers.length;
    //						}
    //					}
    //
    //					var severity = 'info';
    //					if (watchers > 1000) {
    //						severity = 'warn';
    //					}
    //					if (watchers > 1500) {
    //						severity = 'error';
    //					}
    //					var transitionTime = (new Date()) - pageTransitionStart;
    //					console[severity]('transitionTime:', transitionTime, 'Watchers ', watchers);
    //				})();
    //			}, 1);
    //
    //
    //        });

    $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, rejection) {

      $log.error('State change error', rejection);
      $rootScope.hideLoader();

    });

    $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams, rejection) {

      $log.info('State change success', toState);
      $rootScope.hideLoader();

    });

    angular.isUndefinedOrNull = function(val) {
      return angular.isUndefined(val) || val === null || val === "";
    };

    angular.parseJSONpath = function(obj, path) {
      if (path) {
        var l = path.split('.');
        angular.forEach(l, function(o, k) {
          obj = obj[o];
        });
      }
      return obj;
    };

    $rootScope.showLoader = function(label) {
      $rootScope.loaderText = label;
      $rootScope.loader = true;
    };
    $rootScope.hideLoader = function() {
      $rootScope.loaderText = undefined;
      $rootScope.loader = false;
    };

    var sidebarStatusKey = 'isSidebarMinimized';

    $rootScope.isSidebarMinimized = angular.isDefined(localStorageService.get(sidebarStatusKey)) ? localStorageService.get(sidebarStatusKey) === "true" : false;

    $rootScope.toggleSidebar = function() {
      $rootScope.isSidebarMinimized = ($rootScope.isSidebarMinimized === true) ? false : true;
      localStorageService.set(sidebarStatusKey, $rootScope.isSidebarMinimized);
    };

    var EmailValidator = CommonsValidator.EmailValidator;
    var validator = new EmailValidator();
    $rootScope.emailValidationPattern = (function() {
      return {
        test: function(value) {
          return validator.isValid(value);
        }
      };
    })();
  }]);

})();

(function(module) {

  module.controller('LoginController', ['$scope', '$rootScope', 'loginService', 'modalService', 'LoginRequestDTO', '$log', '$state', '$stateParams','alertsService', 'localStorageService', 'bookingService', 'security',
    function($scope, $rootScope,loginService, modalService, LoginRequestDTO, $log, $state,$stateParams, alertsService, localStorageService, bookingService, security ) {

      $rootScope.loginState = function() {
        return $state.current.name == 'login';
      };

      $scope.loginRequestDTO = new LoginRequestDTO();
      $scope.loginRequestDTO.rememberme = false;
      $scope.alerts = [];

      $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
      };

      $scope.updateRememberme = function() {
        var localValue = localStorageService.get($scope.loginRequestDTO.username + ".rememberme") === "true";
        $scope.loginRequestDTO.rememberme = localValue || false;

      };

      $scope.submitLogin = function() {
        console.log('Logging in...');
        $rootScope.showLoader('Logging in...');
        loginService.login($scope.loginRequestDTO).then(function(response) {
          console.log('Logging in... Done');
          $scope.hideLoader();
          $log.debug("Login successful", response);
          console.log('Login successful', response);
          var addModalInstance = modalService.partnerPlaceNotification();

          if(security.isAuthorized('GET_VENDOR_LIST') === true){
	          bookingService.getVendorList().then(function(response) {
	    		  sessionStorage.setItem('vendorListOptions', JSON.stringify(response));
	          });
          }
          /*addModalInstance.result.then(function() {
             $rootScope.showLoader('Please Wait...');
             $state.go('index.secured.partnerPlace', {}, {
              reload: true
             }).finally(function() {
              $rootScope.hideLoader();
             });
          });*/

          // addModalInstance.result.then(function() {
          //
          // }, function() {
          //   $state.go('index.secured.map.view', {}, {
          //     reload: true
          //   }).finally(function() {
          //     $rootScope.hideLoader();
          //   });
          //   $rootScope.showLoader('Please Wait...');
          // });
        }, function(error) {
          console.log('Could not log in');
          $scope.hideLoader();
          $log.error("Could not log in", error);
          $scope.alerts = [];
          $scope.alerts.push({
            type: 'danger',
            msg: "Username and password don't match"
          });
        });
      };

      if ($stateParams.action && $stateParams.action === 'logout') {
        loginService.logout();
        sessionStorage.vendorListOptions = null;
        for (key in localStorage) {
    	  if (key.substring(0,7) == 'userId') {
    	    localStorage.removeItem(key);
    	  }
    	}
        $state.go('login', { action : undefined });
      }


    }
  ]);

})(angular.module('aet.screens.login'));

(function (module) {

  module.controller('ResetPasswordController', ['$scope','$rootScope', 'adminUserService', '$state', 'ResetPasswordRequestDTO',
    function ($scope,$rootScope, adminUserService, $state, ResetPasswordRequestDTO) {
      $scope.alerts = [];
      //$scope.loader = false;

      $rootScope.loginState = function(){
          return $state.current.name == 'resetPassword';
      };

      $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
      };

      $scope.resetPasswordRequestDTO = new ResetPasswordRequestDTO();
      $scope.resetPassword = function () {
        $rootScope.showLoader('Please Wait...');
        adminUserService.forgotPassword($scope.resetPasswordRequestDTO).then(function (data) {
          $rootScope.hideLoader();
          $state.go('resetPasswordSuccess');
        }, function (err) {
          //console.log("Error return to controller");
          $scope.alerts = [];
          $scope.alerts.push({
            type: 'info',
            msg: "It looks like there isn't a Place Pass account associated with this email address. Need help? Email <a href='mailto:support@placepass.com'>support@placepass.com</a>."
          });
          $rootScope.hideLoader();
        });
      }

    }
  ]);

})(angular.module('aet.screens.login'));

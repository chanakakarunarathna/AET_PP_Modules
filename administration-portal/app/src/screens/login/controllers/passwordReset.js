(function(module) {

    module.controller('PasswordResetController', ['$scope', '$rootScope', 'adminUserService', 'alertsService', '$state', 'ResetPasswordRequestDTO', 'ResetPasswordDTO', 'passwordReset',
        function($scope, $rootScope, adminUserService, alertsService, $state, ResetPasswordRequestDTO, ResetPasswordDTO, passwordReset) {

            $scope.resetPasswordDTO = new ResetPasswordDTO();
            $scope.resetPasswordDTO = passwordReset;

            $scope.resetPasswordRequestDTO = new ResetPasswordRequestDTO();

            $rootScope.loginState = function() {
                return $state.current.name == 'passwordReset';
            };

            $scope.passwordReset = function() {
                adminUserService.passwordReset($scope.resetPasswordDTO).then(function(data) {
                    $state.go('login');
                }, function(err) {

                    $state.go('resetPasswordFail');
                });
            }

        }
    ]);

})(angular.module('aet.screens.login'));

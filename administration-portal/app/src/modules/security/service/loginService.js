(function(module) {

    module.service('loginService', ['security', 'userDetails', 'loginEndpoint', '$q', 'localStorageService', 'adminUserTransformer', '$log', '$state',
        function(security, userDetails, loginEndpoint, $q, localStorageService, adminUserTransformer, $log, $state) {

            this.login = function (loginDTO) {
                var self = this;

                security.clearAuthentication();

                return loginEndpoint.login(loginDTO).then(function(response) {
					var admin = adminUserTransformer.loginDTOToAdminUser(response.data);
					userDetails.setUser(admin);
					security.setIsLoggedIn(true);
					localStorageService.set(loginDTO.username + ".rememberme", loginDTO.rememberme);

                    return security.updateSecurityDetails();

                }, function(err) {
                	$log.error(err);
                    return $q.reject(err);
                });
            };

            this.logout = function() {

                return loginEndpoint.logout().then(function(response) {
                    security.clearAuthentication();
                    return response;
                }, function(err) {
                    $log.debug('Maybe an error, maybe not', err);
                    return $q.reject(err);
                }).finally(function() {
					security.setIsLoggedIn(false);
                    $state.go(security.getLoginState());
                });
            };

            this.resetPassword = function (userName) {

                return loginEndpoint.resetPassword(userName).then(function(response) {

                    $log.debug("SUCCESS RESET PASSWORD" + response.data);

                    return response.data;

                }, function(err) {
                	$log.error(err);
                    return $q.reject(err);
                });
            };

            this.passwordReset = function (userName) {

                return loginEndpoint.passwordReset(userName).then(function(response) {

                    $log.debug("SUCCESS PASSWORD RESET" + response.data);

                    return response.data;

                }, function(err) {

                	$log.error(err);
                    return $q.reject(err);
                });
            };
        }
    ]);

})(angular.module('aet.security'));

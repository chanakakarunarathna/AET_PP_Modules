(function(module) {
    
    module.service('loginEndpoint', ['$http', 'api', 'security',
        function($http, api, security) {

            this.login = function (loginRequest) {

                var loginUserURL = api('admin') + security.getLoginUrl();

                return $http.post(loginUserURL, loginRequest);
            };

            this.logout = function () {
                var logoutUserURL = api('admin') + security.getLogoutUrl();
                return $http.get(logoutUserURL);
            };
            
            this.resetPassword = function (userName) {

                var resetPasswordUserURL = api('admin') + '/admin/resetpassword/request';

                return $http.put(resetPasswordUserURL, userName);
            };
            
            this.passwordReset = function (emailAddress) {
            	
                var resetPasswordUserURL = api('admin') + '/admin/resetpassword';

                return $http.put(resetPasswordUserURL, emailAddress);
            };
        }
    ]);

})(angular.module('aet.security'));
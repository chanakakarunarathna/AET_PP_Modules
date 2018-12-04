(function(module) {
    
    module.service('loginEndpoint', ['$http', 'api',
        function($http, api) {

            this.login = function (loginRequest) {

                var loginUserURL = api('admin') + '/login';

                return $http.post(loginUserURL, loginRequest);
            };

            this.logout = function () {
                var logoutUserURL = api('admin') + '/logout';
                return $http.get(logoutUserURL);
            };
        }
    ]);

})(angular.module('aet.endpoints'));
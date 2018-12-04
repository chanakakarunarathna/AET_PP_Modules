(function(module) {
    
    module.service('LoginRequestDTO', [function() {

        function LoginRequestDTO() {
        	this.username = undefined;
			this.password = undefined;
			this.rememberme = undefined;
        }

        return LoginRequestDTO;

    }]);

})(angular.module('aet.domain.login'));
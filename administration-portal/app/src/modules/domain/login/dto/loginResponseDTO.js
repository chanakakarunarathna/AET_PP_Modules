(function(module) {
    
    module.service('LoginResponseDTO', [function() {

        function LoginResponseDTO() {
        	this.accountId = undefined;
        	this.firstName = undefined;
        	this.lastName = undefined;
        	this.createdDate = undefined;
        	this.partnerId = undefined;
        	this.merchantId = undefined;
        }

        return LoginResponseDTO;

    }]);

})(angular.module('aet.domain.login'));
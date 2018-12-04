(function(module) {

  module.service('ResendEmailRequestDTO', [function() {

    function ResendEmailRequestDTO() {
    	
    	this.emailType = undefined;
    	this.receiverEmail = undefined;
    	
    }

    return ResendEmailRequestDTO;

  }]);

})(angular.module('aet.domain.booking'));

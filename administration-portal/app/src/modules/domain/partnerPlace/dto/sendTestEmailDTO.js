(function(module) {

  module.service('SendTestEmailDTO', [function() {

    function SendTestEmailDTO() {

      this.receiverId = undefined;
      this.senderName = undefined;
      this.senderEmail = undefined;
      this.host = undefined;
      this.username = undefined;
      this.password = undefined;
      this.port = undefined;
      this.secureProtocol = undefined;
      this.emailGateway = undefined;
      this.replyToEmail = undefined;

    }

    return SendTestEmailDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

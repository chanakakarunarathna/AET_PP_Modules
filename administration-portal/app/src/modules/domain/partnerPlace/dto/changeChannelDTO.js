(function(module) {

  module.service('ChangeChannelDTO', [function() {

    function ChangeChannelDTO() {

      this.channelId = undefined;
      this.title = undefined;

    }

    return ChangeChannelDTO;

  }]);

})(angular.module('aet.domain.partnerPlace'));

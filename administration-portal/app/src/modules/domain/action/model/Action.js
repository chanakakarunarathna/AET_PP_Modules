(function(module) {

  module.service('MissionAction', [function() {

    function MissionAction() {

      this.actionId = undefined;
      this.actionTitle=undefined;
      this.actionHeader = undefined;
      this.isMediaIncluded = undefined;
      this.mediaIncludedMessage=undefined;

    }

    return MissionAction;

  }]);

})(angular.module('aet.domain.action'));

(function(module) {

  module.service('NavigationConfig', [function() {

    function NavigationConfig() {
      this.title = undefined;
      this.url = undefined;
      this.clickFnc = undefined;
      this.validationFnc = undefined;
      this.disableArg = undefined;
    }

    return NavigationConfig;

  }]);

})(angular.module('aet.navigation'));

(function (module) {
  module.service('MissionPreviewNavigationObject', [function () {

    function NavigationObject(){

      this.isValidFunc = undefined;
      this.isVisitedTab = undefined;
      this.isVisitedFunc = undefined;
    }

    return NavigationObject;
  }])

})(angular.module('aet.navigation'));

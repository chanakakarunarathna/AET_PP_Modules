(function (module) {

  module.provider('navTabService', [function () {

    var selectedTab;
    var selectedTabId;
    var tabs = {};
    var tabClass;

    this.setTabClass = function (elClass) {
      tabClass = elClass;
    };

    this.$get = ['$rootScope', '$state', function ($rootScope, $state) {

      var self = this;

      this.registerTab = function (id, el) {
        tabs[id] = el;
        if (selectedTabId === id)
          this.selectTab(id);
      };

      this.selectTab = function (id) {
        if (angular.isDefined(selectedTab))
          selectedTab.removeClass(tabClass);

        selectedTabId = id;

        var newTab = tabs[id];
        if (angular.isDefined(newTab))
          newTab.addClass(tabClass);

        selectedTab = newTab;

      };

      return this;
    }];

    return this;

  }]);

  module.run(['$rootScope', '$state', 'navTabService', function ($rootScope, $state, navTabService) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      if (angular.isDefined(toState.data) && angular.isDefined(toState.data.selectedTab))
        navTabService.selectTab(toState.data.selectedTab);
    });
  }]);

})(angular.module('aet.directives.navTab'));

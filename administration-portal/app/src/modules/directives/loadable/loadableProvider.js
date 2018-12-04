(function(module) {
    
    module.provider('loadableService', [function() {

        var selectedTab;
        var tabs = {};
        var tabClass;

        this.setTabClass = function(elClass) {
            tabClass = elClass;
        };

        this.$get = ['$rootScope', '$state', function($rootScope, $state) {
            
            var self = this;
            
            $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
                if(angular.isDefined(toState.data) && angular.isDefined(toState.data.selectedTab))
                    self.selectTab(toState.data.selectedTab);
            });

            this.registerTab = function(id, el) {
                tabs[id] = el;
            };

            this.selectTab = function(id) {
                if(angular.isDefined(selectedTab))
                    selectedTab.removeClass(tabClass);

                var newTab = tabs[id];
                if(angular.isDefined(newTab))
                    newTab.addClass(tabClass);

                selectedTab = newTab;
            };

            return this;
        }];

        return this;

    }]);
    
})(angular.module('aet-directives-loadable'));

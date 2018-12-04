(function(module) {

    module.service('NavigationService', ['$q', 'NavigationConfig',
        function($q, NavigationConfig) {

            var step = 0;
            var tabs = [];
            var self = this;

            this.newInstance = function() {
                step = 0;
                tabs = [];
                return this;
            };

            this.registerTab = function(title, url, clickFnc, validationFnc, disableArg) {
                var navigationConfig = new NavigationConfig();
                navigationConfig.title = title;
                navigationConfig.url = url;
                navigationConfig.clickFnc = clickFnc;
                navigationConfig.validationFnc = validationFnc;
                navigationConfig.disableArg = disableArg;
                navigationConfig.visited = false;
                navigationConfig.show = true;
                tabs.push(navigationConfig);
                return this;
            };

            this.getCurrentTab = function() {
                return tabs[step];
            };

            this.setTab = function(id, tab) {
                tabs[id] = tab;
            };

            this.getTab = function(id) {
                return tabs[id];
            };

            this.getTabs = function() {
                return tabs;
            };

            this.setStep = function(nStep) {
                tabs[nStep].visited = true;
                step = nStep;
            };

            this.getStep = function() {
                return step;
            };

            this.nextStep = function() {
                step++;
                //If tab is hidden load next step
                if (tabs[step].show) {
                    tabs[step].visited = true;
                } else {
                    self.nextStep();
                }
            };

            this.previousStep = function() {
                step--;
                if (tabs[step].show) {
                    tabs[step].visited = true;
                } else {
                    self.previousStep();
                }
            };

            this.hideTab = function(id) {
                tabs[id].show = false;
            }

            this.showTab = function(id) {
                tabs[id].show = true;
            }

            this.getVisibleTabs = function() {
                var count = 0;
                angular.forEach(tabs, function(tab) {
                    count += tab.show ? 1 : 0;
                });
                return count;
            };


        }
    ]);

})(angular.module('aet.navigation'));

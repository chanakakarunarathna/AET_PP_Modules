(function(module) {
    
module.directive('aetNavTab', ['navTabService', function (navTabService) {
    return {
        link: function(scope, element, attrs) {
            navTabService.registerTab(attrs.aetNavTab, element);
        }
    }
}]);
    
})(angular.module('aet.directives.navTab'));
(function(module) {
    
module.directive('aetLoadable', ['loadableService', function (loadableService) {
    return {
        link: function(scope, element, attrs) {
            navTabService.registerTab(attrs.id, element);
        }
    }
}]);
    
})(angular.module('aet-directives-loadable'));
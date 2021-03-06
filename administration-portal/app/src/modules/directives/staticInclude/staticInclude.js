/**
 * Created by naveen.w
 */
(function(module) {

    module.directive('staticInclude', function($http, $templateCache, $compile) {
        return function(scope, element, attrs) {
            var templatePath = attrs.staticInclude;
            $http.get(templatePath, {
                cache: $templateCache
            }).success(function(response) {
                var contents = element.html(response).contents();
                $compile(contents)(scope);
            });
        };
    });

})(angular.module('aet-directives-staticinclude'));

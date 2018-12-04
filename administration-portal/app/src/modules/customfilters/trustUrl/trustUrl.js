(function(module) {

    module.filter('trustUrl', ['$sce', function($sce) {
        return function(url) { return $sce.trustAsResourceUrl(url); };
    }]);

})(angular.module('aet.customfilters.trustUrl'));

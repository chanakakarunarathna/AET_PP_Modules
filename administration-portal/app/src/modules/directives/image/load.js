/**
 * Created by naveen.w
 */
(function(module) {

  module.directive('imageonload', ['$parse', '$timeout', function($parse, $timeout) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
              var fn = $parse(attrs.imageonload);
              element.on('load', function (event) {
                $timeout(function() {
                  fn(scope, { $event: event });
                });
              });
            }
        };
    }])
})(angular.module('aet-directives-image'));

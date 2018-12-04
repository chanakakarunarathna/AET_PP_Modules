/**
 * Created by supun.s
 */
(function(module) {

  module.directive('uiCustomWidth', ['$timeout',function($timeout) {
    return {
      restrict: 'A',
      link: function(scope, element, attr) {
        var input, inputOuterWidth, newWidth;

        $timeout(function(){
          input = element.find('.input-xs');
          inputOuterWidth = input.outerWidth();
          newWidth = inputOuterWidth - 20 + 'px';
          input.width(newWidth);
        }, 200);
      }
    };
  }])
})(angular.module('aet-directives-ui-custom-width'));

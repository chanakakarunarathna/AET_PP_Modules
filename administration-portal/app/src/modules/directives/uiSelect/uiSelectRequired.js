/**
 * Created by naveen.w
 */
(function(module) {

  module.directive('uiSelectRequired', function() {
    return {
      restrict: 'A',
      require: ['^form', 'ngModel'],
      link: function($scope, $element, $attrs, ctrls) {

        $scope.$watch($attrs.ngModel, function(value) {
          var isEmpty = (!value || (value && value.length == 0));
          ctrls[1].$setValidity("empty", !isEmpty);
          if (isEmpty && angular.element($element[0]).hasClass('ng-touched')) {
            ctrls[1].$showError = true;
          }
        });
      }
    };
  })
})(angular.module('aet-directives-uiselect'));

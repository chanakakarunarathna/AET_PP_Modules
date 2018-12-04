(function(module) {

  module.directive('modalWindow', function(){
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      template:
      "<div>" +
      "<div class='modal-header'>" +
      "<h3 ng-bind='dialogTitle'></h3>" +
      "<div ng-click='cancel()'>X</div>" +
      "</div>" +
      "<div class='modal-body' ng-transclude></div>" +
      "</div>"
    };
  })


})(angular.module('aet.directives.modalWindow'));
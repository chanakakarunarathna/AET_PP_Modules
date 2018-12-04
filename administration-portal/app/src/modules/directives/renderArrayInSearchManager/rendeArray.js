(function(module) {

  module.directive('renderArray', ['$log', function($log) {
    return {
      restrict: 'E',
      scope : {
        array : '='
      },
      controller : function($scope){
        $scope.forceRenderToolTip = function () {
          $('span.tooltip-hover').webuiPopover('destroy').webuiPopover({
            trigger: 'click',
            placement: 'right',
            delay: {
              show: 0,
              hide: 500
            },
            width: '278px'
          });
        };
      },
      link: function(scope, element) {
        var a = angular.copy(scope.array);
        a.splice(0, 1);
        scope.popoverContent = a.join(" , ");

      },
      templateUrl: 'src/modules/directives/renderArrayInSearchManager/templates/render-array.html',
      replace : true
    };
  }]);

})(angular.module('aet.directives.render-array'));

(function(module) {

  module.controller('viewImageController', ['$scope', '$state', '$modalInstance', '$log',
    function($scope, $state, $modalInstance, $log) {

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };

    }
  ]);

})(angular.module('aet-directives-hierarchyMap'));

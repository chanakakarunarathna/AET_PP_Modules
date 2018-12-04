(function(module) {

  module.controller('UnsavedController', ['$scope', '$modalInstance', '$log',
    function($scope, $modalInstance, $log) {

      $scope.yes = function() {
        $modalInstance.close({
          save : true
        });
      };

      $scope.no = function() {
        $modalInstance.close({
          save : false
        });
      };

    }
  ]);

})(angular.module('aet.modals'));

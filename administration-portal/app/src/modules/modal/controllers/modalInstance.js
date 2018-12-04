(function(module) {

  module.controller('ModalInstanceController', ['$scope', '$modalInstance', '$log',
    function($scope, $modalInstance, $log) {

//      $log.debug("ModalInstanceController");

      $scope.ok = function() {
        $modalInstance.close('ok');
      };

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };

    }
  ]);

})(angular.module('aet.modals'));

(function(module) {

  module.controller('viewPartnerPlaceListController', ['$scope', '$modalInstance',
    function($scope, $modalInstance) {
      $scope.cancel = function (){
        $modalInstance.dismiss('cancel');
      };
    }
  ]);

})(angular.module('aet.modals'));

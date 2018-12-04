(function(module) {

  module.controller('SearchAdminUserController', ['$scope', 'adminUserService', 'searchManager', '$state', 'alertsService', 'modalService', 'partnerDetails',
    function($scope, adminUserService, searchManager, $state, alertsService, modalService, partnerDetails) {
      $scope.searchManager = searchManager;

      $scope.deleteAdminUser = function(adminuser) {

        var modalInstance = modalService.deleteModal(adminuser.firstName, true, null);
        modalInstance.result.then(function() {
          adminUserService.deleteAdminUser(adminuser.id).then(function(data) {
            alertsService.addAlert({
              title: 'Success',
              message: "Successfully deleted '" + adminuser.firstName + "'",
              type: 'success'
            });
            searchManager.delete(adminuser.id);
          }, function(err) {
            alertsService.addAlert({
              title: 'Error',
              message: "Could not delete '" + adminuser.firstName + "'",
              type: 'error'
            });
          });
        });

      };

      $scope.editAdminUser = function(adminuser) {
        $state.go('index.secured.adminUser.edit', {
          id: adminuser.id
        });
      };
    }
  ]);

})(angular.module('aet.screens.adminUser'));

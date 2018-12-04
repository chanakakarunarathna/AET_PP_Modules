(function(module) {

    module.controller('SearchSuperAdminController', ['$scope', 'superAdminService', 'searchManager', '$state', 'alertsService', 'modalService',
        function($scope, superAdminService, searchManager, $state, alertsService, modalService) {
            $scope.searchManager = searchManager;

            $scope.deleteSuperAdmin = function(adminuser) {

                var modalInstance = modalService.deleteModal(adminuser.firstName);
                modalInstance.result.then(function() {
                    superAdminService.deleteSuperAdmin(adminuser.id).then(function(data) {
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

            $scope.editSuperAdmin = function(adminuser) {
                $state.go('index.secured.superAdmin.edit', {
                    id: adminuser.id
                });
            };
        }
    ]);

})(angular.module('aet.screens.superAdmin'));

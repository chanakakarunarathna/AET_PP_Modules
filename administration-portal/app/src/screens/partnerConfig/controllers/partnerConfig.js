(function(module) {

    module.controller('PartnerConfigController', ['$scope', 'partnerConfigService', 'adminUserService', 'alertsService', '$state', '$log', 'searchManager', 'modalService', '$rootScope',
        function($scope, partnerConfigService, adminUserService, alertsService, $state, $log, searchManager, modalService, $rootScope) {
    		
            $scope.searchManager = searchManager;

            $scope.deletePartnerConfig = function(partnerConfig) {
                var modalInstance = modalService.deleteModal(partnerConfig.hero.title);
                modalInstance.result.then(function() {
                    partnerConfigService.deletePartnerConfig(partnerConfig.id).then(function(data) {
                        alertsService.addAlert({
                            title: 'Success',
                            message: "Successfully deleted '" + partnerConfig.hero.title + "'",
                            type: 'success'
                        });
                        searchManager.delete(partnerConfig.id);
                        $state.reload();

                     
                    }, function(err) {
                        alertsService.addAlert({
                            title: 'Error',
                            message: "Could not delete '" + partnerConfig.hero.title + "'",
                            type: 'error'
                        });
                    });
                });
            };

            $scope.editPartnerConfig = function(partnerConfig) {

                $state.go('index.secured.partnerConfig.edit', {
                    partnerConfigId: partnerConfig.id
                }, {
                    reload: true
                });

                $rootScope.hideLoader();
            };

        }
    ]);

})(angular.module('aet.screens.partnerConfig'));

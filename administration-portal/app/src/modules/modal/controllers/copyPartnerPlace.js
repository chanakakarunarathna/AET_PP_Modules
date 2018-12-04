(function(module) {

    module.controller('copyPartnerPlaceController', ['$scope', 'partnerPlaceService', 'alertsService', '$state', '$log', '$rootScope', 'userDetails', 'CopyPartnerPlaceDTO', '$modalInstance', '$q',
        function($scope, partnerPlaceService, alertsService, $state, $log, $rootScope, userDetails, CopyPartnerPlaceDTO, $modalInstance, $q) {

            $scope.copyPartnerPlace = new CopyPartnerPlaceDTO();
            $scope.isSuperAdminUser = userDetails.getUser().isSuperAdmin;

            $scope.editPartnerPlace = function(partnerPlace) {

                $state.go('index.secured.partnerplace.edit', {
                    partnerPlaceId: partnerPlace.id
                }, {
                    reload: true
                }).then(function() {
                    $rootScope.hideLoader();
                });

            };

            $scope.ok = function() {
                save();
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };

            function save() {
                return $q(function(resolve, reject) {
                    $rootScope.showLoader("Please Wait...");
                    // setting the loggedIn user as partner place creator
                    $scope.copyPartnerPlace.creatorId = userDetails.getUserId();
                    angular.forEach($scope.copyPartnerPlace.partnerPlaceLeaders, function(item, key){
                      $scope.copyPartnerPlace.partnerPlaceLeaderIds.push(item.id);
                    });

                    partnerPlaceService.copyPartnerPlace($scope.origpartnerplace.id, $scope.copyPartnerPlace).then(function(data) {

                        alertsService.addAlert({
                            title: 'Success',
                            message: 'Partner Place "' + $scope.copyPartnerPlace.title + '" successfully created',
                            type: 'success',
                            removeOnStateChange: 2
                        });
                        $scope.editPartnerPlace(data);
                        $modalInstance.dismiss('cancel');
                        resolve(true);
                    }, function(err) {
                        $rootScope.hideLoader();
                        reject(true);
                    });
                });
            }
        }
    ]);

})(angular.module('aet.modals'));

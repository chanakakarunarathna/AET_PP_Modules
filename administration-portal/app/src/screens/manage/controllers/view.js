(function(module) {

    module.controller('ViewManageAccountController', ['$scope', '$rootScope', 'adminUserService', 'AdminUser', 'alertsService', '$state', '$log', 'partnerPlaceDetails', 'adminuser',
        function($scope, $rootScope, adminUserService, AdminUser, alertsService, $state, $log, partnerPlaceDetails, adminuser) {

            $scope.adminuser = adminuser;
            $scope.adminuser.partnerList = "";

            angular.forEach(adminuser.partnerRoles, function(cRole, ckey) {
                $scope.adminuser.partnerList += cRole.partner.name;
                if (ckey !== adminuser.partnerRoles.length - 1) {
                    $scope.adminuser.partnerList += ", ";
                }
            });

            $scope.submitAdminUserForm = function() {
                $rootScope.loader = true;
                adminUserService.updateSelfAdminUser($scope.adminuser).then(function(response) {
                    alertsService.addAlert({
                        title: 'Success',
                        message: 'AdminUser "' + $scope.adminuser.firstName + '" successfully updated',
                        type: 'success',
                        removeOnStateChange: 2
                    });
                    $rootScope.loader = false;
                }, function(err) {
                    $rootScope.loader = false;
                });

            };

        }
    ]);

})(angular.module('aet.screens.adminUser'));

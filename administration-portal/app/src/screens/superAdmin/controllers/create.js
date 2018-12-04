(function(module) {

    module.controller('CreateSuperAdminController', ['$scope', '$rootScope', 'superAdminService', 'roleService', 'AdminUser', 'alertsService', '$state', '$log', 'userDetails', 'partnerDetails', 'partnerList', 'roleDetails', 'PartnerRole',
        function($scope, $rootScope, superAdminService, roleService, AdminUser, alertsService, $state, $log, userDetails, partnerDetails, partnerList, roleDetails, PartnerRole) {

            $scope.adminuser = new AdminUser();
            $scope.adminuser.partnerRoles.push(new PartnerRole());
            $scope.partnerDetails = partnerList.searchResults.results;
            $scope.roleDetails = roleDetails.searchResults.results;
            $scope.adminuser.partnerRoles[0].partner = partnerDetails.getSelectedPartner();
            $scope.isSuperAdmin = userDetails.getUser().isSuperAdmin;
            $scope.adminuser.isSuperAdmin = true;

            $scope.picUrl = function($evt) {
                Console.log("Selected Image :");
                Console.log($evt.files);
            };

            $scope.submitForm = function() {
                $rootScope.loader = true;
                superAdminService.createSuperAdmin($scope.adminuser).then(function(response) {
                	var message = $scope.adminuser.isSuperAdmin==true ? 'Super Admin User ' : 'Admin User ';
                    alertsService.addAlert({
                        title: 'Success',
                        message: message +'"' + $scope.adminuser.firstName + '" successfully created',
                        type: 'success',
                        removeOnStateChange: 2
                    });
                    $rootScope.loader = false;
                    $state.go('index.secured.superAdmin.search', {}, {
                        reload: true
                    });
                }, function(err) {
                    $rootScope.loader = false;
                });

            };

        }
    ]);

})(angular.module('aet.screens.superAdmin'));

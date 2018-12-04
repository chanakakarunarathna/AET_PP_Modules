(function(module) {

    module.controller('CreateAdminUserController', ['$scope', '$rootScope', 'adminUserService', 'roleService', 'AdminUser', 'alertsService', '$state', '$log', 'userDetails', 'partnerDetails', 'partnerList', 'roleDetails', 'PartnerRole',
        function($scope, $rootScope, adminUserService, roleService, AdminUser, alertsService, $state, $log, userDetails, partnerDetails, partnerList, roleDetails, PartnerRole) {

            $scope.adminuser = new AdminUser();
            $scope.adminuser.partnerRoles.push(new PartnerRole());
            $scope.partnerDetails = partnerList.searchResults.results;
            $scope.roleDetails = roleDetails.searchResults.results;
            $scope.adminuser.pictureUrl = "aeturnum.jpg";
            $scope.adminuser.partnerRoles[0].partner = partnerDetails.getSelectedPartner();
            $scope.isSuperAdmin = userDetails.getUser().isSuperAdmin;

            $scope.picUrl = function($evt) {
                Console.log("Selected Image :");
                Console.log($evt.files);

            };

            $scope.addPartnerRole = function() {
                $scope.adminuser.partnerRoles.push(new PartnerRole());
            };

            $scope.deletePartnerRole = function(index) {
                if ($scope.adminuser.partnerRoles.length > 1)
                    $scope.adminuser.partnerRoles.splice(index, 1);
            };

            $scope.$watchCollection(function() {
                return $scope.adminuser.isSuperAdmin;
            }, function(newValue, oldValue) {
                if (newValue) {
                    $scope.adminuser.partnerRoles = [];
                    $scope.adminuser.partnerRoles.push(new PartnerRole());
                    $scope.adminuser.partnerPlaceRoles = undefined;
                }
            });


            $scope.partnerFilter = function(item) {
                var isFound = false;
                angular.forEach($scope.adminuser.partnerRoles, function(partnerRole, crKey) {
                    if (partnerRole.partner && partnerRole.partner.id === item.id) {
                        isFound = true;
                    }
                });
                return !isFound;
            };

            $scope.submitAdminUserForm = function() {
                $rootScope.loader = true;
                adminUserService.createAdminUser($scope.adminuser).then(function(response) {
                	var message = $scope.adminuser.isSuperAdmin==true ? 'Super Admin User ' : 'Admin User ';
                    alertsService.addAlert({
                        title: 'Success',
                        message: message +'"' + $scope.adminuser.firstName + '" successfully created',
                        type: 'success',
                        removeOnStateChange: 2
                    });
                    $rootScope.loader = false;
                    $state.go('index.secured.adminUser.search', {}, {
                        reload: true
                    });
                }, function(err) {
                    $rootScope.loader = false;
                });

            };

        }
    ]);

})(angular.module('aet.screens.adminUser'));

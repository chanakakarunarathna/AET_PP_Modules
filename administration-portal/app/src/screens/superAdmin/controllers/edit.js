(function(module) {

    module.controller('EditSuperUserController', ['$scope', '$rootScope', 'updatedUser', 'superAdminService', 'partnerDetails', 'partnerPlaceDetails', 'localStorageService', 'AdminUser', 'alertsService', '$state', '$log', 'userDetails', 'partnerLists', 'roleDetails', 'adminuser', 'modalService', 'PartnerRole',
        function($scope, $rootScope, updatedUser, superAdminService, partnerDetails, partnerPlaceDetails, localStorageService, AdminUser, alertsService, $state, $log, userDetails, partnerLists, roleDetails, adminuser, modalService, PartnerRole) {

            $scope.adminuser = angular.copy(adminuser);
            $scope.partnerLists = partnerLists.searchResults.results;
            $scope.roleDetails = roleDetails.searchResults.results;
            $scope.partnerDetails = partnerDetails;
            $scope.isSuperAdmin = userDetails.getUser().isSuperAdmin;
            $scope.adminuser.partnerRoles.push(new PartnerRole());

            $scope.addPartnerRole = function() {
                $scope.adminuser.partnerRoles.push(new PartnerRole());
            };

            $scope.deletePartnerRole = function(index) {
                if ($scope.adminuser.partnerRoles.length > 1)
                    $scope.adminuser.partnerRoles.splice(index, 1);
            };

            $scope.partnerFilter = function(item) {
                var isFound = false;
                angular.forEach($scope.adminuser.partnerRoles, function(partnerRole, crKey) {
                    if (partnerRole.partner && partnerRole.partner.id === item.id) {
                        isFound = true;
                    }
                });
                return !isFound;
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

            $scope.submitForm = function() {
                $rootScope.loader = true;

                superAdminService.updateSuperAdmin($scope.adminuser).then(function(response) {
                	var message = $scope.adminuser.isSuperAdmin==true ? 'Super Admin User ' : 'Admin User ';
                    alertsService.addAlert({
                        title: 'Success',
                        message: message +'"' + $scope.adminuser.firstName + '" successfully updated',
                        type: 'success',
                        removeOnStateChange: 2
                    });

                    //checks if the user is the current user and was switched to a lower role
                    if ($scope.adminuser.isSuperAdmin === false) {

                        if (angular.isDefined(updatedUser) && (updatedUser.id === $scope.adminuser.id)) {
                            $state.go('index.secured.dashboard', {}, {
                                reload: true
                            }).then(function() {
                                $rootScope.loader = false;
                            });
                        } else {
                        	$state.go('index.secured.adminUser.search', {}, {
                                reload: true
                            });
                        	$rootScope.loader = false;
                        }
                    } else {
                    	$state.go('index.secured.superAdmin.search', {}, {
                            reload: true
                        });
                        $rootScope.loader = false;
                    }
                }, function(err) {
                    $rootScope.loader = false;
                });

            };

        }
    ]);

})(angular.module('aet.screens.superAdmin'));

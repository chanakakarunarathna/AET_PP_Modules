(function(module) {

    module.controller('EditAdminUserController', ['$scope', '$rootScope', 'updatedUser', 'adminUserService', 'partnerDetails', 'partnerPlaceDetails', 'localStorageService', 'AdminUser', 'alertsService', '$state', '$log', 'userDetails', 'partnerLists', 'roleDetails', 'adminuser', 'modalService', 'PartnerRole',
        function($scope, $rootScope, updatedUser, adminUserService, partnerDetails, partnerPlaceDetails, localStorageService, AdminUser, alertsService, $state, $log, userDetails, partnerLists, roleDetails, adminuser, modalService, PartnerRole) {

            $scope.adminuser = angular.copy(adminuser);
            $scope.partnerLists = partnerLists.searchResults.results;
            $scope.roleDetails = roleDetails.searchResults.results;
            $scope.partnerDetails = partnerDetails;
            $scope.isSuperAdmin = userDetails.getUser().isSuperAdmin;

            $scope.addPartnerRole = function() {
                $scope.adminuser.partnerRoles.push(new PartnerRole());
            };

            function isValidDelete(index){
              var isValid = true;
              var cr = $scope.adminuser.partnerRoles[index];
              var partnerPartnerPlaces = [];
              var partnerLists = [];

              angular.forEach($scope.adminuser.partnerPlaceRoles, function(partnerPlaceRole, prKey) {
                  if (cr.partner != undefined && partnerPlaceRole.partnerPlace.partner.id == cr.partner.id) {
                      partnerPartnerPlaces.push(partnerPlaceRole);
                  }
              });

              angular.forEach($scope.adminuser.list, function(list, ilKey) {
                  if (list.partnerPlace.partner.id == cr.partner.id) {
                      partnerLists.push(list);
                  }
              });

              if ((partnerPartnerPlaces.length > 0) || (partnerLists.length > 0)) {
                  isValid = false;
                  showNotification(partnerPartnerPlaces, partnerLists);
              }

              return isValid;
            }

            $scope.deletePartnerRole = function(index) {
                if ($scope.adminuser.partnerRoles.length > 1 && isValidDelete(index))
                    $scope.adminuser.partnerRoles.splice(index, 1);
            };

            function isValidRoleChange(newValue, oldValue) {
                var isValid = true;
                angular.forEach($scope.adminuser.partnerRoles, function(partnerRole, crKey) {

                    var partnerPartnerPlaces = [];
                    var partnerLists = [];
                    if (!angular.equals(newValue[crKey], oldValue[crKey])) {
                      // we should be able to remove first if condition TODO
                        if ((newValue.length < oldValue.length)) {

                            angular.forEach($scope.adminuser.partnerPlaceRoles, function(partnerPlaceRole, prKey) {
                                if (oldValue[crKey].partner!=undefined && partnerPlaceRole.partnerPlace.partner.id == oldValue[crKey].partner.id) {
                                    partnerPartnerPlaces.push(partnerPlaceRole);
                                }
                            });

                            angular.forEach($scope.adminuser.list, function(list, ilKey) {
                                if (list.partnerPlace.partner.id == oldValue[crKey].partner.id) {
                                    partnerLists.push(list);
                                }
                            });

                            if ((partnerPartnerPlaces.length > 0) || (partnerLists.length > 0)) {
                                isValid = false;
                                showNotification(partnerPartnerPlaces, partnerLists);
                                $scope.adminuser.partnerRoles = oldValue;
                            }


                        } else if (angular.isDefined(newValue[crKey].partner) && angular.isDefined(newValue[crKey].role) && angular.isDefined(oldValue[crKey])) {

                            angular.forEach($scope.adminuser.partnerPlaceRoles, function(partnerPlaceRole, prKey) {
                                if (angular.isDefined(oldValue[crKey].partner)) {
                                    if (partnerPlaceRole.partnerPlace.partner.id == oldValue[crKey].partner.id) {
                                        partnerPartnerPlaces.push(partnerPlaceRole);
                                    }
                                }
                            });
                            angular.forEach($scope.adminuser.list, function(list, ilKey) {
                                if (angular.isDefined(oldValue[crKey].partner)) {
                                    if (list.partnerPlace.partner.id == oldValue[crKey].partner.id) {
                                        partnerLists.push(list);
                                    }
                                }
                            });

                            if ((partnerPartnerPlaces.length > 0) || (partnerLists.length > 0)) {

                                var isPartnerPlaceAdmin = false;
                                var isPartnerPlaceLeader = false;
                                var isTeamMember = false;
                                var isSME = false;
                                angular.forEach(partnerPartnerPlaces, function(partnerPlaceRole, rKey) {
                                    if (partnerPlaceRole.role.id === 4) {
                                        isPartnerPlaceAdmin = true;
                                    }
                                    if (partnerPlaceRole.role.id === 5) {
                                        isPartnerPlaceLeader = true;
                                    }
                                    if (partnerPlaceRole.role.id === 6) {
                                        isTeamMember = true;
                                    }
                                    if (partnerPlaceRole.role.id === 8) {
                                        isSME = true;
                                    }
                                });

                                if (angular.isUndefined(newValue[crKey])) {
                                    isValid = false;
                                    showNotification(partnerPartnerPlaces, partnerLists);
                                    $scope.adminuser.isSuperAdmin = false;
                                    $scope.adminuser.partnerRoles[crKey] = oldValue[crKey];
                                } else if (angular.isDefined(oldValue[crKey])) {
                                    if (!angular.equals(newValue[crKey].partner, oldValue[crKey].partner)) {
                                        //If partner changed show notification
                                        isValid = false;
                                        showNotification(partnerPartnerPlaces, partnerLists);
                                        $scope.adminuser.partnerRoles[crKey].partner = oldValue[crKey].partner;
                                    } else if (!angular.equals(newValue[crKey].role, oldValue[crKey].role)) {
                                        //if role changed
                                        if (isSME) {
                                            if (newValue[crKey].role.id !== 3) {
                                                isValid = false;
                                                showNotification(partnerPartnerPlaces, partnerLists);
                                                $scope.adminuser.partnerRoles[crKey] = oldValue[crKey];
                                            }
                                        } else {
                                            if (newValue[crKey].role.id === 3) {
                                                isValid = false;
                                                showNotification(partnerPartnerPlaces, partnerLists);
                                                $scope.adminuser.partnerRoles[crKey] = oldValue[crKey];
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                return isValid;
            }

            $scope.$watchCollection(function() {
                return $scope.adminuser.isSuperAdmin;
            }, function(newValue, oldValue) {
                if (newValue === true) {
                    if (isValidRoleChange([], $scope.adminuser.partnerRoles)) {
                        $scope.adminuser.partnerRoles = [];
                        $scope.adminuser.partnerPlaceRoles = undefined;
                    } else {
                        $scope.adminuser.isSuperAdmin = oldValue;
                    }
                } else if ($scope.adminuser.partnerRoles.length === 0) {
                    $scope.adminuser.partnerRoles.push(new PartnerRole());
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

            $scope.partnerRoleFilter = function(item) {

                if ($scope.isSuperAdmin) {
                    return true;
                } else {
                    var admin = _.find(userDetails.getUser().partnerRoles, function(partnerRole) {
                        return (partnerRole.partner.id == $scope.partnerDetails.getSelectedPartner().id && partnerRole.role.title == "Partner Admin");
                    });
                    if (admin != undefined && $scope.partnerDetails.getSelectedPartner().id == item.partner.id) {
                        return true;
                    }
                    return false;
                }
            };

            $scope.$watch(function() {
                return $scope.adminuser.partnerRoles;
            }, function(newValue, oldValue) {
                console.log("$WATCH","$scope.adminuser.partnerRoles");
                isValidRoleChange(newValue, oldValue);
            }, true);

            function showNotification(partnerPartnerPlaces, partnerLists) {
                var modalInstance = modalService.partnerPlaceAndListWarningModal($scope.adminuser, partnerPartnerPlaces, partnerLists);
                modalInstance.result.then(function() {});
            }

            $scope.submitAdminUserForm = function() {
                $rootScope.loader = true;

                adminUserService.updateAdminUser($scope.adminuser).then(function(response) {
                	var message = $scope.adminuser.isSuperAdmin==true ? 'Super Admin User ' : 'Admin User ';
                    alertsService.addAlert({
                        title: 'Success',
                        message: message +'"' + $scope.adminuser.firstName + '" successfully updated',
                        type: 'success',
                        removeOnStateChange: 2
                    });
                    //checks if the user is the current user and was switched to a lower role
                    if (angular.isDefined(updatedUser) && (updatedUser.id === $scope.adminuser.id) && ($scope.adminuser.partnerRoles[0].role.id !== 1)) {
                        $state.go('index.secured.dashboard', {}, {
                            reload: true
                        }).then(function() {
                            $rootScope.loader = false;
                        });
                    } else if ($scope.adminuser.isSuperAdmin === true) {
                        $state.go('index.secured.superAdmin.search', {}, {
                            reload: true
                        });
                        $rootScope.loader = false;
                    } else {
                    	$state.go('index.secured.adminUser.search', {}, {
                            reload: true
                        });
                        $rootScope.loader = false;
                    }

                    //
                    //updating changed BU details
                    /*partnerPlaceDetails.clear();
                    localStorageService.set('partnerPlaceId', selectedPartnerPlaceId);
                    $state.go('index.secured.adminUser.search', {}, {
                      reload: true
                    });*/
                }, function(err) {
                    $rootScope.loader = false;
                });

            };

        }
    ]);

})(angular.module('aet.screens.adminUser'));

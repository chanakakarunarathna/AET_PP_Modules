(function(module) {

    module.service('adminUserService', ['adminUserEndpoint', 'superAdminEndpoint', 'adminUserTransformer', '$q', '$log', 'partnerDetails', 'PartnerRole', 'userDetails', 'partnerPlaceService', '$stateParams', 'partnerPlaceDetails',
        function(adminUserEndpoint, superAdminEndpoint, adminUserTransformer, $q, $log, partnerDetails, PartnerRole, userDetails, partnerPlaceService, $stateParams, partnerPlaceDetails) {

            this.createAdminUser = function(adminuser) {
                if (adminuser.isSuperAdmin) {
                    adminuser.partnerRoles = [{}];
                }

                var dto = adminUserTransformer.adminUserToCreateDTO(adminuser);
                return adminUserEndpoint.createAdminUser(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not create adminuser", err);
                    return $q.reject(err);
                });

            };

            this.deleteAdminUser = function(id) {
                return adminUserEndpoint.deleteAdminUser(id).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not delete adminuser", err);
                    return $q.reject(err);
                });
            };

            this.updateAdminUser = function(adminuser) {
                if (adminuser.isSuperAdmin) {
                    adminuser.partnerRoles = [{}];
                }

                var dto = adminUserTransformer.adminUserToUpdateDTO(adminuser);

                return adminUserEndpoint.updateAdminUser(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not update business user", err);
                    return $q.reject(err);
                });

            };

            this.updateSelfAdminUser = function(adminuser) {

                var dto = adminUserTransformer.adminUserToUpdateSelfAdminUserDTO(adminuser);

                return adminUserEndpoint.updateSelfAdminUser(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not update self business user", err);
                    return $q.reject(err);
                });

            };

            this.findSelfAdminUser = function(adminId) {

                return adminUserEndpoint.findSelfAdminUser(adminId).then(function(response) {
                    return adminUserTransformer.DTOToAdminUser(response.data);
                }, function(err) {
                    console.error("Could not load business user");
                    return $q.reject(err);
                });

            };

            this.findAdminUser = function(id) {

                return adminUserEndpoint.findAdminUser(id).then(function(response) {
                    return adminUserTransformer.DTOToAdminUser(response.data);
                }, function(err) {
                    console.error("Could not load business user");
                    return $q.reject(err);
                });

            };

            this.searchAdminUser = function(params) {

                return adminUserEndpoint.listAdminUser(params).then(function(dto) {
                    return adminUserTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search admin user", err);
                    return $q.reject(err);
                });

            };

            this.searchAdminUserByPartnerId = function(params) {

                return adminUserEndpoint.searchAdminUserByPartnerId(params).then(function(dto) {
                    return adminUserTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search Admin Users by partner id", err);
                    return $q.reject(err);
                });

            };

            this.searchAdminUserByPartnerIdEditPartnerPlace = function(params, partnerPlaceId) {

                return adminUserEndpoint.searchAdminUserByPartnerId(params).then(function(dto) {
                    partnerPlaceService.findPartnerPlace(partnerPlaceId).then(function(adminUser) {

                        var adminUsers = dto.data.adminUsers;
                        var selectedAdminUsers = adminUser;
                        var users = [];

                        angular.forEach(selectedAdminUsers.partnerPlaceAdmins, function(partnerPlaceAdmin, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceAdmin);
                        });
                        angular.forEach(selectedAdminUsers.partnerPlaceLeaders, function(partnerPlaceLeader, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceLeader);
                        });
                        angular.forEach(selectedAdminUsers.partnerPlaceSupportMembers, function(partnerPlaceSupportMember, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceSupportMember);
                        });
                        angular.forEach(selectedAdminUsers.partnerPlaceTeamMembers, function(partnerPlaceTeamMember, partnerPlaceAdminsKey) {
                            users.push(partnerPlaceTeamMember);
                        });

                        angular.forEach(users, function(user, userKey) {
                            for (var i = 0; adminUsers.length > i; i++) {
                                if (adminUsers[i].id === user.id) {
                                    adminUsers.splice(i, 1);
                                }
                            }
                        });

                        return adminUsers;
                    });


                    return adminUserTransformer.searchDTOToSearchResults(dto);


                }, function(err) {
                    console.error("Could not search admin users by partnerId edit partner place", err);
                    return $q.reject(err);
                });

            };


            this.getAuthorizedPartners = function(admin) {
                return _.map(admin.partnerRoles, function(partnerRole) {
                    return partnerRole.partner;
                });
            };

            this.hasFeature = function(admin, feature) {

                if (angular.isUndefined(admin.partnerFeatures) || angular.isUndefined(admin.partnerPlaceFeatures)) {
                    return false;
                }

                // setting partnerPlaceFeatures to null if there aren't any partner places in the partner place selector
                var partnerPlaceFeatures = null;
                if (angular.isDefined(partnerPlaceDetails.getSelectedPartnerPlace())) {
                    partnerPlaceFeatures = admin.partnerPlaceFeatures[partnerPlaceDetails.getSelectedPartnerPlace().id];
                }
                var partnerFeatures = admin.partnerFeatures[partnerDetails.getSelectedPartner().id];

                var hasPartnerFeature = _.any(partnerFeatures, function(f) {
                    return f.title === feature;
                });

                var hasPartnerPlaceFeature = _.any(partnerPlaceFeatures, function(f) {
                    return f.title === feature;
                });

                return hasPartnerFeature || hasPartnerPlaceFeature;
            };

            this.addRole = function(admin, role) {
                var mr = new PartnerRole();
                mr.partner = partnerDetails.getSelectedPartner();
                mr.role = role;
                admin.partnerRoles.push(mr);
            };

            this.forgotPassword = function(dto) {

                return adminUserEndpoint.forgotPassword(dto).then(function(dto) {
                    return true;
                }, function(err) {
                    console.error("Could not reset password", err);
                    return $q.reject(err);
                });
            };

            this.passwordReset = function(dto) {
                return adminUserEndpoint.passwordReset(dto).then(function(dto) {
                    return true;
                }, function(err) {
                    console.error("Could not reset password", err);
                    return $q.reject(err);
                });
            };

            this.createSuperAdmin = function(adminuser) {
                if (adminuser.isSuperAdmin) {
                    adminuser.partnerRoles = [{}];
                }

                var dto = adminUserTransformer.adminUserToCreateDTO(adminuser);
                return superAdminEndpoint.createSuperAdmin(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not create super admin", err);
                    return $q.reject(err);
                });

            };

            this.deleteSuperAdmin = function(id) {
                return superAdminEndpoint.deleteSuperAdmin(id).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not delete super admin", err);
                    return $q.reject(err);
                });
            };

            this.updateSuperAdmin = function(adminuser) {
                if (adminuser.isSuperAdmin) {
                    adminuser.partnerRoles = [{}];
                }

                var dto = adminUserTransformer.adminUserToUpdateDTO(adminuser);

                return superAdminEndpoint.updateSuperAdmin(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not update super admin", err);
                    return $q.reject(err);
                });

            };

            this.findSuperAdmin = function(id) {

                return superAdminEndpoint.findSuperAdmin(id).then(function(response) {
                    return adminUserTransformer.DTOToAdminUser(response.data);
                }, function(err) {
                    console.error("Could not load super admin");
                    return $q.reject(err);
                });

            };

        }
    ]);

})(angular.module('aet.domain.adminUser'));

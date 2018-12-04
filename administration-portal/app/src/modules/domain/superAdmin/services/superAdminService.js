(function(module) {

    module.service('superAdminService', ['superAdminEndpoint', 'superAdminTransformer', '$q', '$log', 'partnerDetails', 'PartnerRole', 'userDetails', 'partnerPlaceService', '$stateParams', 'partnerPlaceDetails',
        function(superAdminEndpoint, superAdminTransformer, $q, $log, partnerDetails, PartnerRole, userDetails, partnerPlaceService, $stateParams, partnerPlaceDetails) {

            this.createSuperAdmin = function(adminuser) {
                if (adminuser.isSuperAdmin) {
                    adminuser.partnerRoles = [{}];
                }

                var dto = superAdminTransformer.superAdminToCreateDTO(adminuser);
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

                var dto = superAdminTransformer.superAdminToUpdateDTO(adminuser);

                return superAdminEndpoint.updateSuperAdmin(dto).then(function(response) {
                    return response.data;
                }, function(err) {
                    $log.debug("Could not update super admin", err);
                    return $q.reject(err);
                });

            };

            this.findSuperAdmin = function(id) {

                return superAdminEndpoint.findSuperAdmin(id).then(function(response) {
                    return superAdminTransformer.DTOToSuperAdmin(response.data);
                }, function(err) {
                    console.error("Could not load super admin");
                    return $q.reject(err);
                });

            };

            this.searchSuperAdmins = function(params) {

                return superAdminEndpoint.listSuperAdmin(params).then(function(dto) {
                    return superAdminTransformer.searchDTOToSearchResults(dto);
                }, function(err) {
                    console.error("Could not search super admin", err);
                    return $q.reject(err);
                });

            };

        }
    ]);

})(angular.module('aet.domain.superAdmin'));

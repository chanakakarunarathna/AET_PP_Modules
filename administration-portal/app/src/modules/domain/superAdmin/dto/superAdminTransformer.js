(function(module) {

    module.service('superAdminTransformer', ['SuperAdmin', 'genericTransformer', 'partnerRoleTransformer', 'partnerPlaceRoleTransformer', '$log', 'CreateSuperAdminDTO', 'UpdateSuperAdminDTO',
        function(SuperAdmin, genericTransformer, partnerRoleTransformer, partnerPlaceRoleTransformer, $log, CreateSuperAdminDTO, UpdateSuperAdminDTO) {

            this.DTOToSuperAdmin = function(dto) {
                var adminUser = genericTransformer.DTOToObject(dto, SuperAdmin);

                adminUser.partnerRoles = partnerRoleTransformer.DTOToPartnerRoles(dto.partnerRoles);
                adminUser.partnerPlaceRoles = partnerPlaceRoleTransformer.DTOToPartnerPlaceRoles(dto.partnerPlaceRoles);
                adminUser.list = dto.internalLists;

                //convert createdDate to Date object
                var createdDate = new Date(dto.createdDate);
                adminUser.createdDate = createdDate.getMonth() + 1 + "/" + createdDate.getDate() + "/" + createdDate.getFullYear();

                setFeatures(adminUser);

                adminUser.showDiscussionDailyNotificationsCheckbox = showDiscussionNotificationCheckbox(adminUser.partnerPlaceRoles);

                return adminUser;
            };

            this.DTOToSuperAdmins = function(dto) {
                return _.map(dto, this.DTOToSuperAdmin);
            };

            this.searchDTOToSearchResults = function(dto) {
                var searchResults = genericTransformer.DTOToSearchResults(dto.data);
                searchResults.results = dto.data.adminUsers;
                return searchResults;
            };

            this.loginDTOToSuperAdmin = function(dto) {
                var adminUser = genericTransformer.DTOToObject(dto, SuperAdmin);
                adminUser.id = dto.userId;
                adminUser.email = dto.email;

                //convert createdDate to Date object
                var createdDate = new Date(dto.createdDate);
                adminUser.createdDate = createdDate.getMonth() + 1 + "/" + createdDate.getDate() + "/" + createdDate.getFullYear();
                //adminUser.role = roleTransformer.DTOToRole(adminUser.role);

                return adminUser;
            };

            this.superAdminToCreateDTO = function(adminUser) {
                var dto = genericTransformer.objectToDTO(adminUser, CreateSuperAdminDTO);
                dto.partnerRoles = _.map(adminUser.partnerRoles, partnerRoleTransformer.partnerRoleToCreateDTO);
                return dto;
            };

            this.superAdminToUpdateDTO = function(adminUser) {
                var dto = genericTransformer.objectToDTO(adminUser, UpdateSuperAdminDTO);
                dto.partnerRoles = _.map(adminUser.partnerRoles, partnerRoleTransformer.partnerRoleToCreateDTO);
                return dto;
            };

            this.superAdminToUpdateSelfSuperAdminDTO = function(adminUser) {
                var dto = genericTransformer.objectToDTO(adminUser, UpdateSelfSuperAdminDTO);
                return dto;
            };

            var setFeatures = function(adminUser) {

                adminUser.partnerFeatures = _.reduce(adminUser.partnerRoles, function(result, partnerRole) {
                    if (angular.isDefined(result[partnerRole.partner.id])) {
                        result[partnerRole.partner.id] = result[partnerRole.partner.id].concat(partnerRole.role.features);
                    } else {
                        result[partnerRole.partner.id] = partnerRole.role.features;
                    }

                    return result;
                }, {});

                adminUser.partnerPlaceFeatures = _.reduce(adminUser.partnerPlaceRoles, function(result, partnerPlaceRole) {
                    if (angular.isDefined(result[partnerPlaceRole.partnerPlace.id])) {
                        result[partnerPlaceRole.partnerPlace.id] = result[partnerPlaceRole.partnerPlace.id].concat(partnerPlaceRole.role.features);
                    } else {
                        result[partnerPlaceRole.partnerPlace.id] = partnerPlaceRole.role.features;
                    }

                    return result;
                }, {});
            };

            var showDiscussionNotificationCheckbox = function(partnerPlaceRoles) {
                var showDiscussionCheckBox = false;

                for (var i = 0; i < partnerPlaceRoles.length; i++) {
                    if (partnerPlaceRoles[i].role.id == 5 || partnerPlaceRoles[i].role.id == 6) {
                        showDiscussionCheckBox = true;
                        break;
                    }
                }

                return showDiscussionCheckBox;
            };
        }
    ]);

})(angular.module('aet.domain.superAdmin'));

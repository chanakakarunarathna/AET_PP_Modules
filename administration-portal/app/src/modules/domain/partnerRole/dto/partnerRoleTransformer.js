(function(module) {
    
    module.service('partnerRoleTransformer', ['PartnerRole', 'genericTransformer', 'partnerTransformer', 'roleTransformer',
        function(PartnerRole, genericTransformer, partnerTransformer, roleTransformer) {

            this.DTOToPartnerRoles = function(dto) {
                return _.map(dto, this.DTOToPartnerRole);
            }

            this.DTOToPartnerRole = function(dto) {
                var partnerRole = new PartnerRole();
                partnerRole.partner = partnerTransformer.getDTOToPartner(dto.partner);
                partnerRole.role = roleTransformer.DTOToRole(dto.role);

                return partnerRole;
            };
			
            this.partnerRoleToCreateDTO = function(partnerRole) {
              if(partnerRole.partner && partnerRole.role) {
                return {
                  partnerId: partnerRole.partner.id,
                  roleId: partnerRole.role.id
                };
              }
              else {
                return {};
              }
                
            };

        }
    ]);

})(angular.module('aet.domain.partnerRole'));


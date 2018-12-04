(function(module) {

  module.service('partnerPlaceRoleTransformer', ['PartnerPlaceRole', 'genericTransformer', 'partnerPlaceTransformer', 'roleTransformer',
    function(PartnerPlaceRole, genericTransformer, partnerPlaceTransformer, roleTransformer) {

      this.DTOToPartnerPlaceRoles = function(dto) {
        return _.map(dto, this.DTOToPartnerPlaceRole);
      }

      this.DTOToPartnerPlaceRole = function(dto) {
        var partnerPlaceRole = new PartnerPlaceRole();
        partnerPlaceRole.partnerPlace = partnerPlaceTransformer.DTOToPartnerPlace(dto.partnerPlace);
        partnerPlaceRole.role = roleTransformer.DTOToRole(dto.role);
        return partnerPlaceRole;
      };

      this.partnerPlaceRoleToCreateDTO = function(partnerPlaceRole) {
        if (partnerPlaceRole.partnerPlace && partnerPlaceRole.role) {
          return {
        	partnerPlaceId: partnerPlaceRole.partnerPlace.id,
            roleId: partnerPlaceRole.role.id
          };
        } else {
          return {};
        }

      };

    }
  ]);

})(angular.module('aet.domain.partnerPlaceRole'));

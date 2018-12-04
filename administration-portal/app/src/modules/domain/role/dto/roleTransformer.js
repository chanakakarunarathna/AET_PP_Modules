(function(module) {

  module.service('roleTransformer', ['Role', 'genericTransformer', 'featureTransformer', 'CreateRoleRequestDTO',
    function(Role, genericTransformer, featureTransformer, CreateRoleRequestDTO) {

      this.DTOToRole = function(dto) {
        var role = genericTransformer.DTOToObject(dto, Role);
        role.features = _.map(dto.features, featureTransformer.getDTOToFeature);
        role.featureNames = _.reduce(role.features, function(obj, feature) {
          obj[feature.title] = true;
          return obj;
        }, {});
        return role;
      };

      this.searchDTOToSearchResults = function(dto) {
        var searchResults = genericTransformer.DTOToSearchResults(dto);
        searchResults.results = _.map(dto.roles, this.DTOToRole);

        return searchResults;
      };

      this.roleToCreateDTO = function(role) {
        var dto = genericTransformer.objectToDTO(role, CreateRoleRequestDTO);

        dto.featureIds = _.map(role.features, 'id');

        return dto;
      };

      this.roleToUpdateDTO = function(role) {
        var dto = genericTransformer.objectToDTO(role, CreateRoleRequestDTO);

        dto.featureIds = _.map(role.features, 'id');
        dto.id = role.id;

        return dto;
      };

    }
  ]);

})(angular.module('aet.domain.role'));

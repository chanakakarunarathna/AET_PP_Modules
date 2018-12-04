(function(module) {

  module.service('roleService', ['roleEndpoint', '$q', '$log', 'roleTransformer', 'alertsService',
    function(roleEndpoint, $q, $log, roleTransformer, alertsService) {

      this.searchRoles = function(searchObject) {

        return roleEndpoint.searchRoles(searchObject).then(function(dto) {
          return roleTransformer.searchDTOToSearchResults(dto.data);
        }, function(err) {
          console.error("Could not search Roles");
          return $q.reject(err);
        });

      };

      this.searchBURoleLists = function(searchObject) {

        return roleEndpoint.searchBURoleLists(searchObject).then(function(dto) {
          return roleTransformer.searchDTOToSearchResults(dto.data);
        }, function(err) {
          console.error("Could not search Roles");
          return $q.reject(err);
        });

      };

      this.createRole = function(role, features) {
        $log.debug("roleService::createRole", role);

        role.featureNames = _.pick(role.featureNames, function(value, feature) {
          return value;
        })
        role.features = _.map(role.featureNames, function(value, feature) {
          return _.find(features, function(f) {
            return f.title == feature;
          });
        });
        var dto = roleTransformer.roleToCreateDTO(role);

        return roleEndpoint.createRole(dto).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not create role");
          return $q.reject(err);
        });
      };

      this.updateRole = function(role, features) {
        $log.debug("roleService::updateRole", role);

        role.featureNames = _.pick(role.featureNames, function(value, feature) {
          return value;
        })
        role.features = _.map(role.featureNames, function(value, feature) {
          return _.find(features, function(f) {
            return f.title == feature;
          });
        });
        var dto = roleTransformer.roleToUpdateDTO(role);

        return roleEndpoint.updateRole(dto).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not update role");
          return $q.reject(err);
        });
      };

      this.getRole = function(id) {
        $log.debug("roleService::getRole", id);

        return roleEndpoint.getRole(id).then(function(dto) {
          return roleTransformer.DTOToRole(dto.data);
        }, function(err) {
          console.error("Could not get role");
          return $q.reject(err);
        });

      };

      this.deleteRole = function(id) {
        $log.debug("roleService::deleteRole", id);

        return roleEndpoint.deleteRole(id).then(function(response) {
          return response.data;
        }, function(err) {
          console.error("Could not delete role");
          return $q.reject(err);
        });

      };

    }

  ])


})(angular.module('aet.domain.role'));

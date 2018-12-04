(function (module) {

  module.service('roleEndpoint', ['$q', '$http', 'api', '$log',
    function ($q, $http, api, partnerDetails, $log) {

      this.searchRoles = function (params) {

        var searchRoleURL = api('admin') + '/role';
        return $http.get(searchRoleURL, {params: params});
      };

      this.searchBURoleLists = function (params) {

        var searchRoleURL = api('admin') + '/role';
        return $http.get(searchRoleURL, {params: {ispartnerrole: true}});
      };

      this.createRole = function (dto) {
        var url = api('admin') + '/role';

        return $http.post(url, dto);
      };

      this.updateRole = function (dto) {
        var url = api('admin') + '/role/' + dto.id;

        return $http.put(url, dto);
      };

      this.deleteRole = function (id) {
        var url = api('admin') + '/role/' + id;

        return $http.delete(url);
      };

      this.getRole = function (id) {
        var url = api('admin') + '/role/' + id;

        return $http.get(url);
      };

    }
  ]);

})(angular.module('aet.endpoints'));
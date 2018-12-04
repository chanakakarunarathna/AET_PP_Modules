(function(module) {

    module.service('superAdminEndpoint', ['$q', '$http', 'api', 'partnerDetails',
        function($q, $http, api, partnerDetails) {

            this.createSuperAdmin = function(superAdmin) {

                var createSuperAdminUrl = api('admin') + '/superadmin';
                return $http.post(createSuperAdminUrl, superAdmin);
            };

            this.updateSuperAdmin = function(superAdmin) {

                var url = api('admin') + '/superadmin/' + superAdmin.id;
                return $http.put(url, superAdmin);
            };


            this.findSuperAdmin = function(id) {

                var url = api('admin') + '/superadmin/' + id;
                return $http.get(url);
            };

            this.deleteSuperAdmin = function(id) {
                var url = api('admin') + '/superadmin/' + id;
                return $http.delete(url);
            };
            this.listSuperAdmin = function(params) {
                var listSuperAdminURL = api('admin') + '/superadmin/search';
                return $http.get(listSuperAdminURL, {
                    params: params
                });
            };

        }
    ]);

})(angular.module('aet.endpoints'));

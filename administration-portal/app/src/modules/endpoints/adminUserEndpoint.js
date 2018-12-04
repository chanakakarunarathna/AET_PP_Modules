(function(module) {

    module.service('adminUserEndpoint', ['$q', '$http', 'api', 'partnerDetails',
        function($q, $http, api, partnerDetails) {

            this.createAdminUser = function(adminuser) {

                var createAdminUserUrl = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/adminuser';
                return $http.post(createAdminUserUrl, adminuser);
            };

            this.updateAdminUser = function(adminuser) {

                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/adminuser/' + adminuser.id;
                return $http.put(url, adminuser);
            };

            this.updateSelfAdminUser = function(adminuser) {
                var url = api('admin') + '/adminuser/' + adminuser.id + '/profile';
                return $http.put(url, adminuser);
            };

            this.findSelfAdminUser = function(userId) {

                var findAdminUserURL = api('admin') + '/adminuser/' + userId + '/profile';
                return $http.get(findAdminUserURL);
            };

            this.findAdminUser = function(id) {

                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/adminuser/' + id;
                return $http.get(url);
            };

            this.listAdminUser = function(params) {
                var listAdminUserURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/search/adminuser';
                return $http.get(listAdminUserURL, {
                    params: params
                });
            };

            this.searchAdminUserByPartnerId = function(params) {
                var listAdminUserByPartnerIdURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/adminuser';
                return $http.get(listAdminUserByPartnerIdURL, {
                    params: params
                });
            };

            this.deleteAdminUser = function(id) {
                var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/adminuser/' + id;
                return $http.delete(url);
            };

            this.forgotPassword = function(dto) {
                var url = api('admin') + '/adminuser/forgotpassword';
                return $http.put(url, dto);
            };

            this.passwordReset = function(dto) {
                var url = api('admin') + '/adminuser/resetpassword';
                return $http.put(url, dto);
            };

        }
    ]);

})(angular.module('aet.endpoints'));

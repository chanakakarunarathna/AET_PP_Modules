(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.manageaccount', {
                    url: '/account',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/manage/templates/view.html',
                            controller: 'ViewManageAccountController'
                        }
                    },
                    data: {
                        screenName: "Edit Profile"
                    },
                    resolve: {
                        adminuser: ['adminUserService', '$stateParams', 'updatedUser', function(adminUserService, $stateParams, updatedUser) {
                            return adminUserService.findSelfAdminUser(updatedUser.id);
                        }]
                    }
                });
        }
    ]);

})(angular.module('aet.screens.manageaccount'));

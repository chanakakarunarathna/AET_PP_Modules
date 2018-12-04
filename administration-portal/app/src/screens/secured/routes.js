(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured', {
                    abstract: true,
                    resolve: {
                        updatedUser: ['security', 'localStorageService', '$q', 'userDetails',
                            function(security, localStorageService, $q, userDetails) {
                                return $q.when(security.updateSecurityDetails());
                            }
                        ]
                    },
                    views: {
                        'header': {
                            templateUrl: 'src/screens/secured/templates/header.html',
                            controller: 'HeaderController'
                        },
                        'sidebar@index': {
                            templateUrl: 'src/screens/sidebars/templates/admin.html',
                            controller: 'AdminSidebarController'
                        },
                        'minisidebar@index': {
                            templateUrl: 'src/screens/sidebars/templates/minisidebar.html',
                            controller: 'AdminSidebarController'
                        },
                        'footer': {
                            templateUrl: 'src/screens/secured/templates/footer.html',
                            controller: 'FooterController'
                        },
                    }
                })
                .securedState('index.secured.logout', {
                    url: '/logout',
                    resolve: {
                        logout: ['loginService', function(loginService) {
                            return loginService.logout();
                        }]
                    }
                });
        }
    ]);

})(angular.module('aet.screens.secured'));

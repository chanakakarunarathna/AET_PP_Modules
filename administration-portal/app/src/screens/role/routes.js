(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.role', {
                    abstract: true,
                    url: '/role',
                    data: {
                        selectedTab: "Roles",
                        screenName: "Roles"
                    }
                })
                .securedState('index.secured.role.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/role/templates/search.html',
                            controller: 'SearchRoleController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'roleService', '$log', 'updatedUser', function(SearchManager, roleService, $log, updatedUser) {
                            var searchManager = new SearchManager(roleService.searchRoles);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Roles",
                        feature: "super"
                    }

                })
                .securedState('index.secured.role.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/role/templates/create.html',
                            controller: 'CreateRoleController',
                        }
                    },
                    data: {
                        screenName: "Create Role",
                        feature: "super"
                    },
                    resolve: {
                        featureSearchManager: ['SearchManager', 'featureService', '$log', 'updatedUser', function(SearchManager, featureService, $log, updatedUser) {
                            var searchManager = new SearchManager(featureService.searchFeatures);

                            searchManager.count = 1000;

                            return searchManager.search();
                        }]
                    }
                })
                .securedState('index.secured.role.edit', {
                    url: '/edit/:roleId',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/role/templates/edit.html',
                            controller: 'EditRoleController',
                        }
                    },
                    data: {
                        screenName: "Edit Role",
                        feature: "super"
                    },
                    resolve: {
                        featureSearchManager: ['SearchManager', 'featureService', '$log', 'updatedUser', function(SearchManager, featureService, $log, updatedUser) {
                            var searchManager = new SearchManager(featureService.searchFeatures);

                            searchManager.count = 1000;

                            return searchManager.search();
                        }],
                        role: ['roleService', '$stateParams', 'updatedUser', function(roleService, $stateParams, updatedUser) {
                            return roleService.getRole($stateParams.roleId);
                        }]
                    }
                });


        }
    ]);

})(angular.module('aet.screens.role'));

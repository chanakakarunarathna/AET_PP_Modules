(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.superAdmin', {
                    abstract: true,
                    url: '/superAdmin',
                    data: {
                        selectedTab: "SuperAdmins"
                    }
                })
                .securedState('index.secured.superAdmin.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/superAdmin/templates/search.html',
                            controller: 'SearchSuperAdminController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'superAdminService', '$log', 'updatedUser', function(SearchManager, superAdminService, $log, updatedUser) {
                            var searchManager = new SearchManager(superAdminService.searchSuperAdmins);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Search Super Admin User",
                        feature: "LIST_SUPERADMINS"
                    }

                })
                .securedState('index.secured.superAdmin.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/superAdmin/templates/create.html',
                            controller: 'CreateSuperAdminController',
                        }
                    },
                    resolve: {
                        partnerList: ['SearchManager', 'partnerService', '$log', function(SearchManager, partnerService, $log) {
                            var searchManager = new SearchManager(partnerService.searchPartners);
                            searchManager.count = 0;
                            return searchManager.search();
                        }],
                        roleDetails: ['SearchManager', 'roleService', '$log', 'updatedUser', function(SearchManager, roleService, $log, updatedUser) {
                            var searchManager = new SearchManager(roleService.searchBURoleLists);
                            searchManager.count = 0;

                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Create Super Admin User",
                        feature: "ADD_SUPERADMIN_USER"
                    }
                })
                .securedState('index.secured.superAdmin.edit', {
                    url: '/edit/:id',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/superAdmin/templates/edit.html',
                            controller: 'EditSuperUserController',
                        }
                    },
                    data: {
                        screenName: "Edit Super Admin User",
                        feature: "EDIT_SUPERADMIN_USER"
                    },
                    resolve: {
                        partnerLists: ['SearchManager', 'partnerService', '$log', function(SearchManager, partnerService, $log) {
                            var searchManager = new SearchManager(partnerService.searchPartners);
                            searchManager.count = 0;
                            return searchManager.search();
                        }],
                        roleDetails: ['SearchManager', 'roleService', '$log', 'updatedUser', function(SearchManager, roleService, $log, updatedUser) {
                            var searchManager = new SearchManager(roleService.searchBURoleLists);
                            searchManager.count = 0;
                            return searchManager.search();

                        }],
                        adminuser: ['superAdminService', '$stateParams', 'updatedUser', '$state', '$rootScope', function(superAdminService, $stateParams, updatedUser, $state, $rootScope) {
                            return superAdminService.findSuperAdmin($stateParams.id);
                        }]
                    }
                });
        }
    ]);

})(angular.module('aet.screens.superAdmin'));

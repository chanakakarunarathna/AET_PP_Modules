(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.adminUser', {
                    abstract: true,
                    url: '/adminUser',
                    data: {
                        selectedTab: "AdminUsers"
                    }
                })
                .securedState('index.secured.adminUser.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/adminUser/templates/search.html',
                            controller: 'SearchAdminUserController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'adminUserService', '$log', 'updatedUser', function(SearchManager, adminUserService, $log, updatedUser) {
                            var searchManager = new SearchManager(adminUserService.searchAdminUser);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Search Admin User",
                        feature: "LIST_SEARCH_ADMIN_USERS"
                    }

                })
                .securedState('index.secured.adminUser.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/adminUser/templates/create.html',
                            controller: 'CreateAdminUserController',
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
                        screenName: "Create Admin User",
                        feature: "ADD_ADMIN_USER"
                    }
                })
                .securedState('index.secured.adminUser.edit', {
                    url: '/edit/:id',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/adminUser/templates/edit.html',
                            controller: 'EditAdminUserController',
                        }
                    },
                    data: {
                        screenName: "Edit Admin User",
                        feature: "EDIT_ADMIN_USER"
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
                        adminuser: ['adminUserService', '$stateParams', 'updatedUser', function(adminUserService, $stateParams, updatedUser) {
                            return adminUserService.findAdminUser($stateParams.id);
                        }]
                    }
                });
        }
    ]);

})(angular.module('aet.screens.adminUser'));

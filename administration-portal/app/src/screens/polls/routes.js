(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.polls', {
                    abstract: true,
                    url: '/polls',
                    data: {
                        selectedTab: "Polls"
                    }
                })
                .securedState('index.secured.polls.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/polls/templates/polls.html',
                            controller: 'PollsController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'adminService', '$log', 'updatedUser', function(SearchManager, adminService, $log, updatedUser) {
                            //console.log(adminService.searchAdmin());
                            var searchManager = new SearchManager(adminService.searchAdmin);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Search Polls"
                    }

                })
                .securedState('index.secured.polls.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/polls/templates/createServeysExternal.html',
                            controller: 'CreateServeysExternalController',
                        }
                    },
                    resolve: {
                        roleSearchManager: ['SearchManager', 'roleService', '$log', 'updatedUser', function(SearchManager, roleService, $log, updatedUser) {
                            var searchManager = new SearchManager(roleService.searchRoles);
                            searchManager.count = 0;
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Create Serveys External",
                        feature: "CREATE_CLIENT"
                    }
                })
                .securedState('index.secured.polls.edit', {
                    url: '/polls/:id',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/polls/templates/editServeysExternal.html',
                            controller: 'EditServeysExternalController',
                        }
                    },
                    data: {
                        screenName: "Edit Serveys External",
                        feature: "EDIT_Partner"
                    },
                    resolve: {
                        roleSearchManager: ['SearchManager', 'roleService', '$log', 'updatedUser', function(SearchManager, roleService, $log, updatedUser) {
                            var searchManager = new SearchManager(roleService.searchRoles);
                            searchManager.count = 0;
                            return searchManager.search();
                        }],
                        polls: ['adminService', '$stateParams', 'updatedUser', function(adminService, $stateParams, updatedUser) {
                            return adminService.findAdmin($stateParams.id);
                        }]
                    }
                });
        }
    ]);

})(angular.module('aet.screens.polls'));

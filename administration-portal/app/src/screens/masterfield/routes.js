(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.masterfield', {
                    abstract: true,
                    url: '/field',
                    data: {
                        selectedTab: "Fields",
                        screenName: "Fields"
                    }
                })
                .securedState('index.secured.masterfield.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/masterfield/templates/search.html',
                            controller: 'SearchMasterFieldController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'masterfieldService', '$log', 'updatedUser', function(SearchManager, masterfieldService, $log, updatedUser) {
                            var searchManager = new SearchManager(masterfieldService.searchMasterfields);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Create Field",
                        feature: "LIST_MASTERFIELD"
                    }

                })
                .securedState('index.secured.masterfield.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/masterfield/templates/create.html',
                            controller: 'CreateMasterFieldController',
                        }
                    },
                    data: {
                        screenName: "Create Field",
                        feature: "CREATE_MASTERFIELD"
                    }
                })
                .securedState('index.secured.masterfield.edit', {
                    url: '/edit/:masterFieldId',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/masterfield/templates/edit.html',
                            controller: 'EditMasterFieldController',
                        }
                    },
                    data: {
                        feature: "EDIT_MASTERFIELD",
                        screenName: "Edit Field"
                    },
                    resolve: {
                        masterfield: ['$stateParams', 'masterfieldService', 'updatedUser',
                            function($stateParams, masterfieldService, updatedUser) {
                                return masterfieldService.getMasterfield($stateParams.masterFieldId);
                            }
                        ]
                    }
                });


        }
    ]);

})(angular.module('aet.screens.masterfield'));

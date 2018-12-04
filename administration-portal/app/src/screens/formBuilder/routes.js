(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.formBuilder', {
                    abstract: true,
                    url: '/formBuilder',
                    data: {
                        selectedTab: "FormBuilder",
                        screenName: "Form Builder"
                    }
                })

            .securedState('index.secured.formBuilder.searchDraft', {
                    url: '/drafts',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/formBuilder/templates/search.html',
                            controller: 'SearchFormBuilderController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'formService', '$log', 'updatedUser', function(SearchManager, formService, $log, updatedUser) {
                            var searchManager = new SearchManager(formService.searchDraftForms);
                            return searchManager.search();
                        }],
                        formCategory: [function() {
                            return "Drafts";
                        }]
                    },
                    data: {
                        screenName: "Drafts",
                        feature: "LIST_FORM_BUILDER"
                    }

                })
                .securedState('index.secured.formBuilder.searchPublished', {
                    url: '/published',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/formBuilder/templates/search.html',
                            controller: 'SearchFormBuilderController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'formService', '$log', 'updatedUser', function(SearchManager, formService, $log, updatedUser) {
                            var searchManager = new SearchManager(formService.searchPublishedForms);
                            return searchManager.search();
                        }],
                        formCategory: [function() {
                            return "Published";
                        }]
                    },
                    data: {
                        screenName: "Published",
                        feature: "LIST_FORM_BUILDER"
                    }

                })
                .securedState('index.secured.formBuilder.searchDisabled', {
                    url: '/disabled',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/formBuilder/templates/search.html',
                            controller: 'SearchFormBuilderController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'formService', '$log', 'updatedUser', function(SearchManager, formService, $log, updatedUser) {
                            var searchManager = new SearchManager(formService.searchDisabledForms);
                            return searchManager.search();
                        }],
                        formCategory: [function() {
                            return "Disabled";
                        }]
                    },
                    data: {
                        screenName: "Disabled",
                        feature: "LIST_FORM_BUILDER"
                    }

                })
                .securedState('index.secured.formBuilder.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/formBuilder/templates/create.html',
                            controller: 'CreateFormBuilderController',
                        }
                    },
                    data: {
                        screenName: "Create Form",
                        feature: "CREATE_FORM_BUILDER"
                    },
                    resolve: {
                        masterFieldSearchManager: ['SearchManager', 'masterfieldService', '$log', 'updatedUser', function(SearchManager, masterfieldService, $log, updatedUser) {
                            var searchManager = new SearchManager(masterfieldService.searchMasterfields);

                            searchManager.count = 7;
                            searchManager.maxPages = 5;
                            searchManager.searchableFields = [{
                                name: 'name'
                            }, {
                                name: 'label'
                            }, {
                                name: 'tooltip'
                            }, ];

                            return searchManager.search();
                        }]
                    }
                })
                .securedState('index.secured.formBuilder.edit', {
                    url: '/edit/:permitId',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/formBuilder/templates/edit.html',
                            controller: 'EditFormBuilderController',
                        }
                    },
                    data: {
                        screenName: "Edit Form",
                        feature: "EDIT_FORM_BUILDER"
                    },
                    params: {
                        isactive: "",
                        ispublished: ""
                    },
                    resolve: {
                        masterFieldSearchManager: ['SearchManager', 'masterfieldService', '$log', 'updatedUser', function(SearchManager, masterfieldService, $log, updatedUser) {
                            var searchManager = new SearchManager(masterfieldService.searchMasterfields);
                            searchManager.count = 7;
                            searchManager.maxPages = 5;
                            searchManager.searchableFields = [{
                                name: 'name'
                            }, {
                                name: 'label'
                            }, {
                                name: 'tooltip'
                            }, ];

                            return searchManager.search();
                        }],

                        permit: ['formService', '$stateParams', 'updatedUser', function(formService, $stateParams, updatedUser) {
                            var searchObject = {
                                'isactive': $stateParams.isactive,
                                'ispublished': $stateParams.ispublished
                            }
                            return formService.getForm($stateParams.permitId, searchObject);
                        }]
                    }
                });


        }
    ]);

})(angular.module('aet.screens.formBuilder'));

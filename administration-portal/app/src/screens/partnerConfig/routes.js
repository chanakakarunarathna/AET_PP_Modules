(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.partnerConfig', {
                    abstract: true,
                    url: '/partnerConfig',
                    data: {
                        selectedTab: "PartnerConfigs",
                        screenName: "PartnerConfigs"
                    }
                })
                .securedState('index.secured.partnerConfig.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerConfig/templates/partnerConfig.html',
                            controller: 'PartnerConfigController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'partnerConfigService', '$log', 'updatedUser', function(SearchManager, partnerConfigService, $log, updatedUser) {
                            var searchManager = new SearchManager(partnerConfigService.searchPartnerConfig);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "View Partner Config",
                        feature: "VIEW_PARTNER_CONFIG"
                    }

                })
                .securedState('index.secured.partnerConfig.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerConfig/templates/create.html',
                            controller: 'CreatePartnerConfigController',
                        }
                    },
                    resolve: {

                    },
                    data: {
                        screenName: "Create Partner Config",
                        feature: "ADD_PARTNER_CONFIG"
                    }
                })
                .securedState('index.secured.partnerConfig.edit', {
                    url: '/edit/:partnerConfigId',
                    params: {
                        resultRoute: undefined,
                        resultParams: undefined
                    },
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerConfig/templates/edit.html',
                            controller: 'EditPartnerConfigController',
                        }
                    },
                    data: {
                        feature: "EDIT_PARTNER_CONFIG",
                        screenName: "Edit Partner Config"
                    },
                    resolve: {
                    	partnerConfig: ['$stateParams', 'partnerConfigService', 'updatedUser', '$log',
                            function($stateParams, partnerConfigService, updatedUser, $log) {

                                return partnerConfigService.findPartnerConfig($stateParams.partnerConfigId);
                            }
                        ]
                    }
                });


        }
    ]);

})(angular.module('aet.screens.partnerConfig'));

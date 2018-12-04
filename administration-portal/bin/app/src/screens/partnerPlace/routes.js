(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.partnerPlace', {
                    abstract: true,
                    url: '/partnerPlace',
                    data: {
                        selectedTab: "PartnerPlaces",
                        screenName: "PartnerPlaces"
                    }
                })
                .securedState('index.secured.partnerPlace.search', {
                    url: '',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerPlace/templates/search.html',
                            controller: 'SearchPartnerPlaceController',
                        }
                    },
                    resolve: {
                        searchManager: ['SearchManager', 'partnerPlaceService', '$log', 'updatedUser', function(SearchManager, partnerPlaceService, $log, updatedUser) {
                            var searchManager = new SearchManager(partnerPlaceService.searchAllPartnerPlace);
                            return searchManager.search();
                        }]
                    },
                    data: {
                        screenName: "Search Partner Place",
                        feature: "LIST_SEARCH_PARTNER_PLACE"
                    }

                })
                .securedState('index.secured.partnerPlace.create', {
                    url: '/create',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerPlace/templates/create.html',
                            controller: 'CreatePartnerPlaceController',
                        }
                    },
                    resolve: {
                        adminUserDetails: ['adminUserService', 'updatedUser', '$log', function(adminUserService, updatedUser, $log) {
                            return adminUserService.searchAdminUserByPartnerId({
                                count: 0,
                                page: 0,
                                sme: false
                            });
                        }],
                        places: ['$stateParams', 'placesService',
                            function($stateParams, placesService) {
                                var searchObj = {q: '', searchby: 'name', count: 20 };
                                return placesService.searchByQueryPlacess(searchObj);
                            }
                        ]
                    },
                    data: {
                        screenName: "Create Partner Place",
                        feature: "ADD_PARTNER_PLACE"
                    }
                })
                .securedState('index.secured.partnerPlace.edit', {
                    url: '/edit/:partnerPlaceId',
                    params: {
                        resultRoute: undefined,
                        resultParams: undefined
                    },
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/partnerPlace/templates/edit.html',
                            controller: 'EditPartnerPlaceController',
                        }
                    },
                    data: {
                        feature: "EDIT_PARTNER_PLACE",
                        screenName: "Edit Partner Place"
                    },
                    resolve: {
                    	partnerPlace: ['$stateParams', 'partnerPlaceService', 'updatedUser', '$log', 'partnerPlaceDetails',
                            function($stateParams, partnerPlaceService, updatedUser, $log, partnerPlaceDetails) {
                                return partnerPlaceService.findPartnerPlace($stateParams.partnerPlaceId);
                            }
                        ],
                        adminUserDetails: ['updatedUser', 'adminUserService', '$log', 'partnerPlace', '$stateParams',
                            function(updatedUser, adminUserService, $log, partnerPlace, $stateParams) {
                                return adminUserService.searchAdminUserByPartnerId({
                                    count: 0,
                                    page: 0,
                                    sme: false
                                });
                            }
                        ],
                        places: ['$stateParams', 'placesService', 'partnerPlace',
                            function($stateParams, placesService, partnerPlace) {
                                return placesService.getPlaces(partnerPlace.parentId);
                            }
                        ]
                    }
                });


        }
    ]);

})(angular.module('aet.screens.partnerPlace'));

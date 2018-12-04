(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .securedState('index.secured.booking', {
                    abstract: true,
                    url: '/booking',
                    data: {
                        selectedTab: "Bookings"
                    }
                })
                .securedState('index.secured.booking.search', {
                    url: '/search',
                    params: {
                    	searchEnabled: false
                    },
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/booking/templates/search.html',
                            controller: 'SearchBookingController',
                        }
                    },
                    resolve: {
                        previousState: ["$state",function ($state) {
                                var currentStateData = {
                                    Name: $state.current.name,
                                    Params: $state.params,
                                    URL: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }
                        ]
                    },
                    data: {
                        screenName: "Search Bookings",
                        selectedTab: "Bookings",
                        feature: "LIST_PARTNER_BOOKINGS"
                    }

                })
                .securedState('index.secured.booking.report', {
                	url: '/report',
 					params: {
                    	searchEnabled: false
                    },
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/booking/templates/report.html',
                            controller: 'ReportBookingController',
                        }
                    },
                    resolve: {
                        previousState: ["$state",function ($state) {
                                var currentStateData = {
                                    Name: $state.current.name,
                                    Params: $state.params,
                                    URL: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }
                        ]
                    },
                    data: {
                        screenName: "Booking Issue Report",
                        selectedTab: "Reports",
                        feature: "LIST_PARTNER_BOOKINGS"
                    }
                })
                .securedState('index.secured.booking.view', {
                    url: '/view/:id',
                    views: {
                        'main@index': {
                            templateUrl: 'src/screens/booking/templates/view.html',
                            controller: 'ViewBookingController',
                        }
                    },
                    data: {
                        screenName: "View Partner Booking",
                        feature: "GET_PARTNER_BOOKING"
                    },
                    resolve: {
                        booking: ['bookingService', '$stateParams', 'updatedUser', function(bookingService, $stateParams, updatedUser) {
                            return bookingService.findBooking($stateParams.id);
                        }]
                    }
                }).state('voucher', {
                    url: '/booking/view/:id/voucher',
                    params: {
                        htmlContent: undefined
                    },
                    views: {
                        'index': {
                        	templateUrl: 'src/screens/booking/templates/voucher.html',
                        	controller: 'voucherController',
                        }
                    },
                    data: {
                        feature: "GET_PARTNER_BOOKING",
                        screenName: "View Booking Voucher"
                    }
                });
        }
    ]);

})(angular.module('aet.screens.booking'));

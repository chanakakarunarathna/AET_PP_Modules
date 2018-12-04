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
                    url: '',
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
                });
        }
    ]);

})(angular.module('aet.screens.booking'));

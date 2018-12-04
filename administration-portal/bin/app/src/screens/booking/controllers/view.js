(function(module) {

    module.controller('ViewBookingController', ['$scope', '$rootScope', 'updatedUser', 'bookingService', 'partnerDetails', 'partnerPlaceDetails', 'localStorageService', 'Booking', 'alertsService', '$state', '$log', 'userDetails', 'booking', 'modalService', 'PartnerRole',
        function($scope, $rootScope, updatedUser, bookingService, partnerDetails, partnerPlaceDetails, localStorageService, Booking, alertsService, $state, $log, userDetails, booking, modalService, PartnerRole) {

            $scope.booking = angular.copy(booking);
            $scope.bookingSummaryOverallStatusFound = false;
            
            $scope.publishEventType = '';
            if(booking.bookingSummary!=undefined && booking.bookingSummary.overallStatus.status=='SUCCESS'){
            	$scope.bookingSummaryOverallStatusFound = true;
            	$scope.publishEventType = 'Confirmation';
            }else if(booking.bookingSummary!=undefined && booking.bookingSummary.overallStatus.status=='CANCELLED'){
            	$scope.bookingSummaryOverallStatusFound = true;
            	$scope.publishEventType = 'Cancellation';
            }else if(booking.bookingSummary!=undefined && booking.bookingSummary.overallStatus.status=='PENDING'){
            	$scope.bookingSummaryOverallStatusFound = true;
            	$scope.publishEventType = 'Pending';
            }
            
            $scope.booking.bookingSummary.overallStatus.status = booking.bookingSummary.overallStatus.status.replace(/_/g, " ");
            
            $scope.tab = {};
            $scope.tab[1] = false;
            $scope.tab[2] = true;
            $scope.tab[3] = true;
            $scope.tab[4] = true;

            $scope.total = $scope.booking.total.currency + ' ' + $scope.booking.total.finalTotal;
            $scope.bookerDetailsName = $scope.booking.bookerDetails.title + ' ' + $scope.booking.bookerDetails.firstName +
                                        ' ' + $scope.booking.bookerDetails.lastName ;

            $scope.go = function(type, currentTab) {
                var holder = 0,
                    length = 0;
                if (currentTab) {
                    angular.forEach($scope.tab, function(tab, key) {
                        length++;
                        $scope.tab[key] = true;
                    });
                    if (holder < length && type === 'next') {
                        holder = ++currentTab;
                    } else if (holder < length && type === 'previous') {
                        holder = --currentTab;
                    } else {
                        holder = 1;
                    }
                    $scope.tab[holder] = false;
                }

            };

            $scope.cancelBooking = function(booking) {

                var modalInstance = modalService.bookingCancel(booking);
                modalInstance.result.then(function(result) {});

            };
            
            $scope.viewVoucher = function(voucherUrl) {

                var modalInstance = modalService.viewVoucher(voucherUrl);
                modalInstance.result.then(function(result) {});

            };
            
            $scope.publishEvent = function(booking) {
            	bookingService.publishEvent(booking).then(function(data) {
	                alertsService.addAlert({
	                  title: 'Success',
	                  message: publishEventType+ "" + booking.bookingReference + '" successfully re-sent.',
	                  type: 'success',
	                  removeOnStateChange: 2
	                });
	                $rootScope.loader = false;
                },
                function(err) {
	                $rootScope.loader = false;
	                console.error('Could not resend email', err);
	                if(err.status == 500){
	                    alertsService.clear(); //clear the error message
	                }
	                alertsService.addAlert({
                        title: 'Error',
                        message: "Could not re-send '" + publishEventType + "" + booking.bookingReference + ".'",
                        type: 'error'
                    });
                });
            };
        }
    ]);

})(angular.module('aet.screens.booking'));

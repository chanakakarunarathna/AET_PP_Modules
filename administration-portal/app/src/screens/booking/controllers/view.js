(function(module) {

    module.controller('ViewBookingController', ['$scope', '$rootScope', 'updatedUser', 'bookingService', 'partnerDetails', 'partnerPlaceDetails', 'localStorageService', 'Booking', 'alertsService', '$state', '$log', 'userDetails', 'booking', 'modalService', 'PartnerRole', '$window', 'security',
        function($scope, $rootScope, updatedUser, bookingService, partnerDetails, partnerPlaceDetails, localStorageService, Booking, alertsService, $state, $log, userDetails, booking, modalService, PartnerRole, $window, security) {

            $rootScope.bookingObj = angular.copy(booking);
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
            }else if(booking.bookingSummary!=undefined && booking.bookingSummary.overallStatus.status=='BOOKING_REJECTED'){
            	$scope.bookingSummaryOverallStatusFound = true;
            	$scope.publishEventType = 'Rejected';
            }
            
            $rootScope.bookingObj.bookingSummary.overallStatus.status = booking.bookingSummary.overallStatus.status.replace(/_/g, " ");

            $scope.manualInterventionDetailFound = false;
            var data = $rootScope.bookingObj.bookingEvents.find( function( ele ) { 
            	if(ele.manualInterventionDetail!=undefined && ele.manualInterventionDetail!=null && ele.manualInterventionDetail.manualInterventionRequired==true ){
            		$scope.manualInterventionDetailFound = true;
            	}
            } );

            $scope.tab = {};
            $scope.tab[1] = false;
            $scope.tab[2] = true;
            $scope.tab[3] = true;
            $scope.tab[4] = true;

            $scope.subTab = {};
            $scope.subTab[0] = false;

            for (var key = 1; key < $rootScope.bookingObj.bookingEvents.length; key++) {
            	$scope.subTab[key] = true;
            }

            $scope.total = $rootScope.bookingObj.total.currency + ' ' + $rootScope.bookingObj.total.finalTotal;
            $scope.bookerDetailsName = $rootScope.bookingObj.bookerDetails.title + ' ' + $rootScope.bookingObj.bookerDetails.firstName +
                                        ' ' + $rootScope.bookingObj.bookerDetails.lastName ;

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

            $scope.cancelBooking = function(booking, manual) {

                var modalInstance = modalService.bookingCancel(booking, manual);
                modalInstance.result.then(function(result) {});

            };

            $scope.viewVoucher = function(voucherType, voucherUrls) {
                var modalInstance = modalService.viewVoucher(voucherType, voucherUrls);
                modalInstance.result.then(function(result) {});
            };

            $scope.viewVoucherHTML = function(id) {
            	var myWindow = $window;
            	var hasPermission = security.isAuthorized('GET_BOOKING_VOUCHER');
            	var myData={hasPermission:hasPermission};
            	myWindow.localStorage.myData=JSON.stringify(myData);
            	window.open($state.href('voucher', {id: id}, {absolute: false, inherit: true}));
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
                        message: "Could not resend '" + publishEventType + "" + booking.bookingReference + ".'",
                        type: 'error'
                    });
                });
            };
            
            
            $scope.resendEmail = function(booking, emailType) {
            	var modalInstance = modalService.resendEmailModal(booking, emailType);
                modalInstance.result.then(function(result) {});            	
            };

            $scope.cancellationReasons = [
            	{
                    "cancelCode": "00",
                    "cancelDescription": "Testing"
                },
                {
                    "cancelCode": "51",
                    "cancelDescription": "Airline Flight Cancellation - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "52",
                    "cancelDescription": "Airline Schedule Change - Unacceptable to Customer/Traveller"
                },
                {
                    "cancelCode": "53",
                    "cancelDescription": "Death - Customer/Traveller or Immediate Family"
                },
                {
                    "cancelCode": "54",
                    "cancelDescription": "Jury Duty/Court Summons - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "55",
                    "cancelDescription": "Discretionary Cancellation (Viator Use Only)"
                },
                {
                    "cancelCode": "56",
                    "cancelDescription": "Medical Emergency/Hospitalization - Customer/Traveller or Immediate Family"
                },
                {
                    "cancelCode": "57",
                    "cancelDescription": "Military Service - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "58",
                    "cancelDescription": "National Disaster (Insurrection, Terrorism, War) -Affects Customer/Traveller"
                },
                {
                    "cancelCode": "59",
                    "cancelDescription": "Natural Disaster (Earthquake, Fire, Flood) - AffectsCustomer/Traveller"
                },
                {
                    "cancelCode": "62",
                    "cancelDescription": "Service Complaint - Denied Trip Add On Service"
                },
                {
                    "cancelCode": "63",
                    "cancelDescription": "Transport Strike/Labor Dispute - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "66",
                    "cancelDescription": "Trip Add On Supplier Cancellation"
                },
                {
                    "cancelCode": "71",
                    "cancelDescription": "Credit Card Fraud"
                },
                {
                    "cancelCode": "72",
                    "cancelDescription": "Car Segment Cancellation - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "73",
                    "cancelDescription": "Package Segment Cancellation - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "74",
                    "cancelDescription": "Hotel Segment Cancellation - Affects Customer/Traveller"
                },
                {
                    "cancelCode": "77",
                    "cancelDescription": "Re-book"
                },
                {
                    "cancelCode": "78",
                    "cancelDescription": "Duplicate Purchase"
                },
                {
                    "cancelCode": "82",
                    "cancelDescription": "Honest Mistake - Incorrect Purchase"
                },
                {
                    "cancelCode": "87",
                    "cancelDescription": "Non-Refundable Cancellation (Outside 2 Days of Travel/Not Cencellation Event)"
                },
                {
                    "cancelCode": "88",
                    "cancelDescription": "Non-Refundable Cancellation (Within 2 Days of Travel)"
                },
                {
                    "cancelCode": "98",
                    "cancelDescription": "Customer Service/Technical Support Response Outside Time Limit"
                },
                {
                    "cancelCode": "99",
                    "cancelDescription": "Duplicate Processing"
                }
            ];

            if($rootScope.bookingObj.bookingSummary.cancellationReasonCode != null){
            	_.any($scope.cancellationReasons, function(cancellationReason) {
    					if( cancellationReason.cancelCode == $rootScope.bookingObj.bookingSummary.cancellationReasonCode){
    						$scope.cancellationReason = cancellationReason.cancelDescription;
    					}
    			});
            }
        }
    ]);

})(angular.module('aet.screens.booking'));

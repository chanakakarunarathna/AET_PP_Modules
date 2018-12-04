(function(module) {

    module.controller('deleteBookingController', ['$scope', 'bookingService', 'alertsService', '$state', '$log', '$rootScope', 'userDetails', '$modalInstance', '$q',
        function($scope, bookingService, alertsService, $state, $log, $rootScope, userDetails, $modalInstance, $q) {

	    	$scope.operatorOptions = [{
	            "key": "AMOUNT",
	            "value": "Amount"
	        }, {
	            "key": "PERCENTAGE",
	            "value": "Percentage"
	        }];

	    	$scope.refundTypeOptions = [{
	            "key": "FULL",
	            "value": "Full Refund"
	        }, {
	            "key": "RULES",
	            "value": "Rule Based"
	        }, {
	            "key": "AMOUNT",
	            "value": "Partial Refund"
	        }];

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

            $scope.listBooking = function() {
            	if(JSON.parse(sessionStorage.filteredResults) != null){
            		$scope.filteredResults = JSON.parse(sessionStorage.filteredResults);
            		$state.go('index.secured.booking.search', {
                        searchEnabled: true
                    }, {
                        reload: true
                    }).then(function() {
                        $rootScope.hideLoader();
                    });
            	}else if(JSON.parse(sessionStorage.filteredReportResults) != null){
            		$scope.filteredReportResults = JSON.parse(sessionStorage.filteredReportResults);
            		$state.go('index.secured.booking.report', {
                        searchEnabled: true
                    }, {
                        reload: true
                    }).then(function() {
                        $rootScope.hideLoader();
                    });
            	}
            };

            $scope.ok = function() {
                cancelBooking();
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };

            $scope.manualCancel = function() {
            	$rootScope.showLoader("Please Wait...");
            	bookingService.manualCancelBooking($scope.booking).then(function(data) {
	                alertsService.addAlert({
	                  title: 'Success',
	                  message: 'Booking "' + $scope.booking.bookingId + '" successfully cancelled.',
	                  type: 'success',
	                  removeOnStateChange: 2
	                });
	                $scope.listBooking();
                    $modalInstance.dismiss('cancel');
                },
            	function(err) {
            		$modalInstance.dismiss('cancel');
                	$rootScope.hideLoader();
	                if(err.status == 500){
	                    alertsService.clear(); //clear the error message
	                }
	                alertsService.addAlert({
                        title: 'Error',
                        message: "Could not cancel Booking '" + $scope.booking.bookingId + ".'",
                        type: 'error'
                    });
                });
            }

            function cancelBooking() {
                return $q(function(resolve, reject) {
                    $rootScope.showLoader("Please Wait...");
                    // setting the loggedIn user as partner place creator

                    bookingService.cancelBooking($scope.booking).then(function(data) {

                        alertsService.addAlert({
                            title: 'Success',
                            message: 'Booking "' + $scope.booking.bookingId + '" successfully cancelled.',
                            type: 'success',
                            removeOnStateChange: 2
                        });
                        $scope.listBooking();
                        $modalInstance.dismiss('cancel');
                        resolve(true);
                    }, function(err) {
                    	$modalInstance.dismiss('cancel');
                    	$rootScope.hideLoader();
                        reject(true);
                    });
                });
            }

            $scope.preventPastingCharacters = function(e){
            	var val = e.originalEvent.clipboardData.getData('text/plain');
                if (!(/^\d+$/.test(val))){
                    e.preventDefault();
    			}
    		};

    		$scope.clearPartialOptionInputValues = function(item){
    			if(item.key=="PERCENTAGE"){
    				$scope.booking.cancellationPercentage = null;
    			}else if(item.key=="AMOUNT"){
    				$scope.booking.cancellationAmount = null;
    			}
    		};

    		$scope.clearRefundTypeInputValues = function(item){
    			if(item.key=="FULL" || item.key=="RULES"){
    				$scope.booking.operator = null;
    				$scope.booking.cancellationPercentage = null;
    				$scope.booking.cancellationAmount = null;
    			}
    		};

    		$scope.isValidForm  = function () {
    			if($scope.manual==true && $scope.booking.cancellationReason != undefined && $scope.booking.cancellationReason != null){
    				if($scope.booking.refundType != undefined && ($scope.booking.refundType.key=='FULL' || $scope.booking.refundType.key=='RULES')){
    					return false;
        			}else if($scope.booking.refundType!= undefined && $scope.booking.refundType.key=='AMOUNT'){
        				if($scope.booking.operator!= undefined && $scope.booking.operator.key=='AMOUNT' && $scope.booking.cancellationAmount != null && $scope.booking.cancellationAmount != ''){
        					return false;
        				}else if($scope.booking.operator!= undefined && $scope.booking.operator.key=='PERCENTAGE' && $scope.booking.cancellationPercentage != null && $scope.booking.cancellationPercentage != ''){
        					return false;
        				}else{
        					return true;
        				}
        			}else{
        				return true;
        			}
				}else if($scope.manual!=true && $scope.booking.cancellationReason != undefined && $scope.booking.cancellationReason != null){
					return false;
				}else {
					return true;
				}
    	    };

        }
    ]);

})(angular.module('aet.modals'));

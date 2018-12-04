(function(module) {

    module.controller('resendEmailModalController', ['$scope', '$timeout', 'bookingService', 'alertsService', '$rootScope', '$modalInstance', '$q',
        function($scope, $timeout, bookingService, alertsService, $rootScope, $modalInstance, $q) {

    	
            $scope.ok = function() {
                resendEmail();
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };

            function resendEmail() {
                return $q(function(resolve, reject) {
                    $rootScope.showLoader("Please Wait...");
                    var email = $scope.email !=null ? $scope.email : null; 
                    bookingService.resendEmail($scope.booking, $scope.emailType, email).then(function(data) {
                    $timeout( function(){
                    	bookingService.findBooking($scope.booking.bookingId).then(function(response) {
                    		$rootScope.bookingObj = response;
                    		$rootScope.loader = false;
	                		alertsService.addAlert({
          	                  title: 'Success',
          	                  message: $scope.emailType+ " mail for " + $scope.booking.bookingReference + ' successfully re-sent.',
          	                  type: 'success',
          	                  removeOnStateChange: 2
          	                });
                        });
                    }, 2000 );
                    },
                    function(err) {
    	                $rootScope.loader = false;
    	                console.error('Could not resend email', err);
    	                if(err.status == 500){
    	                    alertsService.clear(); //clear the error message
    	                }
    	                alertsService.addAlert({
                            title: 'Error',
                            message: "Could not re-send '" + $scope.emailType + " mail for " + $scope.booking.bookingReference + ".'",
                            type: 'error'
                        });
                    });
                    $modalInstance.dismiss('cancel');
                });
            }
        }
    ]);

})(angular.module('aet.modals'));

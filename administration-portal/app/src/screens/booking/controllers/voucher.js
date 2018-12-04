(function(module) {

    module.controller('voucherController', ['$scope', '$state', '$stateParams', '$log', '$rootScope', '$q', '$window', 'bookingService', 'localStorageService',
        function($scope, $state, $stateParams, $log, $rootScope, $q, $window, bookingService, localStorageService ) {
    	
    		var myWindow= $window;
    		if(myWindow.localStorage.myData != undefined || (localStorage["userId"+localStorageService.get('userId')]!=undefined 
    				&& JSON.parse(localStorage["userId"+localStorageService.get('userId')])==true)) {
    			$rootScope.showLoader("Loading...");
        		$scope.htmlContent = null;
        		if(myWindow.localStorage.myData != undefined && JSON.parse(myWindow.localStorage.myData).hasPermission == true){
        			localStorage["userId"+localStorageService.get('userId')] = JSON.stringify(JSON.parse(myWindow.localStorage.myData).hasPermission);
        		}
    	    	bookingService.getVoucherContent($stateParams.id).then(function(response) {
    	        	if(response != null){
    					$scope.htmlContent = response ;
    				}else{
    					$scope.htmlContent = "No Content Found";
    				}
                    $rootScope.hideLoader();
    	        }, function(err) {
    	        	$scope.htmlContent = "No Content Found";
    	      	  	$rootScope.hideLoader();
    	        });
    		}else{
    			$scope.htmlContent = "No Permission";
	      	  	$rootScope.hideLoader();
    		}
    		localStorage.removeItem("myData");			
        }
    ]);

})(angular.module('aet.screens.booking'));

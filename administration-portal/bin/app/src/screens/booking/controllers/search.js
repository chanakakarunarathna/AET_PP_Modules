(function(module) {

  module.controller('SearchBookingController', ['$scope', 'bookingService', '$state', 'alertsService', 'modalService', 'partnerDetails','$filter','$rootScope','previousState',
    function($scope, bookingService, $state, alertsService, modalService, partnerDetails, $filter, $rootScope, previousState) {    

	  $scope.booking = [];
	  $scope.booking.startactivitydate = undefined;
      $scope.booking.endactivitydate = undefined;
      $scope.booking.startbookingdate = undefined;
      $scope.booking.endbookingdate = undefined;
      
      $scope.resultDetails = {};
      $scope.results = undefined;
      
      $scope.phoneNumberRegex = /^\+?\d{2}[- ]?\d{3}[- ]?\d{5}$/;
      
      $scope.reset = function() {
    	  	$scope.searchBookingForm.$setPristine();
			$scope.searchBookingForm.$setUntouched();
			$scope.searchBookingForm.$setValidity();
			$scope.searchBookingForm.$showError = false;
			angular.forEach( $scope['searchBookingForm'], function( field, fieldName ) {
				if( fieldName[ 0 ] === '$' ) {
		            return;
		        }
				if(fieldName != 'searchoperator'){
					 $scope.booking[fieldName] = null;
				}
		    } );
			$scope.booking.searchoperator = {key: 'and', value: 'AND'};
			$scope.filteredResults =  [];
			$scope.resultDetails  =  [];
			sessionStorage.filteredResults = null;
			sessionStorage.resultDetails = null;
			sessionStorage.bookingParams = null;
      };
    
      $scope.booking.searchoperator = {key: 'and', value: 'AND'};
      $scope.searchOperatorOptions = [{
          "key": "and",
          "value": "AND"
        }, {
          "key": "or",
          "value": "OR"
        }];
      
      $scope.filteredResults = [], $scope.currentPage = 0, $scope.numPerPage = 15, $scope.maxSize = 5;

      if(previousState.URL!=null && previousState.URL.includes('#/booking/view/') && JSON.parse(sessionStorage.filteredResults) != null){
		$scope.filteredResults = JSON.parse(sessionStorage.filteredResults);
		$scope.resultDetails = JSON.parse(sessionStorage.resultDetails);
		$scope.booking = JSON.parse(sessionStorage.bookingParams);
		$scope.currentPage = JSON.parse(sessionStorage.bookingParams).page +1; 
		var searchoperator  = JSON.parse(sessionStorage.bookingParams).searchoperator;
		$scope.booking.searchoperator = {key: searchoperator, value: searchoperator.toUpperCase()};
		$scope.booking.startactivitydate = ($scope.booking.startactivitydate!= "") ? formatDate($scope.booking.startactivitydate) : undefined;
		$scope.booking.endactivitydate = ($scope.booking.endactivitydate!= "") ? formatDate($scope.booking.endactivitydate) : undefined;
		$scope.booking.startbookingdate = ($scope.booking.startbookingdate!= "") ? formatDate($scope.booking.startbookingdate) : undefined;
		$scope.booking.endbookingdate= ($scope.booking.endbookingdate!= "") ? formatDate($scope.booking.endbookingdate) : undefined;
      }else{
		sessionStorage.filteredResults = null;
		sessionStorage.resultDetails = null;
		sessionStorage.bookingParams = null;
      }

      $scope.viewBooking = function(booking) {
            $state.go('index.secured.booking.view', {
                id: booking.bookingId
            }, {
                reload: true
            });
        };

      $scope.deleteBooking = function(Booking) {

        var modalInstance = modalService.deleteModal(Booking.firstName);
        modalInstance.result.then(function() {
          bookingService.deleteBooking(Booking.id).then(function(data) {
            alertsService.addAlert({
              title: 'Success',
              message: "Successfully deleted '" + Booking.firstName + "'",
              type: 'success'
            });
            searchManager.delete(Booking.id);
          }, function(err) {
            alertsService.addAlert({
              title: 'Error',
              message: "Could not delete '" + Booking.firstName + "'",
              type: 'error'
            });
          });
        });

      };

      $scope.editBooking = function(Booking) {
        $state.go('index.secured.booking.edit', {
          id: Booking.id
        });
      };
      
      function formatDate(date) {
          var formattedDate;
          date = new Date(date);
          formattedDate = $filter('date')(date, 'yyyy-MM-dd');
          return formattedDate;
      }
      
      function getDaysDifference(date1, date2) {
          if (angular.isUndefined(date1))
              date1 = null;
          if (angular.isUndefined(date2))
              date2 = null;
          var d1 = new Date($filter('date')(date1, 'yyyy-MM-dd'));
          var d2 = new Date($filter('date')(date2, 'yyyy-MM-dd'));
          return ((d1 - d2) / (1000 * 3600 * 24)).toFixed(0);
      }

      function addOneDayToDate(date) {
          var dt = new Date(date);
          dt.setDate(dt.getDate() + 1);
          return dt;
      };

      $scope.onStartActivityDateChange = function() {
          $scope.minEndActivityDate = $scope.booking.startactivitydate;
          if ($scope.booking.startactivitydate != null && getDaysDifference($scope.booking.endactivitydate, $scope.booking.startactivitydate) < 1) {
              $scope.booking.endactivitydate = addOneDayToDate($scope.booking.startactivitydate);
          }
          $scope.minEndActivityDate = addOneDayToDate($scope.minEndActivityDate);
      };
      
      $scope.onStartBookingDateChange = function() {
          $scope.minEndBookingDate = $scope.booking.startbookingdate;
          if ($scope.booking.startbookingdate != null && getDaysDifference($scope.booking.endbookingdate, $scope.booking.startbookingdate) < 1) {
              $scope.booking.endbookingdate = addOneDayToDate($scope.booking.startbookingdate);
          }
          $scope.minEndBookingDate = addOneDayToDate($scope.minEndBookingDate);
      };
      
      $scope.openStartActivity = function($event) {
    	  $scope.openedStartActivity = true;
      };

      $scope.openEndActivity = function($event) {
          $scope.openedEndActivity = true;
      };
      
      $scope.openStartBooking = function($event) {
          $scope.openedStartBooking = true;
      };

      $scope.openEndBooking = function($event) {
          $scope.openedEndBooking = true;
      };
      
      $scope.dateOptions = {
              formatYear: 'yy',
              startingDay: 1,
              showWeeks: false
          };
      
      $scope.getLength = function(obj) {
          return Object.keys(obj).length;
      }
     
      
      $scope.setBookingStatus = function(value) {
      	var string = value.replace(/_/g, " ");
      	return string;
      }

      $scope.formats = ['dd-MMMM-yyyy', 'yyyy-MM-dd', 'dd.MM.yyyy', 'shortDate', 'yyyy-MM-dd HH:mm:ss'];
      
      $scope.format = $scope.formats[1];

      $scope.isValidSearchBookingForm  = function () {
    	  if(($scope.booking!= undefined) && 
			 ($scope.booking.searchoperator != undefined && $scope.booking.searchoperator != "") && 
			 (($scope.booking.confnumber != undefined && $scope.booking.confnumber != "") ||
			  ($scope.booking.firstname != undefined && $scope.booking.firstname != "") || 
			  ($scope.booking.lastname != undefined && $scope.booking.lastname !="") || 
			  ($scope.booking.email != undefined && $scope.booking.email !="") || 
    		  ($scope.booking.startactivitydate != undefined && $scope.booking.startactivitydate !="") ||
    		  ($scope.booking.endactivitydate != undefined && $scope.booking.endactivitydate !="") || 
    		  ($scope.booking.startbookingdate != undefined && $scope.booking.startbookingdate !="") ||
    		  ($scope.booking.endbookingdate != undefined && $scope.booking.endbookingdate !="") || 
    		  ($scope.booking.geolocation != undefined && $scope.booking.geolocation !="") ||
    		  ($scope.booking.productid != undefined && $scope.booking.productid !="") || 
    		  ($scope.booking.vendor != undefined && $scope.booking.vendor != "") || 
    		  (($scope.booking.phonenumber != undefined && $scope.booking.phonenumber !="" &&
    				  $scope.booking.countryisocode != undefined && $scope.booking.countryisocode !=""))
			 )){
    		  if(($scope.booking.phonenumber != undefined && $scope.booking.phonenumber !="" && $scope.booking.countryisocode == undefined) || 
    			 ($scope.booking.countryisocode != undefined && $scope.booking.countryisocode !="" && $scope.booking.phonenumber == undefined) ||
    			 ($scope.booking.startactivitydate != undefined && $scope.booking.startactivitydate !="" && $scope.booking.endactivitydate == undefined) ||
    			 ($scope.booking.endactivitydate != undefined && $scope.booking.endactivitydate !="" && $scope.booking.startactivitydate == undefined) ||
    			 ($scope.booking.startbookingdate != undefined && $scope.booking.startbookingdate !="" && $scope.booking.endbookingdate == undefined) ||
    			 ($scope.booking.endbookingdate != undefined && $scope.booking.endbookingdate !="" && $scope.booking.startbookingdate == undefined) 
    		  	){
	  				return false;
    		  }
        	  return true;
          }else{
              return false;
          }
      };
      
      $scope.retrieveBookingsReport = function(page) {
    	  var bookingObject = {
    	          'confnumber'			: ($scope.booking.confnumber!=null) ? $scope.booking.confnumber : "",
    	          'firstname' 			: ($scope.booking.firstname!=null) ? $scope.booking.firstname : "",
    	          'lastname'			: ($scope.booking.lastname!=null) ? $scope.booking.lastname : "",
        		  'phonenumber'			: ($scope.booking.phonenumber!=null) ? $scope.booking.phonenumber : "",
        		  'countryisocode'		: ($scope.booking.countryisocode!=null) ? $scope.booking.countryisocode : "",
    	          'email'				: ($scope.booking.email!=null) ? $scope.booking.email : "",
    	          'startactivitydate'	: ($scope.booking.startactivitydate!=null) ? formatDate($scope.booking.startactivitydate) : "",
    	          'endactivitydate' 	: ($scope.booking.endactivitydate!=null) ? formatDate($scope.booking.endactivitydate) : "",
    	          'startbookingdate'	: ($scope.booking.startbookingdate!=null) ? formatDate($scope.booking.startbookingdate) : "",
    	          'endbookingdate'		: ($scope.booking.endbookingdate!=null) ? formatDate($scope.booking.endbookingdate) : "",
    	          'geolocation'			: ($scope.booking.geolocation!=null) ? $scope.booking.geolocation : "",
    	          'productid'			: ($scope.booking.productid!=null) ? $scope.booking.productid : "",
    	          'vendor'				: ($scope.booking.vendor!=null) ? $scope.booking.vendor : "",
    	          'searchoperator'		: ($scope.booking.searchoperator!=null) ? $scope.booking.searchoperator.key : "",
    	          'page'				: (page-1) ,
    	          'count'				: $scope.numPerPage
    	        };
    	  $rootScope.showLoader("Loading...");	
          bookingService.searchBooking(bookingObject).then(function(response) {
                  $scope.resultDetails = response;
                  $scope.filteredResults = response.results;
                  $scope.currentPage = page;
                  if($scope.filteredResults.length>0){
                	  sessionStorage.setItem('bookingParams', JSON.stringify(bookingObject));
                      sessionStorage.setItem('resultDetails', JSON.stringify($scope.resultDetails));
                      sessionStorage.setItem('filteredResults', JSON.stringify($scope.filteredResults));
                  }else{
                	  console.error('No Search Results Found');
                	  $('html, body').animate({
                          scrollTop: 0
                      }, 'fast'); 
                      alertsService.addAlert({
                          title: 'Error',
                          message: "No bookings found with given search parameters",
                          type: 'error'
                      });
                  }
                  $rootScope.hideLoader();
          }, function(err) {
        	  $rootScope.hideLoader();
          });
      }
      
    }
  ]);

})(angular.module('aet.screens.booking'));

(function(module) {

  module.controller('AdminSidebarController', ['$scope', 'appVersion',
    function($scope, appVersion) {
      
      $scope.version = appVersion();
      $scope.mainTabs = {};
      $scope.mainTabs[1] = true; //Administration Tab
      $scope.mainTabs[2] = true; //Place Pass Partner Configs Tab
      $scope.mainTabs[3] = true; //Place Pass Partner Places Tab
      $scope.mainTabs[4] = true; //Partner Property Tab
      $scope.mainTabs[5] = true; //Booking Tab
      $scope.mainTabs[6] = true; //Discussions Tab
      $scope.mainTabs[7] = true; //Lists Tab
      $scope.mainTabs[8] = true; //Reports Tab
      $scope.mainTabs[9] = true; //Tips & Help Tab

      $scope.go = function(mainTabId, currentTab) {
        if (!currentTab) {
          angular.forEach($scope.mainTabs, function(mainTab, key) {
            $scope.mainTabs[key] = true;
          });
          $scope.mainTabs[mainTabId] = false;
        }

      };

      $scope.clear = function() {
		sessionStorage.filteredResults = null;
		sessionStorage.resultDetails = null;
		sessionStorage.bookingParams = null;
		sessionStorage.filteredReportResults = null;
		sessionStorage.resultReportDetails = null;
		sessionStorage.bookingReportParams = null;
      };

    }
  ]);

})(angular.module('aet.screens.sidebars'));

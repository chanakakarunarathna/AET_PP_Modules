/**
 * Created by supun.s on 06/08/2015.
 */
(function(module) {

  module.controller("MapActionSummaryCtrl", ['$scope', 'HierarchyMapService', '$parse', 'userDetails',
    function($scope, HierarchyMapService, $parse, userDetails) {

      $scope.loggedUserID = userDetails.getUserId();
      $scope.HierarchyMapService = HierarchyMapService;

      //this function is used by comment templates
      $scope.mediaPosition = function() {
        if ($scope.comment) {
          return 'pull-right';
        } else {
          return 'pull-left';
        }
      }

      //this function is used by action-summary templates
      $scope.showNoInsightAvailable = function(insightLists) {
        var isNoInsightAvailableVisible = false;
        //used for store mapType(customers or employees) insight object count
        var count = 0;
        angular.forEach(insightLists, function(insightList, insightListKey) {
          if (insightList.mapType === HierarchyMapService.getMapType()) {
            count++;
          }
        });
        if (count === 0) {
          isNoInsightAvailableVisible = true;
        }

        return isNoInsightAvailableVisible;
      }

    }
  ]);
})
(angular.module('aet-directives-hierarchyMap'));

/**
 * Created by supun.s on 06/08/2015.
 */
(function(module) {

  module.controller("EditHierarchyMapCtrl", ['$scope', 'HierarchyMapService','$rootScope','mapService',
    function ($scope, HierarchyMapService, $rootScope, mapService) {

      var stageTemplate = HierarchyMapService.getMapData().stageTemplate;

      $scope.$watch(function () { return HierarchyMapService.getMapData() }, function (newVal, oldVal) {
        $scope.data = newVal;
        stageTemplate = newVal;
      });
      if(stageTemplate !== 'CUSTOM'){
        $scope.stageSortableOptions = {
          disabled: true
        }
      }else{
        $scope.stageSortableOptions = {
          stop : function(e, ui) {
            var stage = ui.item.sortable.model;
            var fromIndex = ui.item.sortable.index;
            var toIndex = ui.item.sortable.dropindex;

            if(toIndex !== undefined && fromIndex !== undefined){
              stage.positionIndex = ui.item.sortable.dropindex;
              HierarchyMapService.updateStagePositionIndex(stage, mapService.updateStage, mapService.getMapData)
            }
          },
          axis: 'x',
          placeholder: {
            element: function(currentItem) {
              return $("<li class='element-container placeholder'><div class='node'></div></li>")[0];
            },
            update: function(container, p) {
              return;
            }
          }
        };
      }

      $scope.touchpointSortableOptions = {
        stop : function(e, ui) {
          var touchpoint = ui.item.sortable.model;
          var fromIndex = ui.item.sortable.index;
          var toIndex = ui.item.sortable.dropindex;
          var dataset = ui.item.sortable.source.context.dataset;
          if(toIndex !== undefined && fromIndex !== undefined){
            touchpoint.stageId = dataset.stageid;
            touchpoint.positionIndex = ui.item.sortable.dropindex;
            HierarchyMapService.updateTouchpointPositionIndex(touchpoint, mapService.updateTouchpoint, mapService.getMapData)
          }
        },
        axis: 'x',
        placeholder: {
          element: function(currentItem) {
            return $("<li class='element-container placeholder'><div class='line-to'></div><div class='node'></div></li>")[0];
          },
          update: function(container, p) {
            return;
          }
        }
      };

      $scope.actionSortableOptions = {
        stop : function(e, ui) {
          var action = ui.item.sortable.model;
          var fromIndex = ui.item.sortable.index;
          var toIndex = ui.item.sortable.dropindex;
          var dataset = ui.item.sortable.source.context.dataset;
          if(toIndex !== undefined && fromIndex !== undefined){
            action.positionIndex = ui.item.sortable.dropindex;
            action.stageId = dataset.stageid;
            action.touchpointId = dataset.touchpointid;
            HierarchyMapService.updateActionPositionIndex(action, mapService.updateAction, mapService.getMapData)
          }
        },
        axis: 'x',
        placeholder: {
          element: function(currentItem) {
            return $("<li class='element-container placeholder'><div class='line-to'></div><div class='node'></div></li>")[0];
          },
          update: function(container, p) {
            return;
          }
        }
      };

      $scope.stage_index = 0;
      $scope.touchpoint_index = 0;

      $scope.active = function (stageIndex, touchpointIndex) {
        $scope.stage_index = stageIndex;
        $scope.touchpoint_index = touchpointIndex;
      };

    }
  ]);

})(angular.module('aet-directives-hierarchyMap'));

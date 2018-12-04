(function (module) {

  module.controller("ViewHierarchyMapCtrl", ['$scope', 'HierarchyMapService', '$rootScope', 'mapService', 'mapModalService', 'security',
    function ($scope, HierarchyMapService, $rootScope, mapService, mapModalService, security) {

      var options = {
        chart: {
          type: 'linePlusBarChart',
          height: 192,
          margin: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 15
          },
          bars: {
            forceY: [1],
            padData: true,
            yDomain: [0,5],
            margin : {
              left : -8,
              bottom: -1
            },
            interactive : false
          },
          showLegend: false,
          focusEnable: false,
          interpolate: "monotone",
          color: ['#006dd4', '#cccccc'],
          xAxis: {
            ticks: false,
            showMaxMin: false
          },
          y1Axis: {
            ticks: false,
            showMaxMin: false,
            domain: [ 0 , 5]
          },
          y2Axis: {
            ticks: false,
            showMaxMin: false
          },
          yDomain:[-0.1,5.1],
          interactive : false,
          tooltips: false
        }
      };

      $scope.experienceCarveOptions = function(touchpointLength){
        if(touchpointLength > 0){
          options.chart.width = (touchpointLength * 190) - 15;

        }
        return options;
      };

      $scope.dataaa = [
        {
          "values": [
            {
              "x": 1,
              "y": 2,
              "series": 0
            },
            {
              "x": 2,
              "y": 0,
              "series": 0
            }
          ]
        }
      ];

      $scope.$watch(function () {return HierarchyMapService.getMapData()},
        function (newVal, oldVal) {
          var tab = HierarchyMapService.getActiveTab();
          $scope.data = newVal;
         HierarchyMapService.setActiveTab(tab);

      });
      //$scope.data = HierarchyMapService.getMapData();
      $scope.HierarchyMapService = HierarchyMapService;

      $scope.hasComments = function (commentsSummaries) {
        if (commentsSummaries.POSITIVE.length > 0 || commentsSummaries.NEGATIVE.length > 0 || commentsSummaries.NEUTRAL.length > 0) {
          return true;
        }
      };

      var date = new Date();
      var timeNow = date.getTime();
      var millisecondsForHour = 86400000;

      $scope.isNew = function (time) {
        if ((timeNow - time) <= millisecondsForHour) {
          return 'new';
        }
      };

      $scope.setNodeColor = function (expectationAverage, loyaltyAverage) {
        return HierarchyMapService.setColor(expectationAverage, loyaltyAverage);
      };

      $scope.averageColor = function (average) {

        if (average === 0) {
          return 'white';
        } else if (average <= 2.9) {
          return 'solidRed';
        } else if (average >= 4 && average <= 5) {
          return 'solidGreen';
        } else {
          return 'solidGray';
        }

      };

      $scope.stage_index = 0;
      $scope.touchpoint_index = 0;
      $scope.action_index = null;

      $scope.active = function (stageIndex, touchpointIndex) {
        $scope.stage_index = stageIndex;
        $scope.touchpoint_index = touchpointIndex;
        $scope.action_index = null;
      };

      $scope.dynamicPopover = {
        content: 'Hello, World!',
        templateUrl: 'myPopoverTemplate.html',
        title: 'Title'
      };

      $scope.options = {
        colours: [
          {
            fillColor: ["#c6000a", "#de6a70", "#96a1a1", "#bbe88e", "#9bce67"]
          }
        ],
        listOfLocations: ['1', '2', '3', '4', '5'],
        options: {
          barShowStroke: false,
          scaleOverlay: true,
          scaleOverride: false,
          scaleShowLabels: true,
          scaleLineColor: 'rgba(0,0,0,0.5)',
          scaleGridLineColor: "rgba(0,0,0,.05)",
          scaleShowHorizontalLines: true,
          scaleShowVerticalLines: false,
          scaleGridLineWidth: 5,
          barValueSpacing: 6,
          responsive: true,
          maintainAspectRatio: false
        }
      };

      $scope.actionDetails = function(stageIndex, touchpointIndex, actionIndex, container, action, stage, touchpoint, averageColor, closeActionDetails, curveType) {
        $scope.action_index = actionIndex;

        ///using modal for action details
        mapModalService.viewActionDetails(action, stage, touchpoint, averageColor, closeActionDetails, curveType);

      };

      $scope.closeActionDetails = function () {
        $scope.action_index = null;
      };

      $scope.stageTouchpointDetails = function(type, data, stage){
        mapModalService.viewStageTouchpointDetails(type, data, stage);
      }

    }
  ])
  ;

})
(angular.module('aet-directives-hierarchyMap'));

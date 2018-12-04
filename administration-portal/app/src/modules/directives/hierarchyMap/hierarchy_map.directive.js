/**
 * Created by supun.s on 06/08/2015.
 */
(function(module) {

  module.directive("editHierarchyMap", ['$log',
      function($log) {
        return {
          restrict: 'E',
          scope: {
            addStageFn: "=",
            editStageFn: "=",
            addTouchpointFn: "=",
            editTouchpointFn: "=",
            addActionFn: "=",
            editActionFn: "=",
            deleteFn: "=",
            mapType: "@"
          },
          templateUrl: 'src/modules/directives/hierarchyMap/templates/edit_hierarchy_map.html',
          bindController: true,
          controller: "EditHierarchyMapCtrl as EditHierarchyMap",
          link: function(scope, element, attr) {

          }
        };
      }
    ])
    .directive("viewHierarchyMap", ['$log',
      function($log) {
        return {
          restrict: 'E',
          scope: {
            mapType: "@",
            curveType: "@",
            export: "=",
            actionCtrl: "=",
            mapDataType: "="
          },
          templateUrl: 'src/modules/directives/hierarchyMap/templates/view_hierarchy_map.html',
          bindController: true,
          controller: "ViewHierarchyMapCtrl as ViewHierarchyMap",
          link: function(scope, element, attr) {
            scope.$watch('export', function(newVal, oldValue) {});
          }
        };
      }
    ])
    .directive('setPosition', function() {
      return {
        restrict: 'A',
        scope: {},
        link: function(scope, element, attr) {

        }
      }
    })
    .directive('pop', function() {
      return {
        restrict: 'A',
        scope: {},
        link: function(scope, element, attr) {
          element.bind('mouseenter', function() {
            if (attr.popLength < 15) {
              //element.next('.popover').hide();
            }
          });
        }
      }
    })
    .directive('actionChart', function() {
      return {
        restrict: 'A',
        scope: {},
        link: function(scope, element, attr) {

        }
      }
    })
    .directive('comment', ['HierarchyMapService', function(HierarchyMapService) {
      return {
        restrict: 'E',
        scope: {
          insightData: '=',
          createdDate: '=',
          commentText: '=',
          type: '@'
        },
        controller: 'MapActionSummaryCtrl as MapActionSummary',
        link: function(scope, element, attr) {
          scope.comment = '';
          scope.more = false;
          var text = scope.commentText;

          scope.showMoreTxt = function() {
            HierarchyMapService.moreCommentTxt(text);
          };

          if (angular.isDefined(text) && text !== null) {
            if (text.length > attr.limit) {
              var charLimitedStr = text.slice(0, attr.limit);
              var last = text.charAt(attr.limit);
              var lastSpacePos = charLimitedStr.lastIndexOf(" ");
              var wordLimitedStr;
              if (!angular.equals(last, " ")) {
                wordLimitedStr = text.slice(0, lastSpacePos);
              } else {
                wordLimitedStr = charLimitedStr;
              }
              scope.comment = wordLimitedStr + '...';
              scope.more = true
            } else {
              scope.comment = text;
              scope.more = false
            }
          }

        },
        templateUrl: 'src/modules/directives/hierarchyMap/templates/assets/comment.html'
      }
    }])
    .directive('readMore', ['HierarchyMapService', function(HierarchyMapService) {
      return {
        restrict: 'E',
        scope: {
          text: '='
        },
        link: function(scope, element, attr) {
          scope.showReadMore = false;
          scope.readText = 'Read More';
          scope.displayText = scope.text;
          var limitedText, originalText = scope.text;

          if(scope.text && (scope.text.length > attr.limit)){
            scope.showReadMore = true;
            limitedText = originalText.slice(0, attr.limit) + '...';
            scope.displayText = limitedText;
          }
          scope.readMoreLess = function(){

            if(scope.displayText.length < scope.text.length){
              scope.displayText = originalText;
              scope.readText = 'Read Less';
            }else{
              scope.displayText = limitedText;
              scope.readText = 'Read More';
            }

          }

        },
        templateUrl: 'src/modules/directives/hierarchyMap/templates/assets/read-more.html'
      }
    }])
    .directive('actionSummary', function() {
      return {
        restrict: 'E',
        scope: {
          actionData: '=',
          stageData: '=',
          touchpointData: '=',
          averageColorFn: '=',
          closeActionDetailsFn: '=',
          curveType: '@',
        },
        controller: 'MapActionSummaryCtrl as MapActionSummary',
        link: function(scope, element, attr) {
          scope.options = {
            colours: [{
              fillColor: ["#c6000a", "#de6a70", "#96a1a1", "#bbe88e", "#9bce67"]
            }],
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
              //maintainAspectRatio: false
            }
          };
        },
        templateUrl: 'src/modules/directives/hierarchyMap/templates/assets/action-summary.html'

      }

    })
    .directive('mapLabel', function() {
      return {
        restrict: 'A',
        scope: {
          label: '@'
        },
        link: function(scope, element, attr) {
          var label = scope.label;
          var c = element[0];
          var context = c.getContext("2d");
          context.translate(c.width / 2, c.height / 2);
          context.rotate(Math.PI / -2);
          context.font = "12px Arial";
          context.fillStyle = "#ffffff"; // green
          context.textAlign = "center";
          context.fillText(label, 0, 0);
        }
      }
    })
    .directive('imgType',['HierarchyMapService', function(HierarchyMapService) {
      return {
        restrict: 'E',
        scope: {
          image: '=',
          time: '='
        },
        link: function(scope, element, attr) {

          var date = new Date();
          var timeNow = date.getTime();
          var millisecondsForHour = 86400000;
          var uploadTime = scope.time;

          function isNew(time) {
            if ((timeNow - time) <= millisecondsForHour) {
              return 'new';
            }
          }

          var fileName = scope.image;
          var type = HierarchyMapService.getFileType(fileName);

          element.append('<div class="type ' + type + ' ' + isNew(uploadTime) + '"></div>')
        }
      }
    }])
})(angular.module('aet-directives-hierarchyMap'));

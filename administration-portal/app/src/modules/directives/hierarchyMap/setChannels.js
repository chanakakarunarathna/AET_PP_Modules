/**
 * Created by supun.s on 06/08/2015.
 */
(function (module) {

  module.directive("setChannels", ['$log', 'HierarchyMapService',
    function ($log, HierarchyMapService) {
      return {
        restrict: 'A',
        scope: {
          channel: '=',
          channelList: '='
        },
        link: function (scope, element, attr) {

          var channelList = scope.channelList,
              channel = scope.channel,
              node = element.find('.node'),
              lineH = element.find('.line-h'),
              height, position;

          position = _.findIndex(channelList, function(cha) {
            return cha.title == channel.title;
          });

          height = (152 + 50) * position;

          element.css('height', height);
          node.css('top', height);
          lineH.css('height', height);

        }
      };
    }
  ])
})(angular.module('aet-directives-hierarchyMap'));

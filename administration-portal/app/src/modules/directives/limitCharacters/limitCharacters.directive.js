(function(module) {

  module.directive('limitCharacters', ['$log', function($log) {

    return {
      scope: {
        text: '='
      },
      link: function(scope, element, attr) {
        var originalText = scope.text;
        scope.limitedText = originalText;
        if(originalText.length > attr.to){
          scope.limitedText = originalText.slice(0, attr.to) + '...';
        }
      },
      templateUrl: 'src/modules/directives/limitCharacters/templates/limit-text.html',
      replace: true
    };
  }]);

})(angular.module('aet.directives.limitCharacters'));

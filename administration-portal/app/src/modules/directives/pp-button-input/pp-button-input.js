(function(module) {

  module.directive('ppButtonInput', ['$log', '$timeout', function($log, $timeout) {
    return {
      scope: {
        ngModel: '=',
        type: '=',
        name: '=',
        placeholder: '='
      },
      templateUrl: 'src/modules/directives/pp-button-input/templates/pp-button-input.html',
      require: '?aetCheckPermission',
      link: function(scope, element, attrs, aetCheckPermission) {
        
        scope.editable = true;
        scope.selected = false;
        
        if(aetCheckPermission) {
          scope.$watch(function() {return aetCheckPermission.hasPermission()}, function(nv) {
            if(nv) {
              scope.editable = true;
            }
            else {
              scope.editable = false;
            }
          });
        }
        
        scope.click = function() {
          if(scope.editable) {
            scope.selected = true;
            $timeout(function() { // I think timeout is needed to wait for textarea to become 'un-hidden'
              element.find('input')[0].focus();
            });
          }
        }
        
        scope.onKeyPress = function($event){
          var keyCode = $event.which || $event.keyCode;
          if (keyCode === 13) {
              scope.selected = false;
              $event.preventDefault();
          }

        };
        
      }
    }
  }]);
  

  module.directive('ppButtonTextarea', ['$log', '$timeout', function($log, $timeout) {
    return {
      scope: {
        ngModel: '=',
        placeholder: '='
      },
      require: '?aetCheckPermission',
      templateUrl: 'src/modules/directives/pp-button-input/templates/pp-button-textarea.html',
      link: function(scope, element, attrs, aetCheckPermission) {

        scope.editable = true;
        scope.selected = false;
        
        if(aetCheckPermission) {
          scope.$watch(function() {return aetCheckPermission.hasPermission()}, function(nv) {
            if(nv) {
              scope.editable = true;
            }
            else {
              scope.editable = false;
            }
          });
        }
        
        scope.click = function() {
          if(scope.editable) {
            scope.selected = true;
            $timeout(function() { // I think timeout is needed to wait for textarea to become 'un-hidden'
              element.find('textarea')[0].focus();
            });
          }
        }
        
      }
    }
  }]);


})(angular.module('aet.directives.pp-button-input'));

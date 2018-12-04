(function(module) {

  module.directive('aetIsLoggedIn', ['security', function(security) {

    return {
      restrict: 'A',
      transclude: false,
      require: 'aetIsLoggedIn',
      link: function link(scope, element, attrs, controller) {

        element.addClass('ng-hide');

        var makeUndefined = false;

        scope.resolvePermission = function() {
          if (security.isAuthenticated())
            hasPermission();
          else
            doesNotHavePermission();
        }

        var hasPermission = function() {
          element.removeClass('ng-hide');
        };

        var doesNotHavePermission = function() {
          element.addClass('ng-hide');
          if (controller.ngModels.length)
            _.each(controller.ngModels, function(ngModel) {
              ngModel.$setViewValue(undefined)
            });
          makeUndefined = true;
        };

        scope.resolvePermission();

        scope.$watch(security.isAuthenticated, function(nv, ov) {
          if (angular.isDefined(nv))
            scope.resolvePermission();
        });

        if (controller.ngModels.length) {
          scope.$watchCollection(function() {
            return _.map(controller.ngModels, function(ngModel) {
              return ngModel.$viewValue;
            })
          }, function(nv) {
            if (makeUndefined)
              _.each(controller.ngModels, function(ngModel) {
                ngModel.$setViewValue(undefined)
              });
          });
        }

      },

      controller: function($scope, $element, $attrs) {

        this.ngModels = [];

        this.registerInputNullifier = function(ngModel) {
          this.ngModels.push(ngModel);
        }

      }
    };

  }]);

})(angular.module('aet.security'));

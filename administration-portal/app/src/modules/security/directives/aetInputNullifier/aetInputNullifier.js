(function(module) {

  module.directive('aetInputNullifier', ['userDetails', function(userDetails) {

    return {
      restrict: 'A',
      transclude: false,
      require: ['ngModel', '?^hasRole', '?^isSuperAdmin'],
      link: function link(scope, element, attrs, controllers) {

        var ngModel = controllers.shift();

        _.remove(controllers, function(controller) {
          return angular.isUndefined(controller);
        });
        if (controllers.length < 1)
          throw new Error('Missing at least one required controller');

        _.each(controllers, function(controller) {
          controller.registerInputNullifier(ngModel);
        });

      }
    };

  }]);

})(angular.module('aet.security'));

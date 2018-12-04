(function(module) {
    
    module.directive('aetIsForbidden', ['userDetails', 'security', function (userDetails, security) {

        return {
            restrict: 'A',
            transclude: false,
            require: 'aetIsForbidden',
            link: function link(scope, element, attrs, controller) {
              
                element.addClass('ng-hide');

                var makeUndefined = false;
				var permission = attrs['aetIsForbidden'];

                var resolvePermission = function() {
                    if(security.isAuthorized(permission) === true)
                    	doesNotHavePermission();
                    else {
                    	hasPermission();
                    }
                }

                var hasPermission = function() {
                    element.removeClass('ng-hide');
                };

                var doesNotHavePermission = function() {
                    element.addClass('ng-hide');
                    if(controller.ngModels.length)
                        _.each(controller.ngModels, function(ngModel) {ngModel.$setViewValue(undefined)});
                    makeUndefined = true;
                };
				
                scope.$watch(function() {return userDetails.getUser()}, function(nv, ov) {
                    if(angular.isDefined(nv))
                        resolvePermission();
                });

                if(controller.ngModels.length) {
                    scope.$watchCollection(function() {return _.map(controller.ngModels, function(ngModel) {return ngModel.$viewValue;})}, function(nv) {
                        if(makeUndefined)
                            _.each(controller.ngModels, function(ngModel) {ngModel.$setViewValue(undefined)});
                    });
                }

            },

            controller: ['$scope', '$element', '$attrs', function($scope, $element, $attrs) {

                this.ngModels = [];

                this.registerInputNullifier = function(ngModel) {
                    this.ngModels.push(ngModel);
                }

            }]
        };

    }]);
    
})(angular.module('aet.security'));
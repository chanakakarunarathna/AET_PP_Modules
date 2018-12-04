(function(module) {
    
    module.directive('aetHasPermission', ['userDetails', 'security', function (userDetails, security) {

        return {
            restrict: 'A',
            transclude: false,
            require: 'aetHasPermission',
            link: function link(scope, element, attrs, controller) {
              
                if(attrs.aetPermissionDisable) {
                    element.attr('disabled', 'disabled');
                }
                else {
                    element.addClass('ng-hide');
                }

                var makeUndefined = false;
				var permission = attrs['aetHasPermission'];

                var resolvePermission = function() {
                    if(security.isAuthorized(permission) === true)
                        hasPermission();
                    else {
                        doesNotHavePermission();
                    }
                }

                var hasPermission = function() {
                    if(attrs.aetPermissionDisable) {
                        element.removeAttr('disabled');
                    }
                    else {
                        element.removeClass('ng-hide');
                    }
                };

                var doesNotHavePermission = function() {
                    if(attrs.aetPermissionDisable) {
                        element.attr('disabled', 'disabled');
                    }
                    else {
                        element.addClass('ng-hide');
                    }
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
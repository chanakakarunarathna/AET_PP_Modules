(function(module) {
    
    module.directive('aetCheckPermission', ['userDetails', 'security', function (userDetails, security) {

        return {
            restrict: 'A',
            transclude: false,
            require: 'aetCheckPermission',
            link: function link(scope, element, attrs, controller) {
              
                var makeUndefined = false;
				var permission = attrs['aetCheckPermission'];
              
                scope.hasPermission = false;

                var resolvePermission = function() {
                    if(security.isAuthorized(permission) === true)
                        hasPermission();
                    else {
                        doesNotHavePermission();
                    }
                }

                var hasPermission = function() {
                    scope.hasPermission = true;
                };

                var doesNotHavePermission = function() {
                    scope.hasPermission = false;
                };
				
                scope.$watch(function() {return userDetails.getUser()}, function(nv, ov) {
                    if(angular.isDefined(nv))
                        resolvePermission();
                });

            },

            controller: ['$scope', '$element', '$attrs', function($scope, $element, $attrs) {

                this.hasPermission = function() {
                  return $scope.hasPermission;
                };
              
            }]
        };

    }]);
    
})(angular.module('aet.security'));
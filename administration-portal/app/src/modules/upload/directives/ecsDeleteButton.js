(function(module) {
    
    module.directive('ecsDeleteButton', [
        function () {
        
            return {
                restrict: 'AC',
                scope: true,
                require: '^ngModel',

                link: function postLink(scope, element, attrs, ngModel) {
					
                    scope.$watch(attrs['ecsDeleteButton'], function(nv) {
                        if(angular.isDefined(nv)) {
                            scope.upload = nv;
                        }
                    });
                    
                    scope.name = attrs.name;
                    
                    element.on('click', function(event) {
                        scope.upload.delete(ngModel.$viewValue).then(function(response) {
                            ngModel.$setViewValue(undefined);
                        });
                    });

                }
            };
        }
    ]);
    
})(angular.module('ecs-upload'));

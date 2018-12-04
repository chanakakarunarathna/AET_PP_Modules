(function(module) {
    
    var directive = ['errorMessages',
        function (errorMessages) {
        
            return {
                scope: {
                    upload: "=for"
                },
                templateUrl: 'src/modules/upload/templates/ecsUploadErrors.html',

                link: function postLink(scope, element, attrs) {
                    
                    scope.$watchCollection('upload.$errors', function(errors) {
                        var errorStrings = [];
                        
                        angular.forEach(errors, function(val,key) {
                            var errorMessage = errorMessages.findMessage(val.validator);
                            angular.forEach(val.params, function(param, index) {
                                var regex = new RegExp('\\$\\{' + index + '\\}', 'g');
                                errorMessage = errorMessage.replace(regex, param);
                            });
                            errorStrings.push(errorMessage);
                            
                        });
                        
                        scope.errorMessage = errorStrings.join('\n');
                    });
                    
                }
            };
        }
    ];

    module.directive('ecsUploadErrors', directive);
	
})(angular.module('ecs-upload'));

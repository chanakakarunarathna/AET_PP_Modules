(function(module) {
    
    var directive = [function () {
        
        return {
            scope: true,
            
            link: function postLink(scope, element, attrs, controllers) {
                
                scope.multiple = scope.$eval(attrs.multiple) || false;
                scope.uploadOnInput = scope.$eval(attrs.uploadOnInput) || true;
                
                scope.$watch(attrs['ecsUploadDropArea'], function(nv) {
                    if(angular.isDefined(nv)) {
                        scope.upload = nv;
                    }
                });
                
                var processDragOverOrEnter = function(event) {
                  if (event != null) {
                    event.preventDefault();
                  }
//        	          event.dataTransfer.effectAllowed = 'copy';
                  return false;
                };
                
                element.bind('dragover', processDragOverOrEnter);
                element.bind('dragenter', processDragOverOrEnter);
                element.bind('drop', function(event) {
                    scope.$apply(function() {

                        if (event != null) {
                            event.preventDefault();
                        }

                        if(event.dataTransfer.files.length > 1)
                            throw new Error('Only one file supported');

                        if(scope.canChangeFile()) {
                            scope.upload.selectedFile = event.dataTransfer.files[0];

                            if(scope.uploadOnInput) {
                                scope.upload.start();
                            }
                            else {
                                // Not supported yet
                            }

                        }
                        
                    })
                });
                
                // Is the control in a state where the file can change?
                scope.canChangeFile = function() {
                    if(angular.isUndefined(scope.upload))
                        return false;

                    return !scope.upload.uploading;
                };
                
            }
        };

    }];

    module.directive('ecsUploadDropArea', directive);
	
})(angular.module('ecs-upload'));

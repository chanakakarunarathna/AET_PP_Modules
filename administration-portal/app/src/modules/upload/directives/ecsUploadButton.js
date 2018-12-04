(function(module) {

    module.directive('ecsUploadButton', [
        function () {

            return {
                restrict: 'AC',
                scope: true,

                link: function postLink(scope, element, attrs, ecsUpload) {

                    scope.$watch(attrs['ecsUploadButton'], function(nv) {
                        if(angular.isDefined(nv)) {
                            scope.upload = nv;
                        }
                    });

                    scope.name = attrs.name;
                    scope.multiple = scope.$eval(attrs.multiple) || false;
                    scope.uploadOnInput = scope.$eval(attrs.uploadOnInput) || true;

                    element.on('click', function(event) {
                        if (angular.isDefined(scope.upload)) {
                        if(scope.upload.uploading) {
                            scope.upload.cancel();
                        }
                        if(scope.canChangeFile()) {
                            scope.fileInputElement.val("");
                            scope.displayFileSelector(event);
                        }
                      }
                    });

                    scope.displayFileSelector = function(event) {
                        scope.fileInputElement[0].click();
                    };

                    scope.onChange = function() {

                        if(scope.multiple) {
                            // I'm not going to support this just yet
                        }
                        else {
                            if(scope.fileInputElement[0].files.length > 1)
                                throw new Error('Only one file supported');

                            if(scope.canChangeFile()) {
                                scope.upload.selectedFile = scope.fileInputElement[0].files[0];

                                if(scope.uploadOnInput) {
                                    scope.upload.start();
                                }
                                else {
                                    // Not supported yet
                                }

                            }
                        }
                    };

                    // Is the control in a state where the file can change?
                    scope.canChangeFile = function() {
                        if(angular.isUndefined(scope.upload))
                            return false;

                        return !scope.upload.uploading;
                    };

                },

                controller: ['$scope', function($scope) {

                    $scope.fileInputElement;

                    this.registerInput = function(input) {
                        if($scope.fileInputElement)
                            $scope.fileInputElement.off('change');
                        $scope.fileInputElement = input;
                        $scope.fileInputElement.on('change', function() {
                            $scope.$apply(function() {
                                $scope.onChange();
                            })
                        });
                    }

                }]
            };
        }
    ]);

})(angular.module('ecs-upload'));

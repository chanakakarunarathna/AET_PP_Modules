(function(module) {

    var directive = ['$parse',
        function($parse) {

            return {
                restrict: 'AC',
                scope: true,
                require: ['?ngModel'],
                priority: 100,

                link: function postLink(scope, element, attrs, controllers) {

                    var ngModel = controllers[0];


                    var saveFn = $parse(attrs['saveOnChange']);

                    scope.$watch(attrs['ecsUploadData'], function(nv) {
                        if (angular.isDefined(nv)) {
                            scope.upload = nv;
                            scope.upload.setValidators(scope.validators);
                        }
                    });

                    scope.progress = 0;

                    scope.$watch('upload.selectedFile', function(nv) {
                        scope.selectedFile = nv;
                        scope.progress = 0;
                    });

                    scope.$watch('upload.$reset', function(nv) {
                        if (nv === true) {
                            ngModel.$setViewValue(undefined);
                            upload.$reset = false;
                        }
                    });

                    scope.$watch('upload.uploadPromise', function(nv, ov) {
                        if (angular.isDefined(nv)) {
                            nv.then(scope.onSuccess, scope.onError, scope.onProgress);
                            if (angular.isDefined(ngModel)) {
                                ngModel.$setViewValue(undefined);
                            }
                        }
                    });

                    if (angular.isDefined(saveFn)) {
                        scope.$watch(function() {
                            return ngModel.$viewValue;
                        }, function(nv, ov) {
                            if (angular.isDefined(scope.upload)) {
                                if (!scope.upload.uploading && ov !== null && ov !== nv) {
                                    saveFn(scope);
                                }
                            }
                        });
                    }

                    scope.onSuccess = function(data) {
                        scope.url = data.url;
                        if (scope.url && angular.isDefined(ngModel))
                            ngModel.$setViewValue(scope.url);
                    };

                    scope.onError = function() {

                    };

                    scope.onProgress = function(progress) {

                        scope.progress = progress.value;
                    };

                },

                controller: ['$scope', function($scope) {
                    $scope.validators = {};

                    this.addValidator = function(vName, vFunc) {
                        $scope.validators[vName] = vFunc;
                    }
                }]
            };
        }
    ];

    module.directive('ecsUploadData', directive);

})(angular.module('ecs-upload'));

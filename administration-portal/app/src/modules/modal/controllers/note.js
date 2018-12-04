(function(module) {

    module.controller('NoteController', ['$scope', '$state', '$modalInstance', '$log',
        function($scope, $state, $modalInstance, $log) {

            $scope.cancel = function() {
                $scope.point = $scope.origPoint;
                $modalInstance.dismiss('cancel');
            };

            $scope.add = function() {
                $modalInstance.close($scope.point);
            };

        }
    ]);

})(angular.module('aet.modals'));

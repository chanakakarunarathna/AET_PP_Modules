(function(module) {

    module.controller('viewVoucherController', ['$scope', '$state', '$log', '$rootScope', '$modalInstance', '$q',
        function($scope, $state, $log, $rootScope, $modalInstance, $q) {
    	
            $scope.close = function() {
                $modalInstance.dismiss('cancel');
            };
        }
    ]);

})(angular.module('aet.modals'));

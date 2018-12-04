(function(module) {
    
    module.controller('FooterController', ['$scope', 'userDetails',
        function($scope, userDetails) {
            
            $scope.user = userDetails.getUser();

        }
    ]);
    
})(angular.module('aet.screens.index'));
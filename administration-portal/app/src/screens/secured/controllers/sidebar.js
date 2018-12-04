(function(module) {
    
    module.controller('SidebarController', ['$scope', 'userDetails',
        function($scope, userDetails) {
            
            $scope.user = userDetails.getUser();

        }
    ]);
    
})(angular.module('aet.screens.index'));
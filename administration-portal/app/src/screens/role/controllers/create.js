(function(module) {
    
    module.controller('CreateRoleController', ['$scope', 'roleService', 'Role', 'alertsService', '$state', '$log', 'featureSearchManager',
        function($scope, roleService, Role, alertsService, $state, $log, featureSearchManager) {
			
			var features = featureSearchManager.searchResults.results;
			
            $scope.submitRoleForm = function() {
            	
            	roleService.createRole($scope.role, features).then(function(data){
            		alertsService.addAlert({
            			 title: 'Success',
            			 message: 'Role "' + $scope.role.title + '" successfully created',
            			 type: 'success',
            			 removeOnStateChange: 2
            		});
                	$state.go('index.secured.role.search');
				}, function(err){
					console.error('Could not create role', err);
				});
                
            }
            
        }
    ]);
    
})(angular.module('aet.screens.role'));
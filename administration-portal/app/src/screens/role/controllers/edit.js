(function(module) {
    
    module.controller('EditRoleController', ['$scope', 'roleService', 'Role', 'alertsService', '$state', '$log', 'featureSearchManager', 'role',
        function($scope, roleService, Role, alertsService, $state, $log, featureSearchManager, role) {
			
            var features = featureSearchManager.searchResults.results;
			
			$scope.role = role;
			
			$scope.hasFeature = function(featureName) {
				return _.any(role.features, function(feature) {
					return feature.title == featureName;
				});
			};
			
            $scope.submitRoleForm = function() {
            	
            	roleService.updateRole($scope.role, features).then(function(data){
            		alertsService.addAlert({
            			 title: 'Success',
            			 message: 'Role "' + $scope.role.title + '" successfully updated',
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
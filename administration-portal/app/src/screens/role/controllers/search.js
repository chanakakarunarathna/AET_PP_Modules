(function(module) {
    
    module.controller('SearchRoleController', ['$scope', 'roleService', 'alertsService', '$state', '$log', 'searchManager', 'modalService',
        function($scope, roleService, alertsService, $state, $log, searchManager, modalService) {
			
			$scope.searchManager = searchManager;
			
			$scope.deleteRole = function(role) {
				
				var modalInstance = modalService.deleteModal(role.name);
				modalInstance.result.then(function() {
					roleService.deleteRole(role.id).then(function(data){
					    alertsService.addAlert({
					  		title: 'Success',
							message: "Successfully deleted '" + role.name + "'",
							type: 'success'
						});
						searchManager.delete(role.id);
					}, function(err){
						alertsService.addAlert({
						    title: 'Error',
							message: "Could not delete '" + role.name + "'",
							type: 'error'
						});
					});
				});
				
			};
			
			$scope.editRole = function(role) {
				$state.go('index.secured.role.edit', {roleId:role.id});
			}
        }
    ]);
    
})(angular.module('aet.screens.role'));
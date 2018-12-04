(function(module) {
    
    module.controller('SearchMasterFieldController', ['$scope', 'masterfieldService', 'alertsService', '$state', '$log', 'searchManager','modalService',
        function($scope, masterfieldService, alertsService, $state, $log, searchManager, modalService) {
			
			$scope.searchManager = searchManager;
			
			$scope.deleteMasterfield = function(masterfield) {
				
				var modalInstance = modalService.deleteModal(masterfield.name);
				modalInstance.result.then(function() {
					masterfieldService.deleteMasterfield(masterfield.id).then(function(data){
					    alertsService.addAlert({
					  		title: 'Success',
							message: "Successfully deleted '" + masterfield.name + "'",
							type: 'success'
						});
						searchManager.delete(masterfield.id);
					}, function(err){
						alertsService.addAlert({
						    title: 'Error',
							message: "Could not delete '" + masterfield.name + "'",
							type: 'error'
						});
					});
				});
				
			};
			
			$scope.editMasterfield = function(masterfield) {
				$state.go('index.secured.masterfield.edit', {masterFieldId:masterfield.id});
			}
        }
    ]);
    
})(angular.module('aet.screens.masterfield'));
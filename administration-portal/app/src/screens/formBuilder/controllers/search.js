(function(module) {
    
    module.controller('SearchFormBuilderController', ['$scope', 'formService', 'alertsService', '$state', '$log', 'searchManager', 'modalService', 'formCategory',
        function($scope, formService, alertsService, $state, $log, searchManager, modalService, formCategory) {
			
			$scope.searchManager = searchManager;
			$scope.formCategory = formCategory;
			
			$scope.deleteForm = function(form) {
				
				var modalInstance = modalService.deleteModal(form.name);
				modalInstance.result.then(function() {
					formService.deleteForm(form.id).then(function(data){
					    alertsService.addAlert({
					  		title: 'Success',
							message: "Successfully deleted '" + form.name + "'",
							type: 'success'
						});
						searchManager.delete(form.id);
					}, function(err){
						alertsService.addAlert({
						    title: 'Error',
							message: "Could not delete '" + form.name + "'",
							type: 'error'
						});
					});
				});
				
			};
			
			$scope.editForm = function(permit,isActive,isPublished) {
				$state.go('index.secured.formBuilder.edit', {permitId:permit.id,isactive:permit.isActive,ispublished:permit.isPublished});
			}
        }
    ]);
    
})(angular.module('aet.screens.formBuilder'));
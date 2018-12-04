(function(module) {

    module.controller('SearchPartnerController', ['$scope', 'partnerService', 'alertsService', '$state', 'searchManager', 'modalService',
        function($scope, partnerService, alertsService, $state, searchManager, modalService) {

    		$scope.searchManager = searchManager;

			$scope.deletePartner = function(partner) {

				var modalInstance = modalService.deleteModal(partner.name);
				modalInstance.result.then(function() {
					partnerService.deletePartner(partner.id).then(function(data){
					    alertsService.addAlert({
					  		title: 'Success',
							message: "Successfully deleted '" + partner.name + "'",
							type: 'success'
						});
						searchManager.delete(partner.id);

            $state.go('index.secured.partner.search',{},{reload:true});
					}, function(err){
						alertsService.addAlert({
						    title: 'Error',
							message: "Could not delete '" + partner.name + "'",
							type: 'error'
						});
					});
				});

			};

			$scope.editPartner = function(partner) {
				$state.go('index.secured.partner.edit', {partnerId:partner.id});
			};
        }
    ]);

})(angular.module('aet.screens.partner'));

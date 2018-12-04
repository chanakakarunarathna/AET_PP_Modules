(function(module) {

    module.controller('SearchPartnerPropertyController', ['$scope', 'partnerPropertyService', 'alertsService', '$state', 'searchManager', 'modalService',
        function($scope, partnerPropertyService, alertsService, $state, searchManager, modalService) {

    		$scope.searchManager = searchManager;

    		$scope.addFilter = function(){
    			var query = $scope.searchManager.query;
    			var filter = {
						name: 'searchby',
	                    label: 'searchby',
	                    value: 'all'
	            };
				var status = _.find($scope.searchManager.filters, function (field) {
					return field.value == 'all';
				});
				if(status==undefined && query.length > 0){
					$scope.searchManager.addFilter(filter);
				}else if(query.length==0){
					$scope.searchManager.removeFilter(filter);
				}
			}

			$scope.deletePartnerProperty = function(partnerProperty) {

				var modalInstance = modalService.deleteModal(partnerProperty.propertyCode);
				modalInstance.result.then(function() {
					partnerPropertyService.deletePartnerProperty(partnerProperty.id).then(function(data){
					    alertsService.addAlert({
					  		title: 'Success',
							message: "Successfully deleted '" + partnerProperty.propertyCode + "'",
							type: 'success'
						});
						searchManager.delete(partnerProperty.id);

            $state.go('index.secured.partnerProperty.search',{},{reload:true});
					}, function(err){
						alertsService.addAlert({
						    title: 'Error',
							message: "Could not delete '" + partnerProperty.propertyCode + "'",
							type: 'error'
						});
					});
				});

			};

			$scope.editPartnerProperty = function(partnerProperty) {
				$state.go('index.secured.partnerProperty.edit', {partnerPropertyId:partnerProperty.id});
			};
        }
    ]);

})(angular.module('aet.screens.partnerProperty'));

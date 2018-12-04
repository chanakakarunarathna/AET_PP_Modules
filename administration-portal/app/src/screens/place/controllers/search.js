(function(module) {

    module.controller('SearchPlacesController', ['$scope', 'placesService', 'alertsService', '$state', 'searchManager', 'modalService',
        function($scope, placesService, alertsService, $state, searchManager, modalService) {

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

			$scope.deletePlaces = function(places) {
				var partnerPlacesFound = false;
				placesService.getPlaces(places.id).then(function(data) {
					partnerPlacesFound = data.partnerPlacesFound;
					var modalInstance = modalService.deleteModal(places.webId, null, partnerPlacesFound);
					modalInstance.result.then(function() {
						placesService.deletePlaces(places.id).then(function(data){
						    alertsService.addAlert({
						  		title: 'Success',
								message: "Successfully deleted '" + places.webId + "'",
								type: 'success'
							});
							searchManager.delete(places.id);

							$state.go('index.secured.places.search',{},{reload:true});
						}, function(err){
							alertsService.addAlert({
							    title: 'Error',
								message: "Could not delete '" + places.webId + "'",
								type: 'error'
							});
						});
					});

		        },
		        function(err) {
		        	console.error('Could not get place', err);
		        });
			};

			$scope.editPlaces = function(places) {
				$state.go('index.secured.places.edit', {placesId:places.id});
			};
        }
    ]);

})(angular.module('aet.screens.places'));

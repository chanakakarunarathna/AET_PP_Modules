(function(module) {

    module.controller('SearchPartnerPlaceController', ['$scope', 'partnerPlaceService', 'adminUserService', 'alertsService', '$state', '$log', 'searchManager', 'modalService', '$rootScope', 'partnerPlaceDetails',
        function($scope, partnerPlaceService, adminUserService, alertsService, $state, $log, searchManager, modalService, $rootScope, partnerPlaceDetails) {

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

            $scope.deletePartnerPlace = function(partnerPlace) {
                var modalInstance = modalService.deleteModal(partnerPlace.parentId);
                modalInstance.result.then(function() {
                    partnerPlaceService.deletePartnerPlace(partnerPlace.id).then(function(data) {
                        alertsService.addAlert({
                            title: 'Success',
                            message: "Successfully deleted '" + partnerPlace.parentId + "'",
                            type: 'success'
                        });
                        searchManager.delete(partnerPlace.id);
                        $state.reload();

                        if (partnerPlace.id == partnerPlaceDetails.getSelectedPartnerPlace().id) {
                            var modalInstance = modalService.partnerPlacePickerNotification();
                            modalInstance.result.then(function() {});
                        }
                    }, function(err) {
                        alertsService.addAlert({
                            title: 'Error',
                            message: "Could not delete '" + partnerPlace.parentId + "'",
                            type: 'error'
                        });
                    });
                });
            };

            $scope.editPartnerPlace = function(partnerPlace) {

                $state.go('index.secured.partnerPlace.edit', {
                    partnerPlaceId: partnerPlace.id
                }, {
                    reload: true
                });

                $rootScope.hideLoader();
            };

            $scope.copyPartnerPlace = function(partnerPlace) {
                adminUserService.searchAdminUserByPartnerId({
                    count: 0,
                    page: 0
                }).then(function(data) {
                    var modalInstance = modalService.copyPartnerPlace(partnerPlace, data.results);
                    modalInstance.result.then(function(result) {});
                });

            };
        }
    ]);

})(angular.module('aet.screens.partnerPlace'));

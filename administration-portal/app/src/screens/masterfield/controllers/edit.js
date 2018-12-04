(function(module) {
    
    module.controller('EditMasterFieldController', ['$scope', 'masterfieldService', 'MasterfieldDTO', 'masterfield', 'alertsService', '$state', '$log',
        function($scope, masterfieldService, MasterfieldDTO, masterfield, alertsService, $state, $log) {
			$log.debug('EditMasterFieldController', masterfield);
			
            $scope.masterfield = masterfield;
			$scope.valueTypes = masterfieldService.getValueTypes();
			
			$scope.addOption = function(option) {
				if (option != null && option != ""){
					$scope.masterfield.options.push(option);
					$scope.masterfield.options = _.uniq($scope.masterfield.options);
					$log.debug("Options " + $scope.masterfield.options);
				}
			}
			
			$scope.deleteOption = function(id) {
				$scope.masterfield.options.splice(id, 1);
			};
           
            $scope.submitMasterFieldForm = function(params) {
            	
            	if (!($scope.masterfield.valueType == $scope.valueTypes[4] || $scope.masterfield.valueType == $scope.valueTypes[5])){
					_.remove($scope.masterfield.options);
				}
            	
            	masterfieldService.updateMasterfield(masterfield).then(function(data){
            		alertsService.addAlert({
						title: 'Success',
						message: 'Field "' + masterfield.name + '" successfully updated',
						type: 'success',
						removeOnStateChange: 2
           			});
                	$state.go('index.secured.masterfield.search');
				}, function(err){
					console.error('Could not edit masterfield', err);
				});
                
            }
            
        }
    ]);
    
})(angular.module('aet.screens.masterfield'));
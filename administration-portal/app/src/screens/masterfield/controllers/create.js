(function(module) {
    
    module.controller('CreateMasterFieldController', ['$scope', 'masterfieldService', 'MasterField', 'alertsService', '$state', '$log', 'modalService',
        function($scope, masterfieldService, MasterField, alertsService, $state, $log, modalService) {
            
            $scope.valueTypes = masterfieldService.getValueTypes();
			$scope.masterField = new MasterField();
			$scope.masterField.valueType = $scope.valueTypes[0];
			
			$scope.addOption = function(option) {
				if (option != null && option != ""){
					$scope.masterField.options.push(option);
					$scope.masterField.options = _.uniq($scope.masterField.options);
					$log.debug("Options " + $scope.masterField.options);
				}
				
			}
			
			$scope.deleteOption = function(id) {
				$scope.masterField.options.splice(id, 1);
			};
			
            $scope.submitMasterFieldForm = function() {
            	
            	if (!($scope.masterField.valueType == $scope.valueTypes[4] || $scope.masterField.valueType == $scope.valueTypes[5])){
					_.remove($scope.masterField.options);
				}
            	
            	masterfieldService.createMasterfield($scope.masterField).then(function(data){
            		alertsService.addAlert({
            			 title: 'Success',
            			 message: 'Field "' + $scope.masterField.name + '" successfully created',
            			 type: 'success',
            			 removeOnStateChange: 2
            		});
                	$state.go('index.secured.masterfield.search');
				}, function(err){
					console.error('Could not create masterfield', err);
				});
                
            }
            
        }
    ]);
    
})(angular.module('aet.screens.masterfield'));
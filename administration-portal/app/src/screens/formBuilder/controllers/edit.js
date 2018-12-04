(function(module) {
    
    module.controller('EditFormBuilderController', ['$scope', 'Form', 'Screen', 'FeeExpression', 'fieldService', '$log', 'formService', 'alertsService', 'masterfieldService', 'masterFieldSearchManager', '$state', 'permit', 'FormStatus',
        function($scope, Form, Screen, FeeExpression, fieldService, $log, formService, alertsService, masterfieldService, masterFieldSearchManager, $state, permit, FormStatus) {
			
			$scope.screenCounter = permit.screens.length;
			$scope.fieldCounter = _.reduce(permit.screens, function(total, screen) {
				total += screen.fields.length;
			}, 0);
			
			$scope.masterFieldSearchManager = masterFieldSearchManager;

			$scope.valueTypes = masterfieldService.getValueTypes(); 
			
			$scope.feeTypes = formService.getFeeTypes();
			
			$scope.feeExpression = new FeeExpression();
			
			$scope.feeExpression.type = $scope.feeTypes[0];
			$scope.feeExpression.label = "";
			$scope.feeExpression.description = "";
			$scope.feeExpression.feeExpression = "";
			
			$scope.statuses = FormStatus;
		
			$scope.permit = permit;

			$scope.addScreen = function() {
				var screen = new Screen();
				screen.name = 'screen' + ++$scope.screenCounter;
				screen.label = 'screen' + $scope.screenCounter;
				$scope.permit.screens.push(screen);
			};
			
			$scope.selectedScreen = $scope.permit.screens[0].name;
			
			$scope.$watchCollection('permit.screens', function(nv, ov) {
				var removed = _.difference(ov, nv);
				if(removed.length && removed[0].name === $scope.selectedScreen)
					$scope.selectedScreen = $scope.permit.screens[0].name;
			});

			// maintain a parallel list of fields
			$scope.flatFields = _.reduce($scope.permit.screens, function(acc, screen) {
				return acc.concat(screen.fields);
			}, []);

			$scope.onDropField = function(event, index, item, type) {
				
				if(angular.isDefined(item.type)) {
					return item;
				}
				
				var existingField = _.find($scope.flatFields, function(field) {
					if(field.name === item.name)
						return field;
				});
				
				if(angular.isDefined(existingField)) {
					// field can only be added once
					alertsService.addAlert({
						title: 'Error',
						message: 'Field "' + existingField.name + '" already exists in permit',
						type: 'error',
						removeOnStateChange: 1
					});
				}
				else {
					var field = fieldService.createFieldFromMasterField(item);
					$scope.flatFields.push(field);
					return field;
				}
			};
			
			$scope.removeField = function(screen, index) {
				var removedField = screen.fields.splice(index, 1);
				_.remove($scope.flatFields, function(field) {
					return field.name === removedField[0].name;
				});
			};
			
			$scope.addOption = function(field, option) {
				if (option != null && option != ""){
					field.options.push(option);
					field.options = _.uniq(field.options);
					$log.debug("Options " + field.options);
				}
				
			};
			
			$scope.removeOption = function(field,index) {
				if(field.options.length > 2) {
					field.options.splice(index,1);
				}
			};
			
			$scope.addFeeExpression = function(feeExpression) {
				$log.debug("Fee Expression");
				$log.debug(feeExpression);
				if (feeExpression.label.length < 1 || feeExpression.label.length > 255 || feeExpression.description.length > 1000 || feeExpression.feeExpression.length > 1000){
					$log.debug("Fee Expression Error");
					formService.feeExpressionInputValidations(feeExpression);
				}
				else{
					var feeExpressionObj = new FeeExpression();
					feeExpressionObj.label = feeExpression.label;
					feeExpressionObj.description = feeExpression.description;
					feeExpressionObj.feeExpression = feeExpression.feeExpression;
					feeExpressionObj.type = feeExpression.type;
					$scope.permit.feeExpressions.push(feeExpressionObj);
					$log.debug($scope.permit.feeExpressions);
				}
			};
			
			$scope.removeFeeExpression = function(index) {
				$scope.permit.feeExpressions.splice(index,1);
				$log.debug($scope.permit.feeExpressions);
			};
			
			$scope.submitFormForm = function(permit){
				
				formService.updateForm(permit).then(function(data){
					alertsService.addAlert({
						title: 'Success',
						message: 'Form "' + $scope.permit.name + '" successfully updated',
						type: 'success',
						removeOnStateChange: 1
					});
				}, function(err){
					console.error('Could not update Form', err);
				});
				
			};
			
			$scope.publishFormForm = function(){
				var permit = $scope.permit;
				permit.isPublished=true;
				permit.isActive=true;
				this.submitFormForm(permit);
			};
			
			$scope.saveFormForm = function(){
				var permit = $scope.permit;
				permit.isPublished=false;
				permit.isActive=true;
				this.submitFormForm(permit);
			};
			
			$scope.disableFormForm = function(){
				var permit = $scope.permit;
				permit.isActive=false;
				permit.isPublished=true;
				this.submitFormForm(permit);
			};
        }
    ]);
})(angular.module('aet.screens.formBuilder'));
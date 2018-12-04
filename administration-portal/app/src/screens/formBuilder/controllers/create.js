(function(module) {
    
    module.controller('CreateFormBuilderController', ['$scope', 'Form', 'Screen', 'FeeExpression', 'fieldService', '$log', 'formService', 'alertsService', 'masterfieldService', 'masterFieldSearchManager', '$state', 'FormStatus',
        function($scope, Form, Screen, FeeExpression, fieldService, $log, formService, alertsService, masterfieldService, masterFieldSearchManager, $state, FormStatus) {
			
			$scope.screenCounter = 0;
			
			$scope.masterFieldSearchManager = masterFieldSearchManager;

			$scope.valueTypes = masterfieldService.getValueTypes(); 
			
			$scope.feeTypes = formService.getFeeTypes();
			
			$scope.feeExpression = new FeeExpression();
			
			$scope.feeExpression.type = $scope.feeTypes[0];
			$scope.feeExpression.label = "";
			$scope.feeExpression.description = "";
			$scope.feeExpression.feeExpression = "";
			
			
			$scope.statuses = FormStatus;
		
			$scope.permit = new Form();

			$scope.addScreen = function() {
				var screen = new Screen();
				screen.name = 'screen' + ++$scope.screenCounter;
				screen.label = 'screen' + $scope.screenCounter;
				$scope.permit.screens.push(screen);
			};
			
			$scope.addScreen();
			
			$scope.selectedScreen = $scope.permit.screens[0].name;
			
			$scope.$watchCollection('permit.screens', function(nv, ov) {
				var removed = _.difference(ov, nv);
				if(removed.length && removed[0].name === $scope.selectedScreen)
					$scope.selectedScreen = $scope.permit.screens[0].name;
			});
			
			// maintain a parallel list of fields
			$scope.flatFields = [];

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
				permit.isActive=true;
				formService.createForm(permit).then(function(data){
					alertsService.addAlert({
						title: 'Success',
						message: 'Form "' + $scope.permit.name + '" successfully created',
						type: 'success',
						removeOnStateChange: 2
					});
					$state.go('index.secured.formBuilder.searchDraft');
				}, function(err){
					console.error('Could not create Form', err);
				});
				
			};
			
			$scope.publishFormForm = function(){
				var permit = $scope.permit;
				permit.isPublished=true;
				this.submitFormForm(permit);
			};
			
			$scope.saveFormForm = function(){
				var permit = $scope.permit;
				permit.isPublished=false;
				this.submitFormForm(permit);
			};
        }
    ]);
})(angular.module('aet.screens.formBuilder'));
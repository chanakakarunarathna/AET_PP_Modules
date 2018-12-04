(function(module) {
    
	module.service('formService', ['formEndpoint', '$q', '$log', 'formTransformer', 'alertsService',
        function(formEndpoint, $q, $log, formTransformer, alertsService) {
			
            this.getForm = function(permitId,searchObject) {
                return formEndpoint.getForm(permitId,searchObject).then(function(dto) {
                    return formTransformer.getDTOToForm(dto);
                }, function(err) {
                    console.error("Could not get permit");
                    return $q.reject(err);
                });

            };
            this.createForm = function(permit) {
				$log.debug("PERMIT:");
				$log.debug(permit);
				if(!this.validateForm(permit)) {
					return $q.reject('Validation failed');
				}
				
				permit = this.setScreenAndFieldOrders(permit);
				
				var createFormDTO = formTransformer.permitToCreateFormDTO(permit);
				
                return formEndpoint.createForm(createFormDTO).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not create permit");
                    return $q.reject(err);
                });

            };
			
			this.updateForm = function(permit) {
				
				if(!this.validateForm(permit)) {
					return $q.reject('Validation failed');
				}
				
				permit = this.setScreenAndFieldOrders(permit);
				
				var updateFormDTO = formTransformer.permitToUpdateFormDTO(permit);
				
                return formEndpoint.updateForm(updateFormDTO, permit.id).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not update permit");
                    return $q.reject(err);
                });

            };

            
            this.setScreenAndFieldOrders = function(permit) {
            	
            	var length = permit.screens.length;
            	for (var i=0; i<length;i++){
					permit.screens[i].order = i+1;
					var fieldLength = permit.screens[i].fields.length;
					for (var j=0; j<fieldLength;j++){
						permit.screens[i].fields[j].order = j+1;
					}
				};
				
            	return permit;
            };
            
            this.searchDraftForms = function(searchObject) {
            	//console.log("search1");
            	searchObject.isactive="true";
            	searchObject.ispublished="false";
                return formEndpoint.searchForms(searchObject).then(function(dto) {
					return formTransformer.searchDTOToSearchResults(dto);
				}, function(err) {
					console.error("Could not search Forms");
                    return $q.reject(err);
				});

            };
            
            this.searchPublishedForms = function(searchObject) {
            	//console.log("search");
                return formEndpoint.searchForms(searchObject).then(function(dto) {
					return formTransformer.searchDTOToSearchResults(dto);
				}, function(err) {
					console.error("Could not search Forms");
                    return $q.reject(err);
				});

            };
            
            this.searchDisabledForms = function(searchObject) {
            	searchObject.isactive="false";
            	searchObject.ispublished="true";
                return formEndpoint.searchForms(searchObject).then(function(dto) {
					return formTransformer.searchDTOToSearchResults(dto);
				}, function(err) {
					console.error("Could not search Forms");
                    return $q.reject(err);
				});
            	
            };
            
            this.deleteForm = function(id) {
            	
                return formEndpoint.deleteForm(id).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not delete masterfield");
                    return $q.reject(err);
                });

            };
			
			// additional validation for permit that does not fit well inside validators
			this.validateForm = function(permit) {
				
				var flatFields = _.reduce(permit.screens, function(acc, screen) {
					return acc.concat(screen.fields);
				}, []);
				
				var i;
				var isValidFeeExpression = true;
				
				for (i=0;i<permit.feeExpressions.length;i++){
					
					$log.debug("expression : "  + permit.feeExpressions[i].feeExpression);
					
					var missingFields = this.validateFeeExpressionFields(permit.feeExpressions[i].feeExpression, flatFields);
					
					$log.debug(missingFields);
					
					if(missingFields.length > 0) {
						
						alertsService.addAlert({
		           			 title: 'Error',
		           			 message: "The following master fields are present in your " + permit.feeExpressions[i].label + " expression, but not in the permit: " + missingFields,
		           			 type: 'error',
		           			 removeOnStateChange: 1
		           		});
						
						isValidFeeExpression = false;
					}
				}
				
				$log.debug("isValidate: " + isValidFeeExpression);
				
				return isValidFeeExpression;
				
				/*var missingFields = this.validateFeeExpressionFields(permit.feeExpression, flatFields);
				
				if(missingFields.length > 0) {
					
					alertsService.addAlert({
	           			 title: 'Error',
	           			 message: "The following master fields are present in your expression, but not in the permit: " + missingFields,
	           			 type: 'error',
	           			 removeOnStateChange: 1
	           		});
					
					return false;
				}
				
				return true;*/
			};
			
			// returns list of fields not matched in the permit or empty list if successful
			this.validateFeeExpressionFields = function(feeExpression, flatFields) {
				
				var flatFieldNames = _.map(flatFields, function(field) {
					return field.name;
				});
				
				var expressionFieldNames = [];
				var regex = /fields\[['"]([\w\s]+)['"]\]/g;
				var match;
				
				while(match = regex.exec(feeExpression)) {
					expressionFieldNames.push(match[1]);
				}
				
				return _.uniq(_.difference(expressionFieldNames, flatFieldNames));
				
			};
			
			this.feeExpressionInputValidations = function(feeExpression){
				if (feeExpression.label.length < 1){
					alertsService.addAlert({
						title: 'Error',
						message: 'Fee Expression - Label Cannot be Empty',
						type: 'error',
						removeOnStateChange: 1
					});
				}
				if (feeExpression.label.length > 255){
					alertsService.addAlert({
						title: 'Error',
						message: 'Fee Expression - Label must be 255 characters or less',
						type: 'error',
						removeOnStateChange: 1
					});
				}
				if (feeExpression.description.length > 1000){
					alertsService.addAlert({
						title: 'Error',
						message: 'Fee Expression - Description must be 1000 characters or less',
						type: 'error',
						removeOnStateChange: 1
					});
				}
				if (feeExpression.feeExpression.length > 1000){
					alertsService.addAlert({
						title: 'Error',
						message: 'Fee Expression - Expression must be 1000 characters or less',
						type: 'error',
						removeOnStateChange: 1
					});
				}
			};
			
			this.getFeeTypes = function() {
				return ['FEE_EXPRESSION', 'ECITY_FEE', 'PAYMENT_PLATFORM_FEE'];
			};
		}
									  
	])
           

})(angular.module('aet.domain.formBuilder'));


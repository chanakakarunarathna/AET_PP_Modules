(function(module) {
    
    module.service('formTransformer', ['genericTransformer', 'screenTransformer', 'CreateFormDTO', 'Form', '$log', '$filter',
        function(genericTransformer, screenTransformer, CreateFormDTO, Form, $log, $filter) {

            this.permitToCreateFormDTO = function(permit) { //permit = Form obj
                //return genericTransformer.objectToDTO(permit, CreateFormDTO);
            	var createFormDto = new CreateFormDTO();
            	
            	createFormDto.name = permit.name;
            	//createFormDto.feeExpression = permit.feeExpression;
            	createFormDto.feeExpressions = permit.feeExpressions;
            	
            	var noOfScreens = permit.screens.length;
            	var screenModelObj;
            	var screenDtoObj;
            	
            	for (var i=0; i<noOfScreens ; i++){
            		screenModelObj = permit.screens[i];
            		screenName = permit.screens[i].name;
            		screenDtoObj = screenTransformer.screenToScreenDTO(screenModelObj);
            		//createFormDto.screens.set(screenName, screenDtoObj);
            		createFormDto.screens[screenName] = screenDtoObj;
            	}
            	
				createFormDto.notifications = _.reduce(permit.notifications, function(result, value, key) {
					result[key] = value.split(',');
					return result;
				}, {});
				createFormDto.isPublished=permit.isPublished ;
				createFormDto.isActive=permit.isActive ;
            	return createFormDto;
            };
			
			this.permitToUpdateFormDTO = function(permit) {
				
            	var updateFormDto = new CreateFormDTO();
            	
            	updateFormDto.name = permit.name;
            	//updateFormDto.feeExpression = permit.feeExpression;
            	updateFormDto.feeExpressions = permit.feeExpressions;
            	
            	var noOfScreens = permit.screens.length;
            	var screenModelObj;
            	var screenDtoObj;
            	
            	for (var i=0; i<noOfScreens ; i++){
            		screenModelObj = permit.screens[i];
            		screenName = permit.screens[i].name;
            		screenDtoObj = screenTransformer.screenToScreenDTO(screenModelObj);
            		//createFormDto.screens.set(screenName, screenDtoObj);
            		updateFormDto.screens[screenName] = screenDtoObj;
            	}
            	
				updateFormDto.notifications = _.reduce(permit.notifications, function(result, value, key) {
					result[key] = value.split(',');
					return result;
				}, {});
				updateFormDto.isPublished=permit.isPublished ;
				updateFormDto.isActive=permit.isActive ;
            	return updateFormDto;
            };
            
            this.searchDTOToSearchResults = function(dto) {
				var searchResults = genericTransformer.DTOToSearchResults(dto.data);
				searchResults.results = dto.data.permits;
				
				return searchResults;
			};
			
			this.getDTOToForm = function(dto) {
				var permit = genericTransformer.DTOToObject(dto.data, Form);
				
				permit.screens = _.map(dto.data.screens, function(screen, key) {
					return screenTransformer.getDTOToScreen(screen, key);
				});
				
				permit.screens = $filter('orderBy')(permit.screens, 'order');
				
				permit.notifications = _.reduce(dto.data.notifications, function(result, value, key) {
					result[key] = value.join(',');
					return result;
				}, {});
				
				return permit;
			};
          

        }
]);

})(angular.module('aet.domain.formBuilder'));


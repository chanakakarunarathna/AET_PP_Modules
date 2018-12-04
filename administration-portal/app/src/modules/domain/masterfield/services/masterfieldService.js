(function(module) {
    
    module.service('masterfieldService', ['masterfieldEndpoint', '$q', '$log', 'masterfieldTransformer',
        function(masterfieldEndpoint, $q, $log, masterfieldTransformer) {

            this.createMasterfield = function(masterfield) {
				$log.debug("masterfieldService::updateMasterfield", masterfield)
				
				var createDTO = masterfieldTransformer.masterfieldToCreateDTO(masterfield);
				
                return masterfieldEndpoint.createMasterfield(createDTO).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not create masterfield");
                    return $q.reject(err);
                });

            };
            
            this.searchMasterfields = function(searchObject) {
            	$log.debug("You're in the searchMasterfields method (masterfieldService)");
            	
                return masterfieldEndpoint.searchMasterfields(searchObject).then(function(dto) {
					return masterfieldTransformer.searchDTOToSearchResults(dto);
				}, function(err) {
					console.error("Could not search masterfields");
                    return $q.reject(err);
				});

            };
			
			this.updateMasterfield = function(masterfield) {
				$log.debug("masterfieldService::updateMasterfield", masterfield);
				
				var updateDTO = masterfieldTransformer.masterfieldToUpdateDTO(masterfield)
				
				return masterfieldEndpoint.updateMasterfield(updateDTO).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not update masterfield");
                    return $q.reject(err);
                });
			};
            
            this.deleteMasterfield = function(mFieldId) {
            	//console.log("You're in the deleteMasterfields method (masterfieldService)");
            	//console.log(mFieldId);
            	
                return masterfieldEndpoint.deleteMasterfield(mFieldId).then(function(response) {
                    return response.data;
                }, function(err) {
                    console.error("Could not delete masterfield");
                    return $q.reject(err);
                });

            };
            
            this.getMasterfield = function(mFieldId) {
            	//console.log("You're in the getMasterfield method (masterfieldService)");
            	//console.log(mFieldId);
            	
                return masterfieldEndpoint.getMasterfield(mFieldId).then(function(dto) {
                    return masterfieldTransformer.getDTOToMasterField(dto);
                }, function(err) {
                    console.error("Could not get masterfield");
                    return $q.reject(err);
                });

            };
			
			this.getValueTypes = function() {
				return ['TEXT', 'NUMBER', 'DATE', 'YESNO', 'SINGLE_SELECT', 'MULTI_SELECT'];
			};
			
        }
    ]);

})(angular.module('aet.domain.masterfield'));


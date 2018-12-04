(function(module) {
    
    module.service('masterfieldEndpoint', ['$q', '$http', 'api', 'partnerDetails', '$log',
        function($q, $http, api, partnerDetails, $log) {

            this.createMasterfield = function (masterfieldRequestDTO) {
				$log.debug("masterField - Endpoint - createMasterfields", masterfieldRequestDTO);
				var partnerUrl = '/partner/' + partnerDetails.getSelectedPartner().id;
                var createMasterfieldURL = api('admin') + partnerUrl + '/masterfield';
				
                return $http.post(createMasterfieldURL,masterfieldRequestDTO);
            };
            
            this.searchMasterfields = function (searchObject) {
            	$log.debug("masterField - Endpoint - searchMasterfields", searchObject);
				var partnerUrl = '/partner/' + partnerDetails.getSelectedPartner().id;
                var searchMasterfieldsURL = api('admin') + partnerUrl + '/masterfield';
                return $http.get(searchMasterfieldsURL,{
					params: searchObject
                });
            };
			
			this.updateMasterfield = function (dto) {
				$log.debug("masterField - Endpoint - updateMasterfields", dto);
				var partnerUrl = '/partner/' + partnerDetails.getSelectedPartner().id;
                var editMasterfieldURL = api('admin') + partnerUrl + '/masterfield/' + dto.id;
				
                return $http.put(editMasterfieldURL,dto);
            };
            
            this.deleteMasterfield = function (mFieldId) {
            	$log.debug("masterField - Endpoint - deleteMasterfields", mFieldId);
				var partnerUrl = '/partner/' + partnerDetails.getSelectedPartner().id;
                var deleteMasterfieldURL = api('admin') + partnerUrl + '/masterfield/' + mFieldId;
				
                return $http.delete(deleteMasterfieldURL);
            };
            
            this.getMasterfield = function (mFieldId) {
            	$log.debug("masterField - Endpoint - getMasterfields", mFieldId);
				var partnerUrl = '/partner/' + partnerDetails.getSelectedPartner().id;
                var getMasterfieldURL = api('admin') + partnerUrl + '/masterfield/' + mFieldId;
				
                return $http.get(getMasterfieldURL);
            };

        }
    ]);

})(angular.module('aet.endpoints'));
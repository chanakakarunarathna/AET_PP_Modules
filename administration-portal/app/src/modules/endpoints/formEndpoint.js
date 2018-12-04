(function(module) {
    
    module.service('formEndpoint', ['$q', '$http', 'api', 'partnerDetails',
        function($q, $http, api, partnerDetails) {

            var controller = '/partner/';
            
            this.createForm = function (createFormDTO) {
            	var urlExtension = partnerDetails.getSelectedPartner().id + '/permit'; // 1000000000 is for partnerId
                var createFormURL = api('admin') + controller + urlExtension;
                
                /*return $http({
                    method: 'POST',
                    url: createFormURL,
                    data: createFormDTO,
                    headers: {'Content-Type': 'application/json;charset=utf-8'}
                });*/
				
                return $http.post(createFormURL,createFormDTO);
            };
			
			this.updateForm = function (dto, id) {
            	var urlExtension = partnerDetails.getSelectedPartner().id + '/permit/' + id; // 1000000000 is for partnerId
                var url = api('admin') + controller + urlExtension;
				
                return $http.put(url,dto);
            };
            
            this.searchForms = function (searchObject) {
            	//console.log(partnerDetails)
            	var urlExtension = partnerDetails.getSelectedPartner().id + '/permit'; // 1 is for partnerId
                var searchFormsURL = api('admin') + controller + urlExtension;
                //console.log("URL" + searchFormsURL);
                //console.log("DATA" + searchObject);
                return $http.get(searchFormsURL,{
					params: searchObject
                });
            };
            
            this.deleteForm = function (id) {
            	////console.log("masterField - Endpoint - searchMasterfields");
            	var urlExtension = partnerDetails.getSelectedPartner().id + '/permit/' + id; // 1 is for partnerId
                var deleteFormURL = api('admin') + controller + urlExtension;
                //console.log("URL " + deleteFormURL);
                return $http.delete(deleteFormURL);
            };
			
            this.getForm = function (permitId,searchObject) {
                var getFormURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/permit/' + permitId;
                return $http.get(getFormURL,{
					params: searchObject});
            };

        }
    ]);

})(angular.module('aet.endpoints'));
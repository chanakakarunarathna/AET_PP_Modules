(function(module) {

    module.service('questionEndpoint', ['$q', '$http', 'api', 'partnerDetails', 'partnerPlaceDetails',
        function($q, $http, api, partnerDetails, partnerPlaceDetails) {

            this.listQuestions = function(missionId) {
                var listQuestionsURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/partnerplace/' + partnerPlaceDetails.getSelectedPartnerPlace().id + '/mission/' + missionId + '/preview';
                return $http.get(listQuestionsURL);
            };


            this.listDiscoveryQuestions = function(missionId) {
                var listQuestionsURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/partnerplace/' + partnerPlaceDetails.getSelectedPartnerPlace().id + '/discovery/mission/' + missionId + '/preview';
                return $http.get(listQuestionsURL);
            };

        }
    ]);

})(angular.module('aet.endpoints'));

(function(module) {

  module.service('partnerConfigEndpoint', ['$q', '$http', 'api', 'partnerDetails',
    function($q, $http, api, partnerDetails) {

      this.createPartnerConfig = function(partnerConfig) {
        var createPartnerConfigUrl = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/config';
        return $http.post(createPartnerConfigUrl, partnerConfig);
      };

      this.updatePartnerConfig = function(partnerConfig) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/config';
        return $http.put(url, partnerConfig);
      };

      this.findPartnerConfig = function(id) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/config';
        return $http.get(url);
      };

      this.listPartnerConfig = function(params) {
        var listPartnerConfigURL = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/config';
        return $http.get(listPartnerConfigURL, {
          params: params
        });
      };

      this.listPartnerConfigsBySelectedPartner = function(partner) {
        var listPartnerConfigURL = api('admin') + '/partner/' + partner.id + '/config';
        return $http.get(listPartnerConfigURL, {
          params: {
            count: 0
          }
        });
      };

      this.deletePartnerConfig = function(id) {
        var url = api('admin') + '/partner/' + partnerDetails.getSelectedPartner().id + '/config';
        return $http.delete(url);
      };

      this.listAllPartnerConfig = function(params) {
        var listAllPartnerConfigURL = api('admin') + '/config';
        return $http.get(listAllPartnerConfigURL, {
          params: params
        });
      };

    }
  ]);

})(angular.module('aet.endpoints'));

(function(module) {

  module.service('s3Endpoint', ['$http', 'api', '$log',
    function($http, api, $log) {

      this.getPutAuth = function(parameters) {

        var url = api('admin') + '/s3/authHeader/put';

        return $http.get(url);

      };

      this.getPostParams = function(bucket, parameters) {
        var url = api('admin') + '/s3/params/' + bucket;

        return $http.get(url);

      };

      this.getS3Config = function() {
        var url = api('admin') + '/s3/config';

        return $http.get(url);

      };

      this.getObjectAuth = function(bucket, key) {

        var params = {
          key: key,
          bucket: bucket
        };
        var url = api('admin') + '/s3/authHeader/get';
        return $http.get(url, {
          params: params
        });

      };

      this.getDeleteParams = function(bucket, key) {

        var params = {
          key: key,
          bucket: bucket
        };
        var url = api('admin') + '/s3/authHeader/delete';

        return $http.get(url, {
          params: params
        });

      };

    }
  ]);

})(angular.module('aet.endpoints'));

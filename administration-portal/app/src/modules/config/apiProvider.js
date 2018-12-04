(function (module) {

  module.provider('api', [function () {

    var apis = {};

    return {

      addApi: function (name, url) {
        apis[name] = url;
      },

      $get: [function () {

        return function (apiName) {
          return apis[apiName];
        };

      }]
    };
  }]);

})(angular.module('aet.config'));
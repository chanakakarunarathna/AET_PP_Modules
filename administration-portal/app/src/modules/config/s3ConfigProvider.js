(function (module) {

  module.provider('s3config', ['__env', function (__env) {

    var bucket = __env.s3bucket;

    return {
      $get: [function () {

        return function () {
          return bucket;
        };

      }]
    };
  }]);

})(angular.module('aet.config'));

(function(module) {

    module.service('pingImage', ['$sce', '$q', '$timeout', function($sce, $q, $timeout) {
        this.checkAndSetUrl = function(url, iterations) {
          var deferred = $q.defer();
          var i = 0;

          if(angular.isUndefined(url)) {
            deferred.reject('url is undefined');
          }
          else {
            imageExists(url, deferred);
          }


          function imageExists(url, d) {
            var img = new Image();
            img.onload = function() {
              d.resolve(url);
            };
            img.onerror = function() {
              ++i;
              if(i < iterations) {
                $timeout(function() {
                  var newUrl;

                  if(url.match(/\?[0-9]+$/)) {
                    newUrl = url.replace(/\?[0-9]+$/, '?' + new Date().getTime());
                  }
                  else {
                    newUrl = url + '?' + new Date().getTime();
                  }
                  imageExists(newUrl, d);
                }, 1000);
              }
              else {
                d.reject();
              }
            };
            img.src = url;
          };

          return deferred.promise;
        }
    }]);

})(angular.module('aet.pingImage'));

(function(module) {

  module.controller('viewVideoController', ['$scope', '$state', '$modalInstance', '$log',
    function($scope, $state, $modalInstance, $log) {

      $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
      };

      $scope.API = null;

      $scope.onPlayerReady = function (API) {
        $scope.API = API;
      };

      /*
      $scope.downloadFile = function (){
        console.log("down", $scope.originalUrl);
        var xhr = new XMLHttpRequest();
        xhr.open('GET', $scope.originalUrl, true);
        xhr.responseType = "arraybuffer"
        blob = new Blob([xhr.response], {type: "video/mp4"});
        saveAs(blob,'test.mp4');
      }
      */

      $scope.config = {
        preload: "none",
        autoPlay: true,
        sources: $scope.sources,
        theme:{
               url: "assets/css/videogular.css"
             }
      };


    }
  ]);

})(angular.module('aet-directives-hierarchyMap'));

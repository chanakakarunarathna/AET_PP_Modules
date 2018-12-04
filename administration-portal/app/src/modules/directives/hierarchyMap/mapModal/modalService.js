(function (module) {

  module.service('mapModalService', ['$modal', '$log', '$q', '$rootScope',
    function ($modal, $log, $q, $rootScope) {

      this.deleteMapModal = function (name, positionIndex) {
        var scope = $rootScope.$new(true);
        scope.name = name;
        scope.positionIndex = positionIndex;
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/deleteMapModal.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.addCommentOrFileModal = function () {
        var scope = $rootScope.$new(true);
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/addCommentsFilesModal.html',
          controller: 'MapModalInstanceController',
          backdrop: "static",
          scope: scope
        });
      };

      this.addFileModal = function () {
        var scope = $rootScope.$new(true);
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/addFilesModal.html',
          controller: 'FileUploadModalController',
          backdrop: "static",
          scope: scope
        });
      };

      this.addMapModal = function (name) {
        var scope = $rootScope.$new(true);
        scope.name = name;
        scope.dataModal = [];
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/addMapModal.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.addActionMapModal = function (name, channels) {
        var scope = $rootScope.$new(true);
        scope.name = name;
        scope.channels = channels;
        scope.dataModal = [];
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/addActionMapModal.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.editActionMapModal = function (action, channels) {
        var scope = $rootScope.$new(true);
        scope.name = action.title;
        scope.title = action.title;
        scope.channels = channels;
        scope.channel = action.channel;
        scope.dataModal = [];
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/editActionMapModal.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.editMapModal = function (name, type) {
        var scope = $rootScope.$new(true);
        scope.type = type;
        scope.name = name.title;
        if(type == 'Stage'){
          scope.stageTitle = name.title;
          scope.touchpointTitle = undefined;
        }else{
          scope.stageTitle = undefined;
          scope.touchpointTitle = name.title;
        }

        scope.dataModal = [];
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/editMapModal.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.moreCommentTxt = function (commentTxt) {
        var scope = $rootScope.$new(true);
        scope.commentTxt = commentTxt;
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/moreCommentTxt.html',
          controller: 'MapModalInstanceController',
          scope: scope
        });
      };

      this.viewActionDetails = function (actionData, stageData, touchpointData, averageColorFn, closeActionDetailsFn, curveType) {
        var scope = $rootScope.$new(true);
        scope.actionData = actionData;
        scope.stageData = stageData;
        scope.touchpointData = touchpointData;
        scope.averageColorFn = averageColorFn;
        scope.closeActionDetailsFn = closeActionDetailsFn;
        scope.curveType = curveType;
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/viewActionDetails.html',
          controller: 'MapModalInstanceController',
          scope: scope,
          windowClass: 'summary'
        });
      };

      this.viewStageTouchpointDetails = function(type, data, stage){
        var scope = $rootScope.$new(true);
        scope.type = type;
        scope.data = data;
        scope.data.stage = stage;
        /*scope.actionData = actionData;
        scope.stageData = stageData;
        scope.touchpointData = touchpointData;
        scope.averageColorFn = averageColorFn;
        scope.closeActionDetailsFn = closeActionDetailsFn;
        scope.curveType = curveType;*/
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/viewStageTouchpointDetails.html',
          controller: 'MapModalInstanceController',
          scope: scope,
          windowClass: 'summary'
        });
      };

      this.viewVideo = function (sources, originalUrl) {
        var scope = $rootScope.$new(true);
        scope.sources = sources;
        scope.originalUrl = originalUrl;
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/viewVideoModal.html',
          controller: 'viewVideoController',
          scope: scope
        });
      };

      this.viewImage = function (url) {
        var scope = $rootScope.$new(true);
        scope.url = url;
        return $modal.open({
          templateUrl: 'src/modules/directives/hierarchyMap/mapModal/templates/viewImageModal.html',
          controller: 'viewImageController',
          scope: scope
        });
      };
    }
  ]);

})(angular.module('aet-directives-hierarchyMap'));

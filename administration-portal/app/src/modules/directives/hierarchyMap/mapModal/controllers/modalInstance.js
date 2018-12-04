(function (module) {

  module.controller('MapModalInstanceController', ['$scope', '$modalInstance', '$log', '$window', 'uploadManager', 's3Uploader', 's3config', 'partnerDetails', 'HierarchyMapService', '$parse', 'userDetails', '$sce', 'mapModalService', 'mapService', 'reportService', '$filter', '$rootScope', 'CSVService',
    function ($scope, $modalInstance, $log, $window, uploadManager, s3Uploader, s3config, partnerDetails, HierarchyMapService, $parse, userDetails, $sce, mapModalService, mapService, reportService, $filter, $rootScope, CSVService) {

      //Action Modal
      //Used to auto populate popup modal
      $scope.$watch(function () {
          return HierarchyMapService.getMapData();
        },
        function (newVal, oldVal) {
          var tab = HierarchyMapService.getActiveTab();
          var i, j, k;
          var isFound = false;

          // Set stage data If this is a stage level popup
          if ($scope.type == 'stage'){
            // If we are in clicked stage positionIndex set new stage data into this stage
            for (i = 0; i < newVal.stages.length; i++) {
              if ($scope.data.positionIndex == newVal.stages[i].positionIndex){
                $scope.data = newVal.stages[i];
                break;
              }
            }

          } else if ($scope.type == 'touchpoint'){ // Set stage data and touchpoint data If this is a touchpoint level popup
            for (i = 0; i < newVal.stages.length; i++) {

              // If we arrived to the selected touchpoint then break
              if ($scope.data.stage == undefined || isFound){
                break;
              }

              // Set the new data to selected touchpoint and its parent stage
              if ($scope.data.stage && $scope.data.stage.positionIndex == newVal.stages[i].positionIndex){
                for (j = 0; j < newVal.stages[i].touchpoints.length; j++){
                  if ($scope.data.positionIndex == newVal.stages[i].touchpoints[j].positionIndex){
                    $scope.data = newVal.stages[i].touchpoints[j];
                    $scope.data.stage = newVal.stages[i];
                    isFound = true;
                  }
                }
              }
            }
          } else { // Set stage, touchpoint and action data If this is a action level popup (This was there before. But after introducing stage and touchpoint pop ups into this same controller, used conditional check to use this for stages and touchpoints)
            for (i = 0; i < newVal.stages.length; i++) {

              // If we arrived to the selected action then break
              if ($scope.stageData == undefined || isFound) {
                break;
              }

              // Set the new data to selected parent (stage, touchpoint) and action
              if ($scope.stageData != undefined && $scope.stageData.positionIndex == newVal.stages[i].positionIndex) {
                $scope.stageData = newVal.stages[i];
                for (j = 0; j < newVal.stages[i].touchpoints.length; j++) {
                  if ($scope.touchpointData.positionIndex == newVal.stages[i].touchpoints[j].positionIndex) {
                    $scope.touchpointData = newVal.stages[i].touchpoints[j];
                    for (k = 0; k < newVal.stages[i].touchpoints[j].actions.length; k++) {
                      if ($scope.actionData.positionIndex == newVal.stages[i].touchpoints[j].actions[k].positionIndex) {
                        $scope.actionData = newVal.stages[i].touchpoints[j].actions[k];
                        isFound = true;
                      }
                    }
                  }
                }
              }

            }
          }

          HierarchyMapService.setActiveTab(tab);
        });

      $scope.options = {
        colours: [{
          fillColor: ["#c6000a", "#de6a70", "#96a1a1", "#bbe88e", "#9bce67"]
        }],
        listOfLocations: ['1', '2', '3', '4', '5'],
        options: {
          barShowStroke: false,
          scaleOverlay: true,
          scaleOverride: false,
          scaleShowLabels: true,
          scaleLineColor: 'rgba(0,0,0,0.5)',
          scaleGridLineColor: "rgba(0,0,0,.05)",
          scaleShowHorizontalLines: true,
          scaleShowVerticalLines: false,
          scaleGridLineWidth: 5,
          barValueSpacing: 6,
          responsive: true,
          //maintainAspectRatio: false
        }
      };

      $scope.loggedUserID = userDetails.getUserId();
      $scope.HierarchyMapService = HierarchyMapService;

      //this function is used by comment templates
      $scope.mediaPosition = function () {
        if ($scope.comment) {
          return 'pull-right';
        } else {
          return 'pull-left';
        }
      }

      //this function is used by action-summary templates
      $scope.showNoInsightAvailable = function (insightLists) {
        var isNoInsightAvailableVisible = false;
        //used for store mapType(customers or employees) insight object count
        var count = 0;
        angular.forEach(insightLists, function (insightList, insightListKey) {
          if (insightList.mapType === HierarchyMapService.getMapType()) {
            count++;
          }
        });
        if (count === 0) {
          isNoInsightAvailableVisible = true;
        }

        return isNoInsightAvailableVisible;
      };
      //Action Modal


      $scope.ok = function () {
        $modalInstance.close();
      };

      $scope.edit = function () {
        $modalInstance.close({
          title: $scope.stageTitle == undefined ? $scope.touchpointTitle : $scope.stageTitle,
          channel: $scope.channel
        });
      };

      $scope.save = function () {
        $modalInstance.close({
          title: $scope.title,
          channel: $scope.channel,
          comment: $scope.comment,
          media: $scope.mediaList
        });
      };

      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };

      /*S3 Implementation*/

      $scope.downloadFile = function (uri) {
        s3Uploader.getDownloadSignature(uri).then(function (response) {
          window.open(response.data.downloadLink, '_blank');
        }, function (err) {
          $log.error("Could not save application", err);
        })
      };

      $scope.mediaList = [];

      $scope.$watch('mediaList', function (newVal, oldVal) {
        if (angular.isDefined($scope.mediaList)) {
          if (!$scope.uploadManager.uploading && !$scope.uploadManager2.uploading && !$scope.uploadManager3.uploading) {
            //moving the empty answers to bottom
            $scope.mediaList.sort(function (a, b) {
              if (angular.isUndefined(a))
                a = "";
              if (angular.isUndefined(b))
                b = "";
              var va = (a === null) ? "" : "" + a,
                vb = (b === null) ? "" : "" + b;

              return va < vb ? 1 : (va === vb ? 0 : -1);
            });

            //Rearraging the list
            var i = $scope.mediaList.length;
            while (i--) {
              if (angular.isDefinedAndNotEmpty($scope.mediaList[i])) {
                //$scope.mediaList.move(0, i);
                $scope.mediaList = angular.moveArrayElement(0, i, $scope.mediaList);
              }
            }
          }
          //question.previousAnswer = angular.copy(question.answer);
          if ((angular.isDefinedAndNotEmpty($scope.mediaList[0]) && angular.isDefinedAndNotEmpty($scope.mediaList[0])) || (angular.isDefinedAndNotEmpty($scope.mediaList[1]) && angular.isDefinedAndNotEmpty($scope.mediaList[1]))) {
            $scope.uploadManager2.isVisible = true;
          }

          if ((angular.isDefinedAndNotEmpty($scope.mediaList[1]) && angular.isDefinedAndNotEmpty($scope.mediaList[1])) || (angular.isDefinedAndNotEmpty($scope.mediaList[2]) && angular.isDefinedAndNotEmpty($scope.mediaList[2]))) {
            $scope.uploadManager3.isVisible = true;
          }

          if ((angular.isUndefinedOrNull($scope.mediaList[0]) || angular.isUndefinedOrNull($scope.mediaList[0])) && (angular.isUndefinedOrNull($scope.mediaList[1]) || angular.isUndefinedOrNull($scope.mediaList[1]))) {
            $scope.uploadManager2.isVisible = false;
          }

          if ((angular.isUndefinedOrNull($scope.mediaList[1]) || angular.isUndefinedOrNull($scope.mediaList[1])) && (angular.isUndefinedOrNull($scope.mediaList[2]) || angular.isUndefinedOrNull($scope.mediaList[2]))) {
            $scope.uploadManager3.isVisible = false;
          }
        }
      }, true);

      var uploadOptions = {
        destroyWithScope: $scope,
        bucket: s3config(),
        acl: 'public-read',
        folder: '/administration/' + partnerDetails.getSelectedPartner().id + '/partnerPlace/insights/',
        maxsize: '300',
        fileTypes: ['gif', 'jpg', 'jpeg', 'png', 'gif', 'ico', 'svg', 'psd', 'raw', 'tiff', 'mp3', 'm4a', 'm4b', 'm4p', 'm4v', 'ogg', 'wav', 'mp4', 'm4v', 'mov', 'wmv', 'avi', 'mpg', 'mpeg', 'ogv', '3gp', '3g2', '3gpp', '3gp2', 'aiff', 'aif', 'aifc', 'cdda', 'amr', 'swa', 'flac', 'pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'rtf']
      };

      $scope.uploadManager = uploadManager.newS3UploadId(uploadOptions);
      $scope.uploadManager2 = uploadManager.newS3UploadId(uploadOptions);
      $scope.uploadManager3 = uploadManager.newS3UploadId(uploadOptions);

      $scope.getFileName = function (fileURL) {
        if (!fileURL) {
          return '';
        }
        var fileName = fileURL.split("/").pop();
        fileName = fileName.substring(fileName.indexOf("_") + 1);
        return fileName.substring(0, fileName.lastIndexOf('.'));
      };

      angular.isUndefinedOrNull = function (val) {
        return angular.isUndefined(val) || val === null || val === ""
      };

      angular.isDefinedAndNotEmpty = function (val) {
        return angular.isDefined(val) && !angular.equals(val, "") && !angular.equals(val, null);
      };

      angular.moveArrayElement = function (old_index, new_index, array) {
        if (new_index >= array.length) {
          var k = new_index - array.length;
          while ((k--) + 1) {
            array.push(undefined);
          }
        }
        array.splice(new_index, 0, array.splice(old_index, 1)[0]);
        return array;
      };

      //videogular code block start

      var mediaNo, fileType, fileExtension, sourceObj, videoObj;
      var videosArr = [];
      //$scope.includeVideoPlayer = false;

      //Set videos array
      if ($scope.actionData != undefined || $scope.actionData != null) {
        $scope.HierarchyMapService.getMapType($scope.actionData.feedbackSummary).mediaSummaries.forEach(function (media, index) {

          fileType = HierarchyMapService.getFileType(media.mediaUrl);
          fileExtension = HierarchyMapService.getFileExtension(media.mediaUrl);

          if (fileType == 'video') {

            /*if ($scope.includeVideoPlayer == false){
             $scope.includeVideoPlayer = true;
             }*/

            //sourceObj = {src:$sce.trustAsResourceUrl(media.mediaUrl), type: "video/" + fileExtension}; // issue with mov extension

            var url = null;

            if (angular.isDefined(media.processedMediaUrl) && !angular.equals(media.processedMediaUrl, null)) {
              url = media.processedMediaUrl;
            } else {
              url = media.mediaUrl;
            }

            videoObj = {
              sources: [
                {src: $sce.trustAsResourceUrl(url), type: "video/mp4"},
                {src: $sce.trustAsResourceUrl(url), type: "video/webm"},
                {src: $sce.trustAsResourceUrl(url), type: "video/ogg"},
                {src: $sce.trustAsResourceUrl(url), type: "video/mov"},
                {src: $sce.trustAsResourceUrl(url), type: "video/quicktime"},
                {src: $sce.trustAsResourceUrl(url), type: "video/aaf"},
                {src: $sce.trustAsResourceUrl(url), type: "video/3gp"},
                {src: $sce.trustAsResourceUrl(url), type: "video/avi"},
                {src: $sce.trustAsResourceUrl(url), type: "video/wmv"},
                {src: $sce.trustAsResourceUrl(url), type: "video/m4v"}

              ], mediaId: index, originalUrl: media.mediaUrl
            };

            videosArr.push(videoObj);
          }
        });
      }

      //Set Selected Video to the player
      var setVideo = function (index) {

        var videoIndex;

        for (videoIndex = 0; videoIndex < videosArr.length; videoIndex++) {
          if (videosArr[videoIndex].mediaId == index) {
            mapModalService.viewVideo(videosArr[videoIndex].sources, videosArr[videoIndex].originalUrl);


            break;
          }
        }


      };

      //View media from browser or play video
      $scope.viewMedia = function (link, processedLink, index) {
        var fileType = HierarchyMapService.getFileType(link);
        var fileExtension = HierarchyMapService.getFileExtension(link);

        if (fileExtension == 'mp4' || fileExtension == 'webm' || fileExtension == 'ogg' || fileExtension == 'mov' || fileExtension == 'ogv') {
          setVideo(index);
        } else if (fileType == 'image') {
          mapModalService.viewImage(link);
        } else {
          window.open(link, '_blank');
        }
      };

      $scope.view_media = function(media){
        var url = (angular.isDefined(media.processedMediaUrl) && !angular.equals(media.processedMediaUrl, null)) ? media.processedMediaUrl : media.mediaUrl;

        var fileType = HierarchyMapService.getFileType(url || media.mediaUrl);
        var fileExtension = HierarchyMapService.getFileExtension(url  || media.mediaUrl);
        var sources = [
          {src: $sce.trustAsResourceUrl(url), type: "video/mp4"},
          {src: $sce.trustAsResourceUrl(url), type: "video/webm"},
          {src: $sce.trustAsResourceUrl(url), type: "video/ogg"},
          {src: $sce.trustAsResourceUrl(url), type: "video/mov"},
          {src: $sce.trustAsResourceUrl(url), type: "video/quicktime"},
          {src: $sce.trustAsResourceUrl(url), type: "video/aaf"},
          {src: $sce.trustAsResourceUrl(url), type: "video/3gp"},
          {src: $sce.trustAsResourceUrl(url), type: "video/avi"},
          {src: $sce.trustAsResourceUrl(url), type: "video/wmv"},
          {src: $sce.trustAsResourceUrl(url), type: "video/m4v"}
        ];

        if (fileType === 'video') {
          mapModalService.viewVideo(sources, media.mediaUrl);
        } else if (fileType == 'image') {
          mapModalService.viewImage(media.mediaUrl);
        } else {
          window.open(media.mediaUrl, '_blank');
        }

      };

      //videogular code block end

      // this is a fix for rendering the popover/tooltip in ng-repeat
      $scope.forceRenderToolTip = function () {
        $('span.tooltip-hover').webuiPopover('destroy').webuiPopover({
          trigger: 'hover',
          placement: 'right',
          delay: {
            show: 0,
            hide: 500
          }
        });
      };

      $scope.getDownLoadLink = function (mediaUrl) {
        var inputKey = mediaUrl.split(".s3.amazonaws.com/")[1];
        var bucketName = mediaUrl.split(".s3.amazonaws.com/")[0].split("://")[1];
        mapService.getDownloadMediaUrl(inputKey, bucketName).then(function (response) {
          window.open(response, '_self');
        }, function (err) {
        });
      }

      $scope.downloadActionData = function (stageId, touchpointId, actionId) {
        var mapType = HierarchyMapService.getMapType();
        if (mapType == 'CUSTOMERS') {
          mapType = 'customer';
        } else if (mapType == 'EMPLOYEES') {
          mapType = 'employee';
        }

        reportService.getActionDataReport(stageId, touchpointId, actionId, mapType).then(function (actDataRes) {
          $scope.exportExcel(actDataRes.data);
        }, function (actDataResErr) {
          console.error(actDataResErr);
        });
      };

      $scope.exportExcel = function (actionData) {
        var blob = new Blob([CSVService.convertActionDataReportToCSV(actionData)], {
          type: "text/csv;charset=utf-8;"
        });
        saveAs(blob, actionData.partnerPlace.title + '_' + actionData.actionTitle + $filter('date')(new Date(), "MMMM dd yyyy") + ".csv");

      };

      function convertDoubleQuoteToEscapeChar(str) {
        var csv = str.replace(/"/g, '\""');    //wrapping double quotes
        csv = '\"' + csv + '\"';                  //wrapping all the word with double quotes
        return csv;
      }

      function formatDate(date) {
        var formattedDate = undefined;
        date = new Date(date);
        formattedDate = $filter('date')(date, 'yyyy-MM-dd');
        return formattedDate;
      }

      $scope.filterdQuestions = [];

      $scope.initQuestions = function (questions) {
        $scope.filterdQuestions =  HierarchyMapService.getQuestions(questions, undefined, 10);
      };

      $scope.getQuestions = function (questions, currentPage) {
        $scope.filterdQuestions =  HierarchyMapService.getQuestions(questions, currentPage, 10);
      };

      $scope.maxSize = 10;
      $scope.currentPage = 1;

      $scope.downloadQuestionsCSV = function(type, data){

        var stageId, touchpointId;

        stageId = (type === 'touchpoint' ? data.stage.id : data.id);
        touchpointId = (type === 'touchpoint' ? data.id : undefined);

        var mapType = HierarchyMapService.getMapType();
        if (mapType == 'CUSTOMERS') {
          mapType = 'customer';
        } else if (mapType == 'EMPLOYEES') {
          mapType = 'employee';
        }

        reportService.getDiscoveryDataReport(stageId, touchpointId, mapType).then(function (response) {
          CSVService.compileTargetDiscoveryDataCSV(response).then(function(csv){
            var blob = new Blob([csv], {
                type: "text/csv;charset=utf-8;"
            });

            saveAs(blob, "Discovery Mission Report " + $filter('date')(new Date(), "MMMM dd yyyy") + ".csv");
          });
        }, function (err) {
          console.error(err);
        });
      };

      $scope.downloadQuestionCSV = function(type, data, questionId){
        var stageId, touchpointId, actionId;

        if(type === 'STAGE'){
          stageId = data.id;
        } else if (type === 'TOUCHPOINT') {
          stageId = data.stage.id;
          touchpointId = data.id;
        } else {
          stageId = data.stage.id;
          touchpointId = data.touchpoint.id;
          actionId = data.id;
        }

        var mapType = HierarchyMapService.getMapType();
        if (mapType == 'CUSTOMERS') {
          mapType = 'customer';
        } else if (mapType == 'EMPLOYEES') {
          mapType = 'employee';
        }

        reportService.getDiscoveryQuestionDataReport(stageId, touchpointId, actionId, questionId, mapType).then(function (response) {
          CSVService.compileTargetDiscoveryQuestionDataCSV(response).then(function(csv){
            var blob = new Blob([csv], {
                type: "text/csv;charset=utf-8;"
            });

            saveAs(blob, "Discovery Mission Report " + $filter('date')(new Date(), "MMMM dd yyyy") + ".csv");
          });
        }, function (err) {
          console.error(err);
        });
      };
    }

  ]);

})(angular.module('aet-directives-hierarchyMap'));

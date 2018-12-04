(function(module) {

    module.controller('FileUploadModalController', ['$scope', '$modalInstance', '$log', '$window', 'uploadManager', 's3Uploader', 's3config', 'partnerDetails', 'HierarchyMapService', '$parse', 'userDetails', '$sce', 'mapModalService', 'mapService', 'reportService', '$filter', '$rootScope', 'CSVService',
        function($scope, $modalInstance, $log, $window, uploadManager, s3Uploader, s3config, partnerDetails, HierarchyMapService, $parse, userDetails, $sce, mapModalService, mapService, reportService, $filter, $rootScope, CSVService) {

            $scope.data = {};

            var uploadOptions = {
                destroyWithScope: $scope,
                bucket: s3config(),
                acl: 'public-read',
                folder: '/administration/' + partnerDetails.getSelectedPartner().id + '/partnerPlace/adminUserMedia/',
                maxsize: '300',
                fileTypes: ['gif', 'jpg', 'jpeg', 'png', 'gif', 'ico', 'svg', 'psd', 'raw', 'tiff', 'mp3', 'm4a', 'm4b', 'm4p', 'm4v', 'ogg', 'wav', 'mp4', 'm4v', 'mov', 'wmv', 'avi', 'mpg', 'mpeg', 'ogv', '3gp', '3g2', '3gpp', '3gp2', 'aiff', 'aif', 'aifc', 'cdda', 'amr', 'swa', 'flac', 'pdf', 'doc', 'docx', 'xls', 'xlsx', 'ppt', 'pptx', 'txt', 'rtf']
            };

            $scope.getFileName = function(fileURL) {
                if (!fileURL) {
                    return '';
                }
                var fileName = fileURL.split("/").pop();
                fileName = fileName.substring(fileName.indexOf("_") + 1);
                return fileName.substring(0, fileName.lastIndexOf('.'));
            };

            $scope.downloadFile = function(uri) {
                s3Uploader.getDownloadSignature(uri).then(function(response) {
                    window.open(response.data.downloadLink, '_blank');
                }, function(err) {
                    $log.error("Could not save application", err);
                })
            };

            $scope.uploadManager = uploadManager.newS3UploadId(uploadOptions);

            $scope.save = function() {
                $modalInstance.close({
                  mediaURL: $scope.data.mediaURL
                });
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };
        }
    ]);

})(angular.module('aet-directives-hierarchyMap'));

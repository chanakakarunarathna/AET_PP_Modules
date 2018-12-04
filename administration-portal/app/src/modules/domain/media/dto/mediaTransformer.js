(function(module) {

  module.service('mediaTransformer', ['genericTransformer', 'Media', 'MediaType', '$sce', 'pingImage',
    function(genericTransformer, Media, MediaType, $sce, pingImage) {

      this.getDTOToMedia = function(dto) {
        var media = genericTransformer.DTOToObject(dto, Media);
        media.type = this.getFileType(media.mediaUrl);

        if(angular.isDefined(media.thumbnailUrl)) {
          media.imageUrl = $sce.trustAsResourceUrl(media.thumbnailUrl);
        }

        if(media.type === 'video') {

          if(media.processedMediaUrl) {
            media.videoSource = {src: $sce.trustAsResourceUrl(media.processedMediaUrl), type: 'video/mp4'};
          }
          else {
            media.videoSource = {src: $sce.trustAsResourceUrl(media.mediaUrl), type: 'video/' + this.getFileExtension(media.mediaUrl)};
          }
        }
        else if(media.type === 'audio') {

          if(media.processedMediaUrl) {
            media.audioSource = {src: $sce.trustAsResourceUrl(media.mediaUrl), type: 'audio/mpeg'};
          }
        }

        return media;
      };

      this.getFileExtension = function(fileURL) {
          var ext = fileURL.split('.').pop();
          var extToLowercase = angular.lowercase(ext);

          return extToLowercase;
      };

      this.getFileType = function(fileURL) {
          var ext = this.getFileExtension(fileURL);
          var fileType;

          if (ext == 'aaf' || ext == '3gp' || ext == 'avi' || ext == 'mp4' || ext == 'wmv' || ext == 'mov' || ext == 'm4v') {
              fileType = 'video';
          } else if (ext == 'mp3' || ext == 'wma' || ext == 'cda' || ext == 'wav' || ext == 'amr' || ext == 'ima4') {
              fileType = 'audio';
          } else if (ext == 'jpg' || ext == 'gif' || ext == 'jpeg' || ext == 'png' || ext == 'ico' || ext == 'svg' || ext == 'tiff') {
              fileType = 'image';
          } else {
              fileType = 'file';
          }

          return fileType;
      };

    }
  ]);

})(angular.module('aet.domain.media'));

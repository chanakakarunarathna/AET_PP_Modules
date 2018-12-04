(function(module) {

  module.service('Media', [function() {

    function Media() {

      this.id = undefined;
      this.answerId = undefined;
      this.mediaUrl = undefined;
      this.processedMediaUrl = undefined;
      this.missionType = undefined;
      this.thumbnailUrl = undefined;
      this.creatorId = undefined;
      this.creator = undefined;
      this.creatorRole = undefined;
      this.createdDate = undefined;
      this.promotedDate = undefined;
      this.lastModifiedDate = undefined;
      this.mapType = undefined;
      this.isNew = undefined;
      this.username = undefined;

    }

    return Media;

  }]);

})(angular.module('aet.domain.partner'));

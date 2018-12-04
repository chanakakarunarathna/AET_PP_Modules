(function(module) {
    
	module.service('uploadRegistry', [
		function() {

			var instancesMap = {};

			return {

				getUpload: function(uploadId) {
					return instancesMap[uploadId];
				},

				putUpload: function(uploadId, upload) {
					instancesMap[uploadId] = upload;
					return upload;
				},

				removeUpload: function(uploadId) {
					if(instancesMap.hasOwnProperty(uploadId)) {
						delete instanceMap[uploadId];
					}
				}
			}


		}
	]);
	
})(angular.module('ecs-upload'));

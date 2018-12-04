(function(module) {
    
	module.service('uploadManager', ['$timeout', 'uploadRegistry', '$q', 'S3Upload',
		function ($timeout, uploadRegistry, $q, S3Upload) {

			this.getUniqueId = function() {
				return $timeout(function() {return new Date().getTime()}, 2);
			};

			this.newS3Upload = function(id, options) {
				var upload = new S3Upload(id, options);
				uploadRegistry.putUpload(id, upload);
				handleScope(options, upload);
				return upload;
			};
			
			/*this.newS3UploadId = function(options) {
				return this.getUniqueId().then(function(id) {
					
					var upload = new S3Upload(id, options);
					uploadRegistry.putUpload(id, upload);
					handleScope(options, upload);
					return upload;

				});
			};*/
			
			this.newS3UploadId = function(options) {
					var id = this.getUniqueId();
					var upload = new S3Upload(id, options);
					uploadRegistry.putUpload(id, upload);
					handleScope(options, upload);
					return upload;

			};

			this.destroyUpload = function(upload) {
				upload.destroy();
				uploadRegistry.removeUpload(upload.id);
			};

			var handleScope = function(options, upload) {
				var self = this;

				if(angular.isDefined(options.destroyWithScope)) {
					options.destroyWithScope.$on('destroy', function(event) {
						self.destroyUpload(upload);
					});
				}
			};

		}
	]);
	
})(angular.module('ecs-upload'));
(function(module) {
    
	module.service('S3Upload', ['$q', 'BaseUpload', 's3Uploader', '$log',
		function($q, BaseUpload, s3Uploader, $log) {

			// ******** ******** ********
			// ******** s3 Upload ********
			// ******** ******** ******** ********
			function S3Upload(id, options) {
				var self = this;

				this.__superConstructor.apply(this, arguments);

				this.upload = function() {

					this.uploading = true;

					return s3Uploader.post(this.s3Options.bucket,
							this.s3Options.folder,
							this.s3Options.acl,
							this.selectedFile,
							this.fieldValue
					).then(function(upload) {
						self.xhr = upload.xhr;
						upload.promise.then(function (xhr) {
							self.uploading = false;
							self.complete = true;
							self.uploadDeferred.resolve(self.s3Options.onSuccess(xhr));
						}, function (error) {
							if(error.type && error.type == 'cancelled') {
								self.uploadDeferred.resolve({
									message: 'Cancelled'
								});
							}
							else {
								$log.error(error);
								self.uploadDeferred.reject(error);
							}
							self.uploading = false;
							self.complete = false;
						}, function(progress) {
							self.uploadDeferred.notify(progress);
						});
					});

				};

				this.stop = function() {
					var deferred = $q.defer();
					this.xhr.abort();
					deferred.resolve('Abort');
					return deferred.promise;
				};

				this.onSuccess = function(xhr) {
					return {
						url: decodeURIComponent(xhr.getResponseHeader("Location"))
					}
				}

				this.defaults = {
					onSuccess: self.onSuccess
				};

				this.s3Options = angular.extend(this.defaults, options);
				
				this.getFileName=function(fileURL){
					if(!fileURL) {
						return '';
					}
	            	var fileName=fileURL.split("/").pop();
	            	fileName=fileName.substring(fileName.indexOf("_")+1);
	            	return fileName.substring(0,fileName.lastIndexOf('.'));
				};
                
                this.downloadFile = function(uri) {
                    s3Uploader.getDownloadSignature(uri).then(function(response){
                        window.open(response.data.downloadLink, '_blank');
                    }, function(err) {
                        $log.error("Could not save application", err);
                    });
                };
                
                this.delete = function(uri) {
                    return s3Uploader.deleteS3Object(uri).then(function(response) {
                        return response;
                    }, function(err) {
                        $log.error("Could not delete file", err);    
                    });
                };
			}

			S3Upload.prototype = Object.create(BaseUpload.prototype);
			S3Upload.prototype.constructor = S3Upload;
			S3Upload.prototype.__superConstructor = BaseUpload.prototype.constructor;
			// ******** ******** ******** ********
			// ******** end Upload ********
			// ******** ******** ********

			return S3Upload;
		}
	]);
	
})(angular.module('ecs-upload'));
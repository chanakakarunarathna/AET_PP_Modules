(function(module) {

	module.service('s3Uploader', ['$http', '$q', '$window', '$rootScope', 's3Endpoint', '$log',
		function ($http, $q, $window, $rootScope, s3Endpoint, $log) {

			this.getS3URI = function(bucket) {
				return 'https://' + bucket + '.s3.amazonaws.com';
			}

			this.post = function (bucket, folder, acl, file) {

				var self = this;
				var deferred = $q.defer();

				var filename = file.name;
				var fileExactName  = filename.split('.')[0];
				fileExactName = fileExactName.replace(/\s/g, "_");
				var ext = filename.split('.').pop();
				folder = folder?folder:'';
				var key = folder + (new Date()).getTime() + '_' + fileExactName + "." + ext;
				key = key.replace(/\/\//g, '/');
				if(key[0] == '/')
					key = key.substr(1);

				return s3Endpoint.getPostParams(bucket, {acl:acl}).then(function(response) {

					var postParams = response.data;
					var uri = self.getS3URI(bucket);

					var fd = new FormData();
					fd.append('key', key);
					fd.append('acl', acl);
					fd.append('Content-Type', file.type);
					fd.append('AWSAccessKeyId', postParams.key);
					fd.append('policy', postParams.policy);
					fd.append('signature', postParams.signature);
					fd.append("file", file);

					var xhr = new XMLHttpRequest();
					xhr.upload.addEventListener("progress", uploadProgress, false);
					xhr.addEventListener("progress", uploadProgress, false);
					xhr.addEventListener("load", uploadComplete, false);
					xhr.addEventListener("error", uploadFailed, false);
					xhr.addEventListener("abort", uploadCanceled, false);

					function uploadProgress(e) {
						var msg = {type: 'progress', value: Math.round(e.loaded * 100 / e.total)};
						if (typeof deferred.notify === 'function') {
							deferred.notify(msg);
						}
					}
					function uploadComplete(e) {
						var xhr = e.srcElement || e.target;
						if(xhr.status === 204) { //success status
							deferred.resolve(xhr);
						}
						else {
							deferred.reject(xhr);
						}
					}
					function uploadFailed(e) {
						var xhr = e.srcElement || e.target;
						deferred.reject(xhr);
					}
					function uploadCanceled(e) {
						var xhr = e.srcElement || e.target;
						deferred.reject({
							type: 'cancelled',
							xhr: xhr
						});
					}

					xhr.open('POST', uri, true);
					xhr.send(fd);

					return {
						promise: deferred.promise,
						xhr:xhr
					};

				}, function(error) {
					$log.error("Could not load s3 POST params");
				});

			};

			this.getDownloadSignature = function(uri) {

                var bucket = uri.split('.')[0].slice(8);
				var key = uri.split('/').slice(3).join('/');

                return s3Endpoint.getObjectAuth(bucket, key).then(function(response) {

                	return response;

                }, function(err) {
                    console.error("Could not get signature");
                    return $q.reject(err);
                });
            };

            this.deleteS3Object = function(uri){

                var bucket = uri.split('.')[0].slice(8);
            	var key = "/" + uri.split('/').slice(3).join('/');

                return s3Endpoint.getDeleteParams(bucket, key).then(function(response) {

                	var postParams = response.data;

					var xhr = new XMLHttpRequest();

					xhr.open('DELETE', uri, true);
					xhr.setRequestHeader('Authorization', postParams.authHeader);
					xhr.setRequestHeader('x-amz-content-sha256', postParams.payloadHash);
					xhr.setRequestHeader('Host', "ecity-citizens.s3.amazonaws.com");
					xhr.setRequestHeader('x-amz-date', postParams.date);
					xhr.send();

                }, function(err) {
                    console.error("Could not Delete Object");
                    return $q.reject(err);
                });
            }

	}]);

})(angular.module('ecs-upload'));

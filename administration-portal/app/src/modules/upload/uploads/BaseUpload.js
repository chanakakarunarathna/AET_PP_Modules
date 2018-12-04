(function(module) {

  module.service('BaseUpload', ['$q', '$log',
    function($q, $log) {

      // ******** ******** ********
      // ******** BaseUpload ********
      // ******** ******** ******** ********
      function BaseUpload(id, options) {

        this.id = id;
        this.selectedFile;
        this.uploadDeferred;
        this.uploadPromise;
        this.uploading = false;
        this.complete = false;
        this.$validators = {};
        this.$errors = [];
        this.$invalid = false;
        this.$reset = false;
      }

      BaseUpload.prototype.constructor = BaseUpload;

      BaseUpload.prototype.destroy = function() {
        this.cancel();
      };

      BaseUpload.prototype.reset = function() {
        this.cancel();
        this.$reset = true;
      };

      BaseUpload.prototype.cancel = function() {
        var self = this;

        this.stop().then(function(result) {
          self.uploading = false;
          self.complete = false;
          self.uploadPromise = undefined;
          self.selectedFile = undefined;
        }, function(err) {
          $log.error("Could not stop uplaod.");
          $log.error("Error: " + err);
        });

      };

      BaseUpload.prototype.start = function() {
        var self = this;

        //define new promise
        this.uploadDeferred = $q.defer();
        this.uploadPromise = this.uploadDeferred.promise;

        //validate
        this.validateUpload(this).then(function(valid) {
          if (!valid) {
            self.$invalid = true;
            self.uploadDeferred.reject('Failed validation');
          } else {
            self.$invalid = false;
            self.uploading = true;
            // start uploading
            self.upload();
          }
        }, function(err) {

        });

        /*        if (!this.validate()) {
          this.$invalid = true;
          this.uploadDeferred.reject('Failed validation');
        } else {
          this.$invalid = false;
          this.uploading = true;
          // start uploading
          this.upload();
        }*/


        return this.uploadPromise;
      };

      BaseUpload.prototype.stop = function() {
        var deferred = $q.defer();
        deferred.reject("Abstract BaseUpload");
        return deferred.promise;
      };

      BaseUpload.prototype.upload = function() {
        this.uploadDeferred.reject("Abstract BaseUpload");
      };

      BaseUpload.prototype.validateUpload = function(context) {
        return $q(function(resolve, reject) {
          var self = context;
          var valid = true;

          self.$errors = [];

          angular.forEach(self.$validators, function(val, key) {
            var result = val.call(self);

            if (result !== true) {
              valid = false;
              self.$errors.push(result);
            }
          });

          var filesize;
          //Horrible code. Fix this.
          var isValidSize = true;
          if (angular.isDefined(self.s3Options.maxsize)) {
            filesize = ((self.selectedFile.size / 1024) / 1024).toFixed(1);
            if (Number(filesize) > Number(self.s3Options.maxsize)) {
              isValidSize = false;
            }
          }
          //Horrible code. Fix this.
          var isValidType = true;
          if (angular.isDefined(self.s3Options.fileTypes)) {
            isValidType = false;
            var ext = self.selectedFile.name.split('.').pop().toLowerCase();
            angular.forEach(self.s3Options.fileTypes, function(exType, key) {
              if (angular.equals(ext, exType)) {
                isValidType = true;
              }
            });
          }

          var isValidDimentions = true;
          if (angular.isDefined(self.s3Options.imageDimentions)) {
            var img = new Image;
            var array = self.s3Options.imageDimentions[0].split('x');
            var widthLimit = array[0];
            var heightLimit = array[1];
            img.onload = function() {
              if ((this.width <= widthLimit) && (this.height <= heightLimit)) {
                isValidDimentions = true;
              }
              if (valid && !isValidDimentions) {
                valid = false;
                if (angular.isDefined(self.s3Options.imageDimentions[1])) {
                  self.$errors.push(self.s3Options.imageDimentions[1]);
                } else {
                  self.$errors.push("Selected file needs to be " + 320 + " pixels wide or less or 120 pixels tall or less.");
                }
              }
              resolve(valid);
            }
            isValidDimentions = false;
            var url = URL.createObjectURL(self.selectedFile);
            img.src = url;
          }

          if (!isValidType) {
            valid = false;
            self.$errors.push("Unsupported file type.");
          } else if (!isValidSize) {
            valid = false;
            self.$errors.push("Selected file size is " + filesize + "mb. File size must be less than " + self.s3Options.maxsize + "mb");
          }

          if (!angular.isDefined(self.s3Options.imageDimentions)) {
            resolve(valid);
          }
        });
      }

      BaseUpload.prototype.setValidators = function(validators) {
          angular.extend(this.$validators, validators);
        }
        // ******** ******** ******** ********
        // ******** end BaseUpload ********
        // ******** ******** ********

      return BaseUpload;

    }
  ]);

})(angular.module('ecs-upload'));

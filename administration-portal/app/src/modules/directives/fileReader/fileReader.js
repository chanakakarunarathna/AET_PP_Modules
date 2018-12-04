(function(module) {

  module.directive('fileReader', ['$log', function($log) {

    return {
      scope: {
        fileReader: "="
      },
      link: function(scope, element) {
        $(element).on('change', function(changeEvent) {
          var files = changeEvent.target.files;
          var hasFeedback = $('.csv');

          var validExts = new Array(".csv");
          var fileExt = files[0].name;
          fileExt = fileExt.substring(fileExt.lastIndexOf('.'));
          if (validExts.indexOf(fileExt) < 0) {
            element.next('.error').removeClass('hide');
            hasFeedback.addClass('has-error');
          } else {
            hasFeedback.removeClass('has-error');
            element.next('.error').addClass('hide');
            if (files.length) {
              var r = new FileReader();
              r.onload = function(e) {
                var contents = e.target.result;
                scope.$apply(function() {
                  scope.fileReader = contents;
                });
              };
              r.readAsText(files[0]);
            }
          }
        });
      }
    };
  }]);

})(angular.module('aet.directives.alerts'));

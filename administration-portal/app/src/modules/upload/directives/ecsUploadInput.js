(function(module) {

  var directive = [
    function() {

      return {
        scope: true,
        templateUrl: 'src/modules/upload/templates/ecsUploadInput.html',
        require: ['?^ecsUploadButton'],

        link: function postLink(scope, element, attrs, controllers) {

          var ecsUploadButton = controllers[0];

          scope.name = attrs.name;

          scope.fileInputElement = element.find("input");

          //to resctrict file types
          scope.fileInputElement[0].accept = attrs.accept;

          if (ecsUploadButton)
            ecsUploadButton.registerInput(scope.fileInputElement);

        }
      };
    }
  ];

  module.directive('ecsUploadInput', directive);

})(angular.module('ecs-upload'));

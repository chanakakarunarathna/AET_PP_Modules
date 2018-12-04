(function(module) {
  module.config(function($provide) {
    $provide.decorator('taOptions', ['taRegisterTool', '$delegate', function(taRegisterTool, taOptions) {
      taRegisterTool('fontSize', {
        display: '<span class="btn-custom-group" dropdown style="padding: 0px 0px 0px 0px; text-transform: inherit;">' +
          '<button class="btn btn-default dropdown-toggle" dropdown-toggle style="border: 0px solid transparent;" type="button" ng-disabled="showHtml()">' +
          '   <span>Font size</span>' +
          '</button>' +
          '<ul class="dropdown-menu" style="margin-top: 0px;">' +
          '   <li ng-repeat="o in options">' +
          '       <a ng-click="action(o)">{{o.name}}</a>' +
          '   </li>' +
          '</ul>' +
          '</span>',
        options: [{
          name: 'Smallest'
        }, {
          name: 'Small'
        }, {
          name: 'Medium'
        }, {
          name: 'Medium-Large'
        }, {
          name: 'Large'
        }, {
          name: 'Extra-Large'
        }],
        action: function(option) {
          if (angular.equals(option.name, 'Smallest')) {
            this.$editor().wrapSelection("formatBlock","<H6>");
          }
          if (angular.equals(option.name, 'Small')) {
            this.$editor().wrapSelection("formatBlock","<H5>");
          }
          if (angular.equals(option.name, 'Medium')) {
            this.$editor().wrapSelection("formatBlock","<H4>");
          }
          if (angular.equals(option.name, 'Medium-Large')) {
            this.$editor().wrapSelection("formatBlock","<H3>");
          }
          if (angular.equals(option.name, 'Large')) {
            this.$editor().wrapSelection("formatBlock","<H2>");
          }
          if (angular.equals(option.name, 'Extra-Large')) {
            this.$editor().wrapSelection("formatBlock","<H1>");
          }
        },
        activeState: function(option) {
          return this.$editor().queryFormatBlockState('fontSize');
        }
      });
      taOptions.toolbar[1].push('fontSize');
      return taOptions;
    }]);
  });
})(angular.module('aet.editor'));

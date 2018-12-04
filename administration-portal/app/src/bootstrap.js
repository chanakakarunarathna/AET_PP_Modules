(function () {

  /**
   * @ngdoc overview
   * @name aet.bootstrap
   *
   * @description
   * This module is responsible for 'bootstrapping' or initializing the angular application.
   * Any modules used in the application must be included as a dependency here.
   *
   */

  angular.element(document).ready(function () {
    angular.bootstrap(document, [
      'aet.bootstrap'
    ]);
  });

})(angular.module('aet.bootstrap', [
  // angular modules
  'ui.router',
  'ui.scrollfix',
  'ui.mask',
  'ngCookies',
  'LocalStorageModule',
  'ui.bootstrap',
  'ngAnimate',
  'color.picker',
  'textAngular',
  'ui.select',
  'checklist-model',
  'ui.sortable',
  'angularAwesomeSlider',
  'chart.js',
  'aet.screenshot',
  'aet.browser',
  'nvd3',
  'highcharts-ng',
  'com.2fdevs.videogular',
  'com.2fdevs.videogular.plugins.controls',
  'com.2fdevs.videogular.plugins.buffering',
  'cp.ng.fix-image-orientation',
  'dynamicNumber',
  'ngclipboard',
  'perfect_scrollbar',
  'pageslide-directive',
  'angularGrid',
  'ui.slider',

  // application modules
  'aet.config',
  'aet.security',
  'aet.domain',
  'aet.endpoints',
  'aet.alerts',
  'aet.forms',
  'aet.debounce',
  'aet.modals',
  'ecs-upload',
  'aet.editor',
  'aet.navigation',
  'aet.pingImage',

  // directives
  'aet.directives.navTab',
  'aet.directives.match',
  'aet.search',
  'aet.directives.alerts',
  'aet.directives.modalWindow',
  'aet-directives-hierarchyMap',
  'aet-directives-image',
  'aet-directives-uiselect',
  'aet.directives.fileReader',
  'aet-directives-staticinclude',
  'aet-directives-ui-custom-width',
  'aet.directives.limitCharacters',
  'aet.directives.render-array',
  'aet-directives-selectonclick',
  'aet-directives-clickoutside',
  'aet-directives-scrollposition',
  'aet.directives.pp-button-input',
  'aet.directives.cmsDataRs',

  // screens
  'aet.screens.index',
  'aet.screens.secured',
  'aet.screens.login',
  'aet.screens.masterfield',
  'aet.screens.dashboard',
  'aet.screens.sidebars',
  'aet.screens.formBuilder',
  'aet.screens.adminUser',
  'aet.screens.partner',
  'aet.screens.places',
  'aet.screens.role',
  'aet.screens.unauthorized',
  'aet.screens.manageaccount',
  'aet.screens.partnerPlace',
  'aet.screens.partnerConfig',
  'aet.screens.partnerProperty',
  'aet.screens.booking',
  'aet.screens.experiencecurve',
  'aet.screens.wordcloud',
  'aet.screens.comment',
  'aet.screens.polls',
  'aet.screens.superAdmin',

  //custom filters
  'aet.customfilters.missionchooseaction',
  'aet.customfilters.text',
  'aet.customfilters.participationreport',
  'aet.customfilters.orderbyobject',
  'aet.customfilters.trustUrl',
]));

(function(module) {

  module.config(['$stateProvider',
    function($stateProvider) {

      $stateProvider
        .securedState('index.secured.dashboard', {
          url: '/dashboard',
          views: {
            //						'sidebar@': {
            //							controller: 'AdminSidebarController',
            //							templateUrl: 'src/screens/sidebars/templates/admin.html',
            //						},
            'main@index': {
              controller: 'DashboardController',
              templateUrl: 'src/screens/dashboard/templates/dashboard.html',
            }
          },
          data: {
            selectedTab: "Dashboard",
            screenName: "Dashboard"
          }
        });


    }
  ]);

})(angular.module('aet.screens.dashboard'));

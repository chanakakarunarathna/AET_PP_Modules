(function(module) {

  module.config(['$stateProvider',
    function($stateProvider) {

      $stateProvider
        .securedState('index.secured.partner', {
          abstract: true,
          url: '/partner',
          data: {
            selectedTab: "Partners",
            screenName: "Partners"
          }
        })
        .securedState('index.secured.partner.search', {
          url: '',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partner/templates/search.html',
              controller: 'SearchPartnerController',
            }
          },
          resolve: {
            searchManager: ['SearchManager', 'partnerService', '$log', function(SearchManager, partnerService, $log) {
              var searchManager = new SearchManager(partnerService.searchPartners);
              return searchManager.search();
            }]
          },
          data: {
            screenName: "Search Partners",
            feature: "super"
          }

        })
        .securedState('index.secured.partner.create', {
          url: '/create',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partner/templates/create.html',
              controller: 'CreatePartnerController',
            }
          },
          data: {
            screenName: "Create Partner",
            feature: "super"
          }
        })
        .securedState('index.secured.partner.edit', {
          url: '/edit/:partnerId',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partner/templates/edit.html',
              controller: 'EditPartnerController',
            }
          },
          data: {
            screenName: "Edit Partner",
            feature: "super"
          },
          resolve: {
            partner: ['$stateParams', 'partnerService',
              function($stateParams, partnerService) {
                return partnerService.getPartner($stateParams.partnerId);
              }
            ]
          }
        });


    }
  ]);

})(angular.module('aet.screens.partner'));

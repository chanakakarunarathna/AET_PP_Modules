(function(module) {

  module.config(['$stateProvider',
    function($stateProvider) {

      $stateProvider
        .securedState('index.secured.partnerProperty', {
          abstract: true,
          url: '/partnerProperty',
          data: {
            selectedTab: "PartnerProperty",
            screenName: "PartnerProperty"
          }
        })
        .securedState('index.secured.partnerProperty.search', {
          url: '',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partnerProperty/templates/search.html',
              controller: 'SearchPartnerPropertyController',
            }
          },
          resolve: {
            searchManager: ['SearchManager', 'partnerPropertyService', '$log','updatedUser', function(SearchManager, partnerPropertyService, updatedUser) {
              var searchManager = new SearchManager(partnerPropertyService.searchPartnerProperties);
              return searchManager.search();
            }]
          },
          data: {
            screenName: "Search Partner Property",
            feature: "LIST_PARTNER_PROPERTIES"
          }

        })
        .securedState('index.secured.partnerProperty.create', {
          url: '/create',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partnerProperty/templates/create.html',
              controller: 'CreatePartnerPropertyController',
            }
          },
          data: {
            screenName: "Create Partner Property",
            feature: "ADD_PARTNER_PROPERTY"
          }
        })
        .securedState('index.secured.partnerProperty.edit', {
          url: '/edit/:partnerPropertyId',
          views: {
            'main@index': {
              templateUrl: 'src/screens/partnerProperty/templates/edit.html',
              controller: 'EditPartnerPropertyController',
            }
          },
          data: {
            screenName: "Edit Partner Property",
            feature: "EDIT_PARTNER_PROPERTY"
          },
          resolve: {
            partnerProperty: ['$stateParams', 'partnerPropertyService', 'updatedUser',
              function($stateParams, partnerPropertyService, updatedUser) {
                return partnerPropertyService.getPartnerProperty($stateParams.partnerPropertyId);
              }
            ]
          }
        });


    }
  ]);

})(angular.module('aet.screens.partnerProperty'));

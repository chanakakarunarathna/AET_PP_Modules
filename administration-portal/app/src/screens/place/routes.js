(function(module) {

  module.config(['$stateProvider',
    function($stateProvider) {

      $stateProvider
        .securedState('index.secured.places', {
          abstract: true,
          url: '/places',
          data: {
            selectedTab: "Places",
            screenName: "Places"
          }
        })
        .securedState('index.secured.places.search', {
          url: '',
          views: {
            'main@index': {
              templateUrl: 'src/screens/place/templates/search.html',
              controller: 'SearchPlacesController',
            }
          },
          resolve: {
            searchManager: ['SearchManager', 'placesService', '$log', function(SearchManager, placesService, $log) {
              var searchManager = new SearchManager(placesService.searchPlacess);
              return searchManager.search();
            }]
          },
          data: {
            screenName: "Search Places",
            feature: "LIST_PLACES"
          }

        })
        .securedState('index.secured.places.create', {
          url: '/create',
          views: {
            'main@index': {
              templateUrl: 'src/screens/place/templates/create.html',
              controller: 'CreatePlacesController',
            }
          },
          data: {
            screenName: "Create Place",
            feature: "ADD_PLACE"
          }
        })
        .securedState('index.secured.places.edit', {
          url: '/edit/:placesId',
          views: {
            'main@index': {
              templateUrl: 'src/screens/place/templates/edit.html',
              controller: 'EditPlacesController',
            }
          },
          data: {
            screenName: "Edit Place",
            feature: "EDIT_PLACE"
          },
          resolve: {
            places: ['$stateParams', 'placesService',
              function($stateParams, placesService) {
                return placesService.getPlaces($stateParams.placesId);
              }
            ]
          }
        });


    }
  ]);

})(angular.module('aet.screens.places'));

(function(module) {

  module.service('placesTransformer', ['genericTransformer', 'CreatePlacesRequestDTO', 'Places', 'UpdatePlacesRequestDTO',
    function(genericTransformer, CreatePlacesRequestDTO, Places, UpdatePlacesRequestDTO) {

      this.placesToCreateDTO = function(places) {
        var placesObj=genericTransformer.objectToDTO(places, CreatePlacesRequestDTO);
//        var addrsComponents = [];
//        var addressTypes = [];

        /*addressTypes.push(placesObj.addrsComponents.type);
        delete placesObj.addrsComponents.type;
        placesObj.addrsComponents.types = addressTypes;

        addrsComponents.push(placesObj.addrsComponents);
        placesObj.addrsComponents = addrsComponents;*/

        return placesObj;
      };

      this.getDTOToPlaces = function(dto) {
        //convert sub level number to string
        //dto.subLevel=dto.subLevel.toString();
        var places = genericTransformer.DTOToObject(dto, Places);
        places.aliasList = dto.aliases;
        places.partnerPlacesFound = dto.partnerPlacesFound;
        //convert createdDate to Date object
        //var createdDate = new Date(dto.createdDate);
        //places.createdDate = createdDate.getMonth() + 1 + "/" + createdDate.getDate() + "/" + createdDate.getFullYear();
        return places;
      };

      this.placesToUpdateDTO = function(places) {
        var placesObj= genericTransformer.objectToDTO(places, UpdatePlacesRequestDTO);
        return placesObj;
      };

      this.searchDTOToSearchResults = function(dto) {
        var self = this;

        var searchResults = genericTransformer.DTOToSearchResultsPage(dto.data);
        searchResults.results = _.map(dto.data.placeList, function(places) {
          return self.getDTOToPlaces(places);
        });

        return searchResults;
      };
      
      this.placeParamConvert = function(params) {
          if(params != undefined  && params.sortby != undefined){
            if(params.sortby == 'formattedAddress'){
              params.sortby = 'name'
            }
          }
          return params;
        };

    }
  ]);

})(angular.module('aet.domain.places'));

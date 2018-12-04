(function(module) {

  module.service('productTransformer', ['Product', 'genericTransformer', '$log','SearchResults',
    function(Product, genericTransformer, $log, SearchResults) {

      this.searchDTOToSearchResults = function(dto) {  
        var searchResults = genericTransformer.DTOToSearchResults(dto.data);
        searchResults.results = dto.data.productList;
        return searchResults;	
      };
    }
  ]);

})(angular.module('aet.domain.product'));

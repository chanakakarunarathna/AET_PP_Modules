(function(module) {
    
	/**
	 * @ngdoc object
	 * @name aet.search.object:SearchResults
	 *
	 * @description
	 * An object that contains search results and pagination data.
	 *
	 * @property {Array} results An array of search results.
	 * @property {Object} pagination Pagination data. Contains `count`, `currentPage`, `total`, and `totalPages`.
	 *
	 */
	
    module.service('SearchResults', [function() {

        function SearchResults() {

			this.results = [];
			this.pagination = {
				count:undefined,
				currentPage: undefined,
				total: undefined,
				totalPages: undefined
			}

        }

        return SearchResults;

    }]);

})(angular.module('aet.search'));


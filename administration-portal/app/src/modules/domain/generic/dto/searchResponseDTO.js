(function(module) {
    
    module.service('SearchResponseDTO', [function() {

        function SearchResponseDTO() {

			this.results = [];
			this.page = undefined;
        	this.count = undefined;
        	this.sortby = undefined;
        	this.sortdirection = undefined;

        }

        return SearchResponseDTO;

    }]);

})(angular.module('aet.domain.generic'));


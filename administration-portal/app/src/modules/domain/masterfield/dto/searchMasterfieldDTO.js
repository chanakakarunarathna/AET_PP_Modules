(function(module) {
    
    module.service('SearchMasterfieldRequestDTO', [function() {

        function SearchMasterfieldRequestDTO() {
        	
        	this.q = undefined;
        	this.page = undefined;
        	this.count = undefined;
        	this.sortby = undefined;
        	this.sortdirection = undefined;
        	
        }

        return SearchMasterfieldRequestDTO;

    }]);

})(angular.module('aet.domain.masterfield'));


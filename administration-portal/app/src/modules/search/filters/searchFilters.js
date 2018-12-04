(function(module) {
    /**
	 * @ngdoc filter
	 * @name aet.search.filter:searchFilter
	 *
	 * @description
	 * A filter used by the {@link aet.search.directive:aetSearch aetSearch} directive to filter results
	 * by the columns flagged as searchable in the {@link aet.search.directive:aetSearchColumn aetSearchColumn} directive.
	 *
	 *
	 */
	module.filter('searchFilter', ['$filter', function($filter) {
		
		return function(array, query, fields) {
			
			return _.filter(array, function(object) {
				return _.any(fields, function(field) {
					var fieldName = angular.isObject(field) ? field.name : field;
					if(object.hasOwnProperty(fieldName) && angular.isString(object[fieldName])){
						var re = new RegExp(query, "i");
						return object.hasOwnProperty(fieldName) && angular.isString(object[fieldName]) && object[fieldName].match(re);
					}else if(object.hasOwnProperty(fieldName) && angular.isNumber(object[fieldName])){
						return object.hasOwnProperty(fieldName) && object[fieldName].toString().includes(query);
					}else if(object.hasOwnProperty(fieldName) && angular.isArray(object[fieldName])){
						var re = new RegExp(query, "i");
						var found = false;
						for (var i = 0; i < object[fieldName].length; i++) {
							if(object.hasOwnProperty(fieldName) && object[fieldName][i].match(re)){
								found=true;
							}
						}
						if(found){
							return object.hasOwnProperty(fieldName) && angular.isArray(object[fieldName]);
						}
					}
				});
			});
		}
	}]);
	
})(angular.module('aet.search'));
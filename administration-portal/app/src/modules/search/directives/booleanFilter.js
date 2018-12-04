(function(module) {
    
    /**
	 * @ngdoc directive
	 * @name aet.search.directive:aetBooleanSearchFilter
	 *
	 * @restrict E
	 *
	 * @description
	 * Adds a boolean filter to the search filters
	 *
	 */
    module.directive('aetBooleanSearchFilter', ['$log', function ($log) {
		return {
			templateUrl: 'src/modules/search/templates/booleanFilter.html',
			replace: true,
			require: '^aetSearchFilters',
            scope: true,
			link: function(scope, element, attrs, aetSearchFilters) {
                
                scope.filter = {
                    name: attrs.name,
                    label: attrs.label,
                    value: undefined
                };
                
                scope.statuses = [
                    {label: 'Off', value: undefined},
                    {label: 'True', value: true},
                    {label: 'False', value: false}
                ];
                scope.status = scope.statuses[0];
                
                scope.toggleStatus = function() {
                    scope.status = scope.statuses[((scope.statuses.indexOf(scope.status)) + 1) % 3];
                    scope.filter.value = scope.status.value;
                    aetSearchFilters.setChanged(true);
                };
                
                aetSearchFilters.registerFilter(scope.filter);
                
			}
		}
	}]);
    
})(angular.module('aet.search'));
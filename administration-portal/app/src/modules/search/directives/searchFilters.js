(function(module) {
    
	/**
	 * @ngdoc directive
	 * @name aet.search.directive:aetSearchFilters
	 *
	 * @restrict E
	 *
	 * @description
	 * Creates an dropdown button that can be used to filter by certain criteria
	 * This directive should be nested within an {@link aet.search.directive:aetSearch aetSearch}. See those docs for an example.
	 *
	 * @param {SearchManager} searchManager The searchManager that results will be retrieved from.
	 *
	 */
	
	module.directive('aetSearchFilters', ['$log', function ($log) {
		return {
            transclude: true,
			templateUrl: 'src/modules/search/templates/searchFilters.html',
			replace: true,
			scope: {
				searchManager: '='
			},
			link: function(scope, element, attrs) {
                scope.changed = false;
                
                scope.toggled = function(open) {
                    if(open === false && scope.changed) {
                        scope.searchManager.search();
                    }
                    scope.changed = false;
                };
			},
            controller: function($scope, $element, $attrs) {
                this.registerFilter = function(filter) {
                    $scope.searchManager.addFilter(filter);
                };
                
                this.setChanged = function(val) {
                    $scope.changed = val;
                }
            }
		}
	}]);
    
})(angular.module('aet.search'));
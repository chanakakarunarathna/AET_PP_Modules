(function(module) {
    
	/**
	 * @ngdoc directive
	 * @name aet.search.directive:aetSearchInput
	 *
	 * @restrict E
	 *
	 * @description
	 * Creates an input field that is used to search and filter
	 * This directive should be nested within an {@link aet.search.directive:aetSearch aetSearch}. See those docs for an example.
	 *
	 * @param {SearchManager} searchManager The searchManager that results will be retrieved from.
	 *
	 */
	
	module.directive('aetSearchInput', ['$log', function ($log) {
		return {
			templateUrl: 'src/modules/search/templates/searchInput.html',
			replace: true,
			scope: {
				searchManager: '='
			},
			link: function(scope, element, attrs) {
				
				scope.class = attrs.class || 'form-control input-lg'
				
				scope.onKeyPress = function(keyEvent) {
					if (keyEvent.which === 13)
						scope.searchManager.search();
				};
			}
		}
	}]);
    
})(angular.module('aet.search'));
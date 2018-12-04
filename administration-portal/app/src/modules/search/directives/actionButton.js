(function(module) {

	/**
	 * @ngdoc directive
	 * @name aet.search.directive:aetActionButton
	 *
	 * @restrict A
	 *
	 * @description
	 * Creates a button that appears in the 'Actions' column of the search results table.
	 * This directive should be nested within an {@link aet.search.directive:aetSearch aetSearch}. See those docs for an example.
	 *
	 * @param {expression} ngClick The expression to evaluate upon click. Will most likely pass the `row`
	 * as an argument.
	 *
	 */

	module.directive('aetActionButton', ['$log', function ($log) {
		return {
			restrict: 'A',
			require: '^aetSearch',
			transclude: 'element',
			scope: {
				ngClick: '&'
			},
			compile: function(element, attrs) {

				return function(scope, element, attrs, aetSearch, transclude) {

					var rowName = aetSearch.rowName;

					scope.actionFn = function(param) {
						var params = {};
						params[rowName] = param;
						scope.ngClick(params);
					};


					aetSearch.addActionButton({
						transcludeFn: transclude,
						scope: scope,
						actionFn: function(param) {
							var params = {};
							params[rowName] = param;
							scope.ngClick(params);
						}
					});

				};

			}
		};
	}]);

	module.directive('aetActionButtonTransclude', ['$log', '$compile', function ($log, $compile) {
		return {
			require: '^aetSearch',
			scope: {
				row: '='
			},
			link: function(scope, element, attrs, ecsSearch) {

				var rowName = ecsSearch.rowName;

				_.each(ecsSearch.buttons, function(button) {
					var newScope = button.scope.$new();
					newScope[rowName] = scope.row;
					button.transcludeFn(newScope, function(contents) {
						contents.attr('ng-click', 'actionFn(' + rowName + ')');
						contents.removeAttr('aet-action-button');
						var compiled = $compile(contents)(newScope);
						element.append(compiled);
					});
				});

			}
		};
	}]);

})(angular.module('aet.search'));

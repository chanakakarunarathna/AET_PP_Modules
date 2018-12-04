(function(module) {

	/**
	 * @ngdoc directive
	 * @name aet.search.directive:aetSearchColumn
	 *
	 * @restrict E
	 *
	 * @description
	 * Creates a column for the search results table
	 * This directive should be nested within an {@link aet.search.directive:aetSearch aetSearch}. See those docs for an example.
	 *
	 * @param {String} name The name of the field on the object in your {@link aet.search.object:SearchResults SearchResults}.
	 * @param {String} label The name that appears in the table head for this column.
	 * @param {Boolean} searchable Defines whether this column is searchable. If set to true, the search input will filter
   * @param {Boolean} sortable Defines whether this column is sortable. If set to true, you can click the column to toggle sorting
	 * the results by this column.
	 *
	 */

	module.directive('aetSearchColumn', ['$log', function ($log) {
		return {
			require: '^aetSearch',
			transclude: true,
			scope: true,
			link: function(scope, element, attrs, aetSearch, transcludeFn) {
				
                var column = {
                    name: attrs.name,
                    label: attrs.label,
                    type : attrs.type,
                    filterColumn : attrs.filterColumn,
                    checkingField1 : attrs.checkingField1,
                    checkingField2 : attrs.checkingField2,
                    permission: attrs.permission,
                    searchable: scope.$eval(attrs.searchable),
                    sortable: scope.$eval(attrs.sort),
                    urlParam: attrs.urlParam,
                    scope: scope,
                    transcludeFn: transcludeFn,
                    title: attrs.title
                };

                aetSearch.addColumn(column);

			}
		}
	}]);
    
    module.directive('aetSearchColumnTransclude', ['$log', '$compile', function ($log, $compile) {
		return {
			require: '^aetSearch',
			scope: {
				row: '=',
                column: '='
			},
			link: function(scope, element, attrs, ecsSearch) {
				
				var rowName = ecsSearch.rowName;
				
                var newScope = scope.column.scope.$new();
                newScope[rowName] = scope.row;
                
                scope.column.transcludeFn(newScope, function(contents) {
                    if(contents.length < 1) {
                        contents = angular.element("<span>{{" + rowName + "." + scope.column.name + "}}</span>")
                    }
                    else {
                        contents.removeAttr('ng-click');
                        contents.children().removeAttr('ng-click');
                        var html = contents.html();
                        contents.remove();
                        contents.html(html);
                    }
                    var compiled = $compile(contents)(newScope);
                    element.append(compiled);
                });
				
			}
		}
	}]);

})(angular.module('aet.search'));

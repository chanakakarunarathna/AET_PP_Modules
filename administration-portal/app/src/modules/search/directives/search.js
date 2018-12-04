(function(module) {

	/**
	 * @ngdoc directive
	 * @name aet.search.directive:aetSearch
	 *
	 * @restrict E
	 *
	 * @description
	 * Creates an HTML table listing {@link aet.search.object:SearchResults SearchResults} from a
	 * {@link aet.search.service:SearchManager SearchManager}. Columns are configured by nesting
	 * {@link aet.search.directive:aetSearchColumn SearchColumns} inside this directive.
	 *
	 * @param {SearchManager} searchManager The searchManager that results will be retrieved from
	 * @param {String} rowName A reference to the object used to populate each row. Useful for
	 * passing this object to {@link aet.search.directive:aetActionButton aetActionButtons}.
	 * Default is `'row'`.
	 *
	 * @example
	<aet-search search-manager="searchManager" rowName="admin">

		<aet-search-column name="firstName" label="FirstName" searchable="true"></aet-search-column>
		<aet-search-column name="lastName" label="LastName" searchable="true"></aet-search-column>
		<aet-search-column name="email" label="Email" searchable="true"></aet-search-column>
		<aet-search-column name="isSuperAdmin" label="Super Admin" searchable="false"></aet-search-column>
		<aet-search-column name="isVerified" label="Verified" searchable="false"></aet-search-column>

		<button aet-action-button
				class="btn btn-sm btn-default"
				title="edit"
				type="submit"
				ng-click="editAdmin(admin)">
			<span class="glyphicon glyphicon-edit"></span>
		</button>

		<button aet-action-button
				class="btn btn-sm btn-danger"
				title="delete"
				type="submit"
				ng-click="deleteAdmin(admin)">
			<span class="glyphicon glyphicon-trash"></span>
		</button>

	</aet-search>
	 */

	module.directive('aetSearch', ['$log', function ($log) {
		return {
			transclude: true,
			templateUrl: 'src/modules/search/templates/search.html',
			scope: {
				searchManager: '='
			},
			link: function(scope, element, attrs) {
				scope.searchManager.searchableFields = _.map(scope.columns, function(column) {
					if(column.searchable)
						return {
							name: column.name,
							label: column.label,
							urlParam: column.urlParam
						}
				});

        scope.searchManager.sortableFields = _.filter(scope.columns, function(column) {
					return column.sortable;
				});

        var sortStates = [undefined,'ASC','DESC'];
        scope.toggleSort = function(column) {
            // disable all other sorts
            _.each(scope.searchManager.sortableFields, function(field) {
                if(column.name != field.name) {
                    field.sortState = undefined;
                }
            });
            var currentIndex = sortStates.indexOf(column.sortState);
            column.sortState = sortStates[(currentIndex + 1) % 3];

            scope.searchManager.search();
        };
			},
			controller: function($scope, $element, $attrs) {

				var ctrl = this;
				var columns = ctrl.columns = $scope.columns = [];
				var buttons = ctrl.buttons = $scope.buttons = [];

				ctrl.rowName = $attrs.rowName || 'row';

				ctrl.addColumn = function(column) {
					columns.push(column);
				};

				ctrl.addActionButton = function(button) {
					buttons.push(button);
				};

				ctrl.removeScreen = function(column) {
					var index = columns.indexOf(column);
					columns.splice(index, 1);
				};

				ctrl.getResults = function() {
					return $scope.results;
				};

				ctrl.getQuery = function() {
					return $scope.query;
				};

				var destroyed;
				$scope.$on('$destroy', function() {
					destroyed = true;
				});
			}
		}
	}]);

})(angular.module('aet.search'));

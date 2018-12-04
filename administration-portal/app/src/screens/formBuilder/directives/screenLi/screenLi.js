(function(module) {
    
	module.directive('screenLi', [function () {
		return {
			transclude: true,
			scope: {
				screen: '=',
				selectedScreen: '=',
				screens: '=',
				index: '='
			},
			replace: true,
			templateUrl: 'src/screens/formBuilder/directives/screenLi/screenLi.html',
			link: function(scope, element, attrs) {
				
				scope.editMode = false;
				scope.dragging = false;
				
				scope.setSelected = function() {
					scope.selectedScreen = scope.screen.name;
				};
				
				scope.dragStart = function(event) {
					angular.element(event.target).bind('dragend', scope.dragEnd);
					scope.dragging = true;
				}
													   
				scope.dragEnd = function(event) {
					scope.$apply(function() {
						scope.dragging = false;
					});
				}									   
			}
		}
	}]);
	
})(angular.module('aet.screens.formBuilder'));
(function(module) {
    
    module.config(['$provide', function ($provide) {

        $provide.decorator('ngModelDirective', ['$delegate', function($delegate) {
			
			var ngModel = $delegate[0];
			var compile = ngModel.compile;
			
			ngModel.compile = function() {
				var link = compile.apply(this, arguments);
				
				// postLink function
				return {
					pre: function(scope, element, attrs, ctrls) {
						link.pre.apply(this, arguments);
					},
					post: function(scope, element, attrs, ctrls) {
						var ngModelController = ctrls[0];

						scope.ngModelController = ngModelController;
						
						var showSuccessAndError = _.debounce(function() {
							scope.$apply(function() {
								ngModelController.$validate();
								ngModelController.$showError = true;
							})
						}, 3000);

						var cleanCherecter = function(){

						};
						
						ngModelController.$showError = false;
						ngModelController.$hasError = function() {
							//console.log(_.keys(ngModelController.$error));
							return _.keys(ngModelController.$error).length > 0;
						};
						
						element.on('keypress', function(event) {
							scope.$apply(function() {
								ngModelController.$commitViewValue();
								ngModelController.$validate();
								showSuccessAndError();
								ngModelController.$showError = false;
							});
						});
						
						element.on('blur', function(event) {
							scope.$apply(function() {

								var replaceWordChars = function(text) {
									if(text && typeof text === "string"){
										var s = text;
										// smart single quotes and apostrophe
										s = s.replace(/[\u2018\u2019\u201A]/g, "\'");
										// smart double quotes
										s = s.replace(/[\u201C\u201D\u201E]/g, "\"");
										// ellipsis
										s = s.replace(/\u2026/g, "...");
										// dashes
										s = s.replace(/[\u2013\u2014]/g, "-");
										// circumflex
										s = s.replace(/\u02C6/g, "^");
										// open angle bracket
										s = s.replace(/\u2039/g, "<");
										// close angle bracket
										s = s.replace(/\u203A/g, ">");
										// spaces
										s = s.replace(/[\u02DC\u00A0]/g, " ");
										return s;
									}else{
										return text;
									}
								};

								ngModelController.$showError = true;
								ngModelController.$showSuccess = true;

								var val = replaceWordChars(ngModelController.$modelValue);

//								console.log(ngModelController.$viewValue , ngModelController.$modelValue);
								if (val !== ngModelController.$modelValue) {
									ngModelController.$setViewValue(val);
									ngModelController.$render();
								}
							});
						});
						
						link.post.apply(this, arguments);
					}
				}
				
			};
			
			return $delegate;
			
		}])

    }]);
    
})(angular.module('aet.forms'));
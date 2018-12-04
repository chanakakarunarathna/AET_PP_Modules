(function(module) {
    
    module.provider('customState', ['$stateProvider', 'securityProvider', 
        function($stateProvider, securityProvider) {
            
            var searchResults = ['$state', function($state) {
            }];
			
			var stateFn = $stateProvider.state;
			
			$stateProvider.state = function(path, route) {
				route.resolve = route.resolve || {};
				
				angular.extend(route.resolve, {
					previousState: ["$state",
						function ($state) {
							var currentStateData = {
								name: $state.current.name,
								params: $state.params,
								URL: $state.href($state.current.name, $state.params),
								data: $state.current.data
							};
							return currentStateData;
						}
					]
				});
				
				return stateFn.apply(this, arguments);
			};

            $stateProvider.securedState = function(path, route) {
                route.resolve = route.resolve || {};

                angular.extend(route.resolve, { 
                    isLoggedIn: securityProvider.isLoggedIn, 
                    isAuthorized: securityProvider.isAuthorized 
                });
				
                return $stateProvider.state(path, route);
            };

            return $stateProvider;
        }
    ]);
    
})(angular.module('aet.config'));
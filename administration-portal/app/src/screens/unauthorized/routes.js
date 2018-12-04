(function(module) {
    
    module.config(['$stateProvider',
        function ($stateProvider) {

            $stateProvider
                .securedState('index.secured.unauthorized', {
                    url: '/unauthorized',
					views: {
						'main@index': {
							templateUrl: 'src/screens/unauthorized/templates/unauthorized.html',
							controller: 'UnauthorizedController',
						}
					},
					data: {
						screenName: "Unauthorized",
						selectedTab: "",
					}
                })
            
        }
    ]);
    
})(angular.module('aet.screens.unauthorized'));
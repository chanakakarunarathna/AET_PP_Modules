(function(module) {
    
    module.config(['$stateProvider',
        function ($stateProvider) {

            $stateProvider
                .securedState('index.secured.experiencecurve', {
                    url: '/experiencecurve',
					views: {
						'main@index': {
							templateUrl: 'src/screens/experiencecurve/templates/experiencecurve.html',
							controller: 'ExperienceCurveController',
						}
					},
					data: {
						screenName: "Exprience Curve",
						selectedTab: "Reports",
					}
                })
            
        }
    ]);
    
})(angular.module('aet.screens.experiencecurve'));
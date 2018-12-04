(function(module) {
    
    module.config(['$stateProvider',
        function ($stateProvider) {

            $stateProvider
                .securedState('index.secured.wordcloud', {
                    url: '/wordcloud',
					views: {
						'main@index': {
							templateUrl: 'src/screens/wordcloud/templates/wordcloud.html',
							controller: 'WordCloudController',
						}
					},
					data: {
						screenName: "Word Cloud",
						selectedTab: "Reports",
					}
                })
            
        }
    ]);
    
})(angular.module('aet.screens.wordcloud'));
(function(module) {
    
    module.config(['$stateProvider',
        function ($stateProvider) {

            $stateProvider
                .securedState('index.secured.comment', {
                    url: '/comment',
					views: {
						'main@index': {
							templateUrl: 'src/screens/comment/templates/comment.html',
							controller: 'CommentController',
						}
					},
					data: {
						screenName: "Comments",
						selectedTab: "Reports",
					}
                })
            
        }
    ]);
    
})(angular.module('aet.screens.comment'));
(function(module) {
    
    module.config(['$stateProvider',
    	function ($stateProvider) {

        	$stateProvider
				.state('index', {
						abstract: true,
						views: {
						   'index': {
							   templateUrl: 'src/screens/index/templates/index.html',
						   }
						}
				   });
           
        }
   ]);
    
})(angular.module('aet.screens.index'));
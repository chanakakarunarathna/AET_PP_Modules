(function(module) {
    
    module.config(['$httpProvider',
                
        function($httpProvider) {
            
            $httpProvider.interceptors.push(['$injector', '$q',
                 function ($injector, $q) {
                     
                     var alertsService = $injector.get('alertsService');
                     
                     return {
                         
                        'responseError': function(rejection) {
                            
                            if(rejection.status === 0) {
                                if(rejection.config.url.match(/logout$/))
                                    return rejection;
                                else {
                                    alertsService.addAlert({
                                        title: 'No Response',
                                        message: 'Server did not respond for ' + rejection.config.url,
                                        type: 'error',
                                        removeOnStateChange: false
                                    });
                                }
                            }
                            else if(rejection.status === 403) {
                                alertsService.addAlert({
                                    title: 'Unauthorized Error',
                                    message: 'Server returned unauthorized for ' + rejection.config.url,
                                    type: 'error'
                                });
                            }
                            else {
                                alertsService.addECSErrorMessage(rejection);
                            }


                            return $q.reject(rejection);
                            
                        }
                         
                     }
                     
                 }
                
            ])
            
        }
        
    ]);

})(angular.module('aet.security'));

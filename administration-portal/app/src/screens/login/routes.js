(function(module) {

    module.config(['$stateProvider',
        function($stateProvider) {

            $stateProvider
                .state('login', {
                    url: '/login/:action',
                    views: {
                        'index': {
                            templateUrl: 'src/screens/login/templates/login.html',
                            controller: 'LoginController',
                        }
                    }
                })
                .state('resetPassword', {
                    url: '/resetPassword',
                    views: {
                        'index': {
                            templateUrl: 'src/screens/login/templates/resetPassword.html',
                            controller: 'ResetPasswordController',
                        }
                    }
                })
                .state('passwordReset', {
                    url: '/passwordReset?email&token',
                    views: {
                        'index': {
                            templateUrl: 'src/screens/login/templates/passwordReset.html',
                            controller: 'PasswordResetController',
                            resolve: {
                                passwordReset: ['$stateParams', 'ResetPasswordDTO',
                                    function($stateParams, ResetPasswordDTO) {

                                        var resetPasswordDTO = new ResetPasswordDTO();
                                        var token = $stateParams.token;
                                        var mtoken = token.split(" ").join("+"); //for Mozilla users
                                        mtoken = mtoken.split("%20").join("+"); //for chrome,safari & IE users
                                        resetPasswordDTO.token = mtoken;

                                        var email = $stateParams.email;
                                        var memail = email.split(" ").join("+"); //for Mozilla users
                                        memail = memail.split("%20").join("+"); //for
                                        resetPasswordDTO.email = memail;
                                        return resetPasswordDTO;
                                    }
                                ]
                            }
                        }

                    }
                })


            .state('resetPasswordSuccess', {
                url: '/resetPasswordSuccess',
                views: {
                    'index': {
                        templateUrl: 'src/screens/login/templates/resetPasswordSuccess.html',
                        controller: 'ResetPasswordController',
                    }
                }
            })

            .state('resetPasswordFail', {
                url: '/resetPasswordFail',
                views: {
                    'index': {
                        templateUrl: 'src/screens/login/templates/resetPasswordFail.html',
                        controller: 'PasswordResetController',
                    }
                }
            })

            .state('resetPasswordAccountNotFound', {
                url: '/resetPasswordAccountNotFound',
                views: {
                    'index': {
                        templateUrl: 'src/screens/login/templates/resetPasswordAccountNotFound.html',
                        controller: 'ResetPasswordController',
                    }
                }
            })

            .state('loginError', {
                url: '/loginerror',
                views: {
                    'index': {
                        templateUrl: 'src/screens/login/templates/loginError.html',
                        controller: 'LoginController',
                    }
                }
            })

        }
    ]);

})(angular.module('aet.screens.login'));

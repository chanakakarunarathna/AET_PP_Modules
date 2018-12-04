(function(module) {

    module.provider('security', [function() {

        var authenticationEnabled = true;
        var authorizationEnabled = true;

        var authCookieName;
        var loginState;
        var defaultState;
        var unauthorizedState;

        var loginUrl;
        var logoutUrl;

        return {

            isLoggedIn: ['$q', 'security', 'updatedUser',
                function($q, security, updatedUser) {

                    return $q.when(security.isAuthenticated()).then(function(authenticated) {
                        if(authenticated)
                            return true;
                        else
                            return $q.reject({ needsAuthentication: true });
                    });

                }
            ],

            isAuthorized: ['$q', 'security', 'updatedUser',
                function($q, security, updatedUser) {

					if(angular.isUndefined(this.data) || angular.isUndefined(this.data.feature))
						return true;

                    return $q.when(security.isAuthorized(this.data.feature)).then(function(authorized) {
                        if(authorized)
							return true;
						else
                        	return $q.reject({ needsAuthorization: true });
                    });
                }
            ],

            setAuthenticationEnabled: function(enabled) {
                authenticationEnabled = enabled;
            },

            setAuthorizationEnabled: function(enabled) {
                authorizationEnabled = enabled;
            },

            setAuthCookieName: function(cookieName) {
                authCookieName = cookieName;
            },

            setLoginState: function(state) {
                loginState = state;
            },

            setDefaultState: function(state) {
                defaultState = state;
            },

            setUnauthorizedState: function(state) {
                unauthorizedState = state;
            },

            setLoginUrl: function(url) {
                loginUrl = url;
            },

            setLogoutUrl: function(url) {
                logoutUrl = url;
            },

            getLoginState: function() {
                return loginState;
            },

            getDefaultState: function() {
                return defaultState;
            },

            getUnauthorizedState: function() {
                return unauthorizedState;
            },

            $get: ['$rootScope', '$location', '$q', '$cookies', 'adminUserService', 'userDetails', '$log', 'localStorageService', 'AdminUser', '$state', 'partnerDetails', 'partnerPlaceDetails', 'partnerService', 'partnerPlaceService',
                   function($rootScope, $location, $q, $cookies, adminUserService, userDetails, $log, localStorageService, AdminUser, $state, partnerDetails, partnerPlaceDetails, partnerService, partnerPlaceService) {

                $rootScope.$on('$stateChangeError', function(event, toState, toParams, fromState, fromParams, rejection) {

                    if (rejection && rejection.needsAuthentication === true) {
                        $log.debug("authentication failed");

						event.preventDefault();

                        $state.go(loginState);
                    }
                    else if(rejection && rejection.needsAuthorization === true) {
                        $log.debug("authorization failed");

						event.preventDefault();

                        if(!angular.isDefined(fromState)) { //reloaded
                            $state.go(defaultState);
                        }
                        else {
                            $state.go(unauthorizedState);
                        }
                    }
                    else if(rejection && rejection.updateSecurityDetailsFailed === true) {
                        $log.debug("update security details failed");

						event.preventDefault();

                        userDetails.clear();
						partnerDetails.clear();
						localStorageService.set('isLoggedIn', false);

                        $state.go(loginState);
                    }

                });

                return {

                    isAuthenticated: function() {
                        if(!authenticationEnabled)
                            return true;

						return localStorageService.get('isLoggedIn');
                    },

                    isAuthorized: function(feature) {

                        if(!authorizationEnabled)
                            return true;

						if(angular.isUndefined(feature) || userDetails.getUser().isSuperAdmin)
							return true;

                        if(feature === 'super' && !userDetails.getUser().isSuperAdmin)
                            return false;

						return adminUserService.hasFeature(userDetails.getUser(), feature);
                    },

                    clearAuthentication: function() {
                        userDetails.clear();
						partnerDetails.clear();
                        partnerPlaceDetails.clear();
						localStorageService.set('isLoggedIn', false);
                    },

                    updateSecurityDetails: function() {
                        var self = this;
						var promise;
						var userId = userDetails.getUserId();

						if(authenticationEnabled && (angular.isUndefined(userId) || userId === null))
                            return $q.reject({updateSecurityDetailsFailed:true});
                        else if(!authenticationEnabled && (angular.isUndefined(userId) || userId === null))
                            return new AdminUser();

						try {
							return adminUserService.findSelfAdminUser(userId).then(function(adminUser) {
                                userDetails.setUser(adminUser);

                                if (adminUser.isSuperAdmin) {
                                    return partnerService.searchPartners({count: 0}).then(function(partners) {
                                        partnerDetails.setPartners(adminUser, partners);
                                        return partnerPlaceService.searchPartnerPlace({count: 0}).then(function(partnerPlace) {
                                            partnerPlaceDetails.setPartnerPlaces(adminUser, partnerPlace);
                                            return userDetails.getUser();
                                        });
                                    }, function(err) {
                                        $log.error(err);
                                        return $q.reject({
                                            updateSecurityDetailsFailed: true
                                        });
                                    });
                                } else {
                                    partnerDetails.setPartners(adminUser);
                                    partnerPlaceDetails.setPartnerPlaces(adminUser);

                                    return userDetails.getUser();
                                }
							}, function(err) {
								$log.error(err);
								return $q.reject({updateSecurityDetailsFailed:true});
							});
						}
						catch(err) {
							$log.error(err);
							return $q.reject({updateSecurityDetailsFailed:true});
						}
                    },

                    isAuthenticationEnabled: function() {
                        return authenticationEnabled;
                    },

                    isAuthorizationEnabled: function() {
                        return authorizationEnabled;
                    },

                    getLoginState: function() {
						return loginState;
					},

					getDefaultState: function() {
						return defaultState;
					},

					getUnauthorizedState: function() {
						return unauthorizedState;
					},

					setIsLoggedIn: function(loggedIn) {
						localStorageService.set('isLoggedIn', loggedIn);
					},

					getIsLoggedIn: function() {
						return this.isLoggedIn;
					},

                    getLoginUrl: function() {
                        return loginUrl;
                    },

                    getLogoutUrl: function() {
                        return logoutUrl;
                    }

                };

            }]

        }
    }]);

})(angular.module('aet.security'));

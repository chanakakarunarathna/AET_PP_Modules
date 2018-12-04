(function(module) {

    module.controller('EditPartnerPlaceController', ['$scope', '$rootScope', '$q', '$filter', 'alertsService', 'security', 'ValidateDurationDTO', 'ChangeChannelDTO', 'partnerPlaceDetails', 'partnerDetails', 'userDetails', 'localStorageService', 'modalService', 'partnerPlaceService', '$state', '$log', 'partnerPlace', 'places', 'adminUserDetails', 'uploadManager', 's3Uploader', 's3config', '$stateParams', 'SendTestEmailDTO', 'envConfig', '$timeout', 'placesService',
        function($scope, $rootScope, $q, $filter, alertsService, security, ValidateDurationDTO, ChangeChannelDTO, partnerPlaceDetails, partnerDetails, userDetails, localStorageService, modalService, partnerPlaceService, $state, $log, partnerPlace, places, adminUserDetails, uploadManager, s3Uploader, s3config, $stateParams, SendTestEmailDTO, envConfig, $timeout, placesService) {

            var _timeout;
            $scope.partnerPlace = {};
            $scope.topTips = [];
            $scope.partnerCollection = [];

            $scope._partnerPlaceErrors = [];
            $scope._heroErrors = [];
            $scope._topTipsErrors = [];
            $scope._partnerCollectionErrors = [];
            $scope._getToKnowErrors = [];
            $scope._vendorActivityCountErrors = [];

            $scope.tab = {};
            $scope.tab[1] = false;
            $scope.tab[2] = true;
            $scope.tab[3] = true;
            $scope.tab[4] = true;
            $scope.tab[5] = true;
            $scope.tab[6] = true;

            $scope.partnerplace = partnerPlace;

            if($scope.partnerplace.isTopDestination == undefined){
                $scope.partnerplace.isTopDestination = new Object();
                $scope.partnerplace.isTopDestination = partnerPlace.isTopDestination;
            }
            if($scope.partnerplace.hasCityGuide == undefined){
                $scope.partnerplace.hasCityGuide = new Object();
                $scope.partnerplace.hasCityGuide =  partnerPlace.hasCityGuide;
            }
//
            $scope.searchPlaces = {};
            $scope.searchPlaces.selectedPlace = places;

            $scope.booleanOptions = [{
	              "key": "false",
	              "value": "FALSE"
        	}, {
	        	  "key": "true",
	              "value": "TRUE"
        	}];


            $scope.partnerplace.isTopDestination = _.findWhere($scope.booleanOptions, {
                "key": partnerPlace.isTopDestination
              });

            $scope.partnerplace.hasCityGuide = _.findWhere($scope.booleanOptions, {
                "key": partnerPlace.hasCityGuide
              });

            $scope.topTips = partnerPlace.topTips;
            $scope.partnerCollection = partnerPlace.partnerCollection;

            if($scope.partnerplace.getToKnow == undefined){
                $scope.partnerplace.getToKnow = new Object(); // or var map = {};
            }

            function setFormValidity(form, item, array, modelType, model) {
                if (modelType) {
                    form[item] = {};
                    form[item].$showError = false;
                    form[item].$error = {};
                    form[item].$error.required = false;
                    form[item].$hasError = function() {
                        return _.keys(form[item].$error).length > 0;
                    };
                    if (model) {
                        form[item].$invalid = model.length === 0;
                    } else {
                        form[item].$invalid = model === undefined;
                    }
                }
                (function() {
                    if (form[item].$invalid) {
                        form[item].$showError = true;
                        if (modelType) {
                            form[item].$error.required = true;
                        }
                        form[item].$hasError();
                        if (array.indexOf(item) === -1) {
                            array.push(item);
                        }
                    } else {
                        if (array.indexOf(item) !== -1) {
                            array.splice(array.indexOf(item), 1);
                        }
                    }
                })();
            }


            $scope.partnerPlaceFormValidity = function(form) {
//                setFormValidity(form, 'description', $scope._partnerPlaceErrors);
//                setFormValidity(form, 'partnerPlaceLeader', $scope._partnerPlaceErrors);

            };

            $scope.go = function(type, currentTab) {
                var holder = 0,
                    length = 0;
                if (currentTab) {
                    angular.forEach($scope.tab, function(tab, key) {
                        length++;
                        $scope.tab[key] = true;
                    });
                    if (holder < length && type === 'next') {
                        holder = ++currentTab;
                    } else if (holder < length && type === 'previous') {
                        holder = --currentTab;
                    } else {
                        holder = 1;
                    }
                    $scope.tab[holder] = false;
                }

            };

            partnerPlaceDetails.setSelectedPartnerPlace(partnerPlace.id);
            $scope.adminusers = angular.copy(adminUserDetails.results);

            //getting logged user for check in BU
            $scope.logginId = userDetails.getUserId();

            $scope.disabled = undefined;
            $scope.searchEnabled = undefined;

            $scope.enable = function() {
                $scope.disabled = false;
            };

            $scope.disable = function() {
                $scope.disabled = true;
            };

            $scope.enableSearch = function() {
                $scope.searchEnabled = true;
            };

            $scope.disableSearch = function() {
                $scope.searchEnabled = false;
            };

            $scope.partnerPlaceST1FormValidity = function(){
              console.log("Object.keys($scope.searchPlaces.selectedPlace).length", Object.keys($scope.searchPlaces.selectedPlace).length);
              console.log("$scope.partnerplace", $scope.partnerplace);
              console.log("$scope.multipleBU.selectedUsers.length", $scope.multipleBU.selectedUsers.length);
                if(Object.keys($scope.searchPlaces.selectedPlace).length > 0 &&
                    $scope.partnerplace.location != undefined &&
                    $scope.partnerplace.activityCount != undefined &&
                    $scope.multipleBU.selectedUsers.length > 0 ){
                    return true;
                }else{
                    return false;
                }
            };

            $scope.isValidCMSDataForm  = function (dto) {
                if((dto!=null || dto!=undefined) && dto.title != undefined && dto.subTitle != undefined ){
                    return true;
                }else{
                    return false;
                }
            };

            $scope.manullayAddHeroRow = function (dto, type) {
                if(type == 'TOPTIPS'){
                    $scope.topTips.push(dto);
                    $scope.toptipsDr = null;
                }else if(type == 'PARTNER'){
                    $scope.partnerCollection.push(dto);
                    $scope.partnerCollectionDr = null;
                }
            };

            $scope.deleteUniqueRow = function (dto, type) {
                if(type == 'TOPTIPS'){
                    var idx = $scope.topTips.indexOf(dto);
                    // is currently selected
                    if (idx > -1) {
                        $scope.topTips.splice(idx, 1);
                    }
                }else if(type == 'PARTNER'){
                    var idx = $scope.partnerCollection.indexOf(dto);
                    // is currently selected
                    if (idx > -1) {
                        $scope.partnerCollection.splice(idx, 1);
                    }
                }
            };


            $scope.manuallyAddedRowMapValidation = function (key, value) {
                if(key != undefined && value != undefined){
                    return true;
                }else{ return false; }
            };

            $scope.manullayAddMapRow = function (key, value) {
                $scope.partnerplace.getToKnow[key] = value;
            };

            $scope.deleteUniqueRowMap = function (key) {
                delete $scope.partnerplace.getToKnow[key];
            };

            $scope.addVendorActivityCountRow = function (key, value) {
            	$scope.partnerplace.vendorActivityCount[key] = value;
            	$scope.vendorActivityCountKey = null;
            	$scope.vendorActivityCountValue = null;
            };

            $scope.deleteVendorActivityCountRow = function (key) {
                delete $scope.partnerplace.vendorActivityCount[key];
            };

            //Refresh Places list in the Drop Down
            $scope.refreshPlaces= function(searchName) {
                if(_timeout) { // if there is already a timeout in process cancel it
                    $timeout.cancel(_timeout);
                }
                _timeout = $timeout(function() {
                    var searchObj = {q: searchName, searchby: 'all', count: 20 };
                    return placesService.searchByQueryPlacess(searchObj).then(function(response) {
                        $scope.places = response.results;
                    });
                    _timeout = null;
                }, 2000);
            };


            $scope.multipleBU = {};
            $scope.multipleBU.selectedUsers = partnerPlace.partnerPlaceAdmins;
            $scope.multipleBU.selectedTeams = partnerPlace.partnerPlaceTeamMembers;
            $scope.multipleBU.selectedSupports = partnerPlace.partnerPlaceSupportMembers;
            if (angular.isUndefinedOrNull(partnerPlace.emailConfig)) {
            	partnerPlace.emailConfig = {
                    emailGateway: 'DEFAULT'
                };
            }

            $scope.buttonLoader = false;

            //Admin User Filter
            $scope.$watchCollection('multipleBU', function(newValue, oldValue) {
                var tempBUserList = angular.copy(adminUserDetails.results);
                //this removes every added user from the full admin user list
                angular.forEach($scope.multipleBU, function(list, buKey) {
                    angular.forEach(list, function(user, lKey) {
                        angular.forEach(tempBUserList, function(bUser, bKey) {
                            if (angular.equals(user.id, bUser.id)) {
                                tempBUserList.splice(bKey, 1);
                            }
                        });
                    });
                });

                $scope.adminusers = tempBUserList;
            });

            $scope.submitPartnerPlaceForm = function() {

                var selectedBUIds = [];
                $scope.partnerplace.partnerPlaceLeaderIds = [];
                //$scope.partnerplace.partnerPlaceAdminIds = [];
                $scope.partnerplace.partnerPlaceTeamMemberIds = [];
                $scope.partnerplace.partnerPlaceSupportMemberIds = [];

                $scope.partnerplace.parentId = $scope.searchPlaces.selectedPlace.id;
                $scope.partnerplace.parentWebId = $scope.searchPlaces.selectedPlace.webId;

                $scope.partnerplace.topTips = $scope.topTips;
                $scope.partnerplace.partnerCollection = $scope.partnerCollection;

                if (angular.isDefined($scope.multipleBU.selectedUsers) && $scope.multipleBU.selectedUsers !== null) {
                    $scope.partnerplace.partnerPlaceLeaderIds = [];
                    for (var i = 0; i < $scope.multipleBU.selectedUsers.length; i++) {
                        if ($scope.multipleBU.selectedUsers[i].id !== null) {
                            $scope.partnerplace.partnerPlaceLeaderIds[i] = $scope.multipleBU.selectedUsers[i].id;
                            selectedBUIds.push($scope.multipleBU.selectedUsers[i].id);
                        }
                    }
                }

                if (angular.isDefined($scope.multipleBU.selectedTeams) && $scope.multipleBU.selectedTeams !== null) {
                    $scope.partnerplace.partnerPlaceTeamMemberIds = [];
                    for (var i = 0; i < $scope.multipleBU.selectedTeams.length; i++) {
                        if ($scope.multipleBU.selectedTeams[i].id !== null) {
                            $scope.partnerplace.partnerPlaceTeamMemberIds[i] = $scope.multipleBU.selectedTeams[i].id;
                            selectedBUIds.push($scope.multipleBU.selectedTeams[i].id);
                        }
                    }
                }


                if (angular.isDefined($scope.multipleBU.selectedSupports) && $scope.multipleBU.selectedSupports !== null) {
                    $scope.partnerplace.partnerPlaceSupportMemberIds = [];
                    for (var i = 0; i < $scope.multipleBU.selectedSupports.length; i++) {
                        if ($scope.multipleBU.selectedSupports[i].id !== null) {
                            $scope.partnerplace.partnerPlaceSupportMemberIds[i] = $scope.multipleBU.selectedSupports[i].id;
                            selectedBUIds.push($scope.multipleBU.selectedSupports[i].id);
                        }

                    }
                }

                var isLoginUserIn = false;
                if (userDetails.getUser().isSuperAdmin) {
                    isLoginUserIn = true;
                } else {
                    for (var i = 0; selectedBUIds.length > i; i++) {
                        if (selectedBUIds[i] === $scope.logginId) {
                            isLoginUserIn = true;
                        }
                    }
                }


                if (!isLoginUserIn) {
                    alertsService.addAlert({
                        title: 'Error',
                        message: 'Logged In User should be selected as partner place leader or team or support user',
                        type: 'error',
                        removeOnStateChange: 2
                    });
                } else if ($scope.partnerplace.partnerPlaceLeaderIds.length === 0) {
                    alertsService.addAlert({
                        title: 'Error',
                        message: 'Partner Place Leaders cannot be empty',
                        type: 'error',
                        removeOnStateChange: 2
                    });

                }
                else {
                    $rootScope.loader = true;
                    if ($scope.partnerplace.emailConfig.emailGateway === 'SMTP') {
                        if (!($scope.isValidTestEmailConfig() && $scope.isEmailConfigTested())) {
                            $scope.partnerplace.emailConfig = {
                                emailGateway: null
                            };
                        }
                    } else {
                        if (angular.isUndefinedOrNull($scope.partnerplace.emailConfig.senderName) ||
                            angular.isUndefinedOrNull($scope.partnerplace.emailConfig.replyToEmail)) {
                            $scope.partnerplace.emailConfig = {
                                emailGateway: null
                            };
                        }
                    }
                    partnerPlaceService.updatePartnerPlace($scope.partnerplace).then(function(response) {
                        if (angular.isUndefinedOrNull(partnerPlace.emailConfig.emailGateway)) {
                        	partnerPlace.emailConfig = {
                                emailGateway: 'DEFAULT'
                            };
                        }
                        alertsService.addAlert({
                            title: 'Success',
                            message: 'Partner Place "' + $scope.searchPlaces.selectedPlace.placePassRegion +' - '+$scope.searchPlaces.selectedPlace.country + '" successfully updated',
                            type: 'success',
                            removeOnStateChange: 2
                        });
                        //updating changed partner place details
                        if (angular.isDefined($state.params.resultRoute)) {
                            $rootScope.showLoader("Updating...");
                            $state.go($state.params.resultRoute, $state.params.resultParams, {
                                reload: true,
                            }).then(function() {
                                $rootScope.hideLoader();
                            });
                        } else {
//                            var selectedPartnerPlaceId = partnerPlaceDetails.getSelectedPartnerPlace().id;
//                            localStorageService.set('partnerPlaceId', selectedPartnerPlaceId);
                        	$state.go('index.secured.partnerPlace.search', {}, {
                                reload: true
                            });
                        }
                        $rootScope.loader = false;
                    }, function(err) {
                        $rootScope.loader = false;
                        console.error('Could not update Partner Place', err);
                    });

                }
            };

            $scope.isValidPartnerPlaceInfo = function() {
                return this.partnerPlaceST1FormValidity();

            };

            $scope.getLength = function(obj) {
                return Object.keys(obj).length;
            }

        }
    ]);


})(angular.module('aet.screens.partnerPlace'));

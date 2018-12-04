(function(module) {

    module.controller('CreatePartnerPlaceController', ['$scope', '$rootScope', 'localStorageService', 'security', 'partnerDetails', 'userDetails', 'PartnerPlace', 'partnerPlaceService', 'adminUserDetails', 'alertsService', '$modal', '$state', '$log', 'modalService', '$filter', 'uploadManager', 's3Uploader', 's3config', 'partnerPlaceDetails', 'places', 'SendTestEmailDTO', 'envConfig', 'placesService', '$timeout',
        function($scope, $rootScope, localStorageService, security, partnerDetails, userDetails, PartnerPlace, partnerPlaceService, adminUserDetails, alertsService, $modal, $state, $log, modalService, $filter, uploadManager, s3Uploader, s3config, partnerPlaceDetails, places, SendTestEmailDTO, envConfig, placesService, $timeout) {

            var _timeout;
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

            $scope.searchPlaces = {};
            $scope.searchPlaces.selectedPlace = [];

            $scope.partnerplace = new PartnerPlace();
            $scope.partnerplace.creatorId = userDetails.getUserId();
            $scope.partnerplace.partnerPlaceLeaderIds = [];
            $scope.partnerplace.partnerPlaceTeamMemberIds = [];
            $scope.partnerplace.partnerPlaceSupportMemberIds = [];
            $scope.adminUserDetails = adminUserDetails.results;


            $scope.partnerplace.isTopDestination = {key: 'false', value: 'FALSE'};
            $scope.partnerplace.hasCityGuide = {key: 'false', value: 'FALSE'};

            $scope.booleanOptions = [{
	              "key": "false",
	              "value": "FALSE"
            	}, {
	        	  "key": "true",
	              "value": "TRUE"
            	}];

            $scope.buttonLoader = false;
            $scope.partnerplace.getToKnow =  new Object();
            $scope.partnerplace.vendorActivityCount =  new Object();
            
            $scope.defaultEmailSender = envConfig('defaultEmailSender');

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
                	if (form[item]!=undefined && form[item].$invalid) {
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

            $scope.partnerPlaceST1FormValidity = function(){
                if(Object.keys($scope.searchPlaces.selectedPlace).length > 0 &&
                    $scope.partnerplace.location != undefined &&
                    $scope.partnerplace.activityCount != undefined &&
                    $scope.multipleBU.selectedUsers.length > 0 ){
                    return true;
                }else{
                    return false;
                }
            };

            $scope.topTips1 = [];
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

            $scope.isHeroFormValid  = function (dto) {
                if((dto!=null || dto!=undefined) && dto.title != undefined && dto.subTitle != undefined ){
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

            angular.isUndefinedOrNull = function(val) {
                return angular.isUndefined(val) || val === null || val === "";
            };

            //$scope.places = {};
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

            $scope.multipleBU = {};
            $scope.multipleBU.selectedUsers = [];
            $scope.multipleBU.selectedAdmins = [];
            $scope.multipleBU.selectedTeams = [];
            $scope.multipleBU.selectedSupports = [];


            $scope.adminusers = adminUserDetails.results;

            $scope.$watchCollection('multipleBU', function(newValue, oldValue) {

                //Remove items from partnerplace leaders and add back to admin users
                if (oldValue.selectedUsers.length > newValue.selectedUsers.length) {
                    for (var a = 0; a < oldValue.selectedUsers.length; a++) {
                        var int = 0;
                        for (var b = 0; b < newValue.selectedUsers.length; b++) {
                            //console.log(newValue.selectedUsers[b]);
                            if (oldValue.selectedUsers[a].id == newValue.selectedUsers[b].id) {
                                int = 1;
                            }
                        }
                        if (int === 0) {
                            $scope.adminusers.push(oldValue.selectedUsers[a]);
                        }
                    }
                }

                //Add items from partnerplace leaders and remove from admin users
                if (oldValue.selectedUsers.length < newValue.selectedUsers.length) {
                    for (var i = 0; i < newValue.selectedUsers.length; i++) {
                        for (var j = 0; j < $scope.adminusers.length; j++) {
                            if (newValue.selectedUsers[i] === $scope.adminusers[j]) {
                                $scope.adminusers.splice(j, 1);
                            }
                        }
                    }
                }

                //Remove items from partnerplace admins and add back to admin users
                if (oldValue.selectedAdmins.length > newValue.selectedAdmins.length) {
                    for (var c = 0; c < oldValue.selectedAdmins.length; c++) {
                        var int = 0;
                        for (var d = 0; d < newValue.selectedAdmins.length; d++) {
                            //console.log(newValue.selectedUsers[d]);
                            if (oldValue.selectedAdmins[c].id == newValue.selectedAdmins[d].id) {
                                int = 1;
                            }
                        }
                        if (int === 0) {
                            $scope.adminusers.push(oldValue.selectedAdmins[c]);
                        }
                    }
                }

                //Add items from partnerplace admins and remove from admin users
                if (oldValue.selectedAdmins.length < newValue.selectedAdmins.length) {
                    for (var k = 0; k < newValue.selectedAdmins.length; k++) {
                        for (var l = 0; l < $scope.adminusers.length; l++) {
                            if (newValue.selectedAdmins[k] === $scope.adminusers[l]) {
                                $scope.adminusers.splice(l, 1);
                            }
                        }
                    }
                }

                //Remove items from partnerplace teams and add back to admin users
                if (oldValue.selectedTeams.length > newValue.selectedTeams.length) {
                    for (var a = 0; a < oldValue.selectedTeams.length; a++) {
                        var int = 0;
                        for (var b = 0; b < newValue.selectedTeams.length; b++) {
                            //console.log(newValue.selectedUsers[b]);
                            if (oldValue.selectedTeams[a].id == newValue.selectedTeams[b].id) {
                                int = 1;
                            }
                        }
                        if (int === 0) {
                            $scope.adminusers.push(oldValue.selectedTeams[a]);
                        }
                    }
                }

                //Add items from partnerplace teams and remove from admin users
                if (oldValue.selectedTeams.length < newValue.selectedTeams.length) {
                    for (var i = 0; i < newValue.selectedTeams.length; i++) {
                        for (var j = 0; j < $scope.adminusers.length; j++) {
                            if (newValue.selectedTeams[i] === $scope.adminusers[j]) {
                                $scope.adminusers.splice(j, 1);
                            }
                        }
                    }
                }

                //Remove items from partnerplace supports and add back to admin users
                if (oldValue.selectedSupports.length > newValue.selectedSupports.length) {
                    for (var a = 0; a < oldValue.selectedSupports.length; a++) {
                        var int = 0;
                        for (var b = 0; b < newValue.selectedSupports.length; b++) {
                            //console.log(newValue.selectedUsers[b]);
                            if (oldValue.selectedSupports[a].id == newValue.selectedSupports[b].id) {
                                int = 1;
                            }
                        }
                        if (int === 0) {
                            $scope.adminusers.push(oldValue.selectedSupports[a]);
                        }
                    }
                }

                //Add items from partnerplace supports and remove from admin users
                if (oldValue.selectedSupports.length < newValue.selectedSupports.length) {
                    for (var i = 0; i < newValue.selectedSupports.length; i++) {
                        for (var j = 0; j < $scope.adminusers.length; j++) {
                            if (newValue.selectedSupports[i] === $scope.adminusers[j]) {
                                $scope.adminusers.splice(j, 1);
                            }
                        }
                    }
                }

            });

            $scope.submitPartnerPlaceForm = function() {
                var selectedBUIds = [];

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
                        if (selectedBUIds[i] === $scope.partnerplace.creatorId) {
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

                    partnerPlaceService.createPartnerPlace($scope.partnerplace).then(function(data) {
                        $rootScope.loader = false;
                        alertsService.addAlert({
                            title: 'Success',
                            message: 'Partner Place "' + $scope.searchPlaces.selectedPlace.placePassRegion +' - '+$scope.searchPlaces.selectedPlace.country + '" successfully created',
                            type: 'success',
                            removeOnStateChange: 2
                        });
                        //updating newwly created partner place and redirect to edit map screen
                        security.updateSecurityDetails();
                        partnerPlaceDetails.clear();
                        localStorageService.set('partnerPlaceId', data.id);
                        var user = userDetails.getUser();
                        $state.go('index.secured.partnerPlace.search', {}, {
                            reload: true
                        });
                        
//                        if (_.any($scope.partnerplace.partnerPlaceLeaderIds, function(id) {
//                                return user.id === id;
//                            })
//                        ) {
//                            $state.go('index.secured.map.edit', {}, {
//                                reload: true
//                            });
//                        } else if (_.any($scope.partnerplace.partnerPlaceTeamMemberIds, function(id) {
//                                return user.id === id;
//                            }) || _.any($scope.partnerplace.partnerPlaceSupportMemberIds, function(id) {
//                                return user.id === id;
//                            })) {
//                            $state.go('index.secured.partnerPlace.search', {}, {
//                                reload: true
//                            });
//                        }
                    }, function(err) {
                        $rootScope.loader = false;
                        //console.error('Could not create partner place', err);
                    });
                }
            };

            $scope.isValidPartnerPlaceInfo = function() {
                return this.partnerPlaceST1FormValidity();
            }

            $scope.getLength = function(obj) {
                if(obj != null){
                    return Object.keys(obj).length;
                }
            }
        }
    ]);

})(angular.module('aet.screens.partnerPlace'));
(function(module) {

    module.controller('CreatePartnerConfigController', ['$scope', '$rootScope', 'localStorageService', 'security', 'partnerDetails', 'userDetails', 'PartnerConfig', 'partnerConfigService', 'alertsService', '$modal', '$state', '$log', 'modalService', '$filter', 'uploadManager', 's3Uploader', 's3config', 'envConfig','productService',
        function($scope, $rootScope, localStorageService, security, partnerDetails, userDetails, PartnerConfig, partnerConfigService, alertsService, $modal, $state, $log, modalService, $filter, uploadManager, s3Uploader, s3config, envConfig, productService) {

            $scope._partnerConfigErrors = [];
            $scope._mapErrors = [];
            $scope._emailErrors = [];

            $scope.tab = {};
            $scope.tab[1] = false;
            $scope.tab[2] = true;
            $scope.tab[3] = true;

            $scope.buttonLoader = false
            $scope.btnDisabled = false;
            $scope.partnerconfig = new PartnerConfig();

            $scope.partnerconfig.destinations = [];
            $scope.partnerconfig.featuredProductIds = [];
            $scope.featuredProductsSearchPerformed = false;
            $scope.featuredProductsSearchResults = [];
            $scope.featuredProductsSelectedResults = [];
            $scope.filteredFeaturedProductsSelectedResults = [];

            $scope.currentPage = 1, $scope.numPerPage = 5, $scope.maxSize = 5, $scope.totalItems = 0;
            $scope.selectedCurrentPage = 1, $scope.selectedNumPerPage = 5;

            $scope.selectedFeaturedProducts = function(){
            	var begin = (($scope.selectedCurrentPage-1) * $scope.selectedNumPerPage),
                end = begin + $scope.selectedNumPerPage;
            	if (angular.isDefined($scope.featuredProductsSearchResults)) {
            		$scope.filteredFeaturedProductsSelectedResults = $scope.featuredProductsSelectedResults.slice(begin, end);
	            }
            }

            $scope.objectName = function(objectID){
            	return objectID.replace(/\-/g,'_');
            }

            $scope.searchFeaturedProducts = function(searchName) {
	            var searchObj = {q: searchName, searchby: 'name', count : $scope.numPerPage, page : ($scope.currentPage-1)};
	            return productService.searchProducts(searchObj).then(function(response) {
	            	$scope.featuredProductsSearchPerformed = true;
	            	$scope.featuredProductsSearchResults = response;
	            	$scope.totalItems = $scope.numPerPage*response.pagination.totalPages;
	            });
            };

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


            $scope.partnerConfigFormValidity = function(form) {
//                setFormValidity(form, 'name', $scope._partnerConfigErrors);
//                setFormValidity(form, 'description', $scope._partnerConfigErrors);
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

            $scope.addDestinationOrFeaturedProductRow = function (dto, type) {
            	if(type == 'DESTINATIONS'){
            		if ($.inArray(dto, $scope.partnerconfig.destinations) < 0) {
            			$scope.partnerconfig.destinations.push(dto);
                        $scope.destinationsDr = null;
					}else {
						alert('Destination '+ dto +' exists.');
					}                	
                }
            	if(type == 'FEATURED_PRODUCT'){
					if ($.inArray(dto.objectID, $scope.partnerconfig.featuredProductIds) < 0) {
						$scope.featuredProductsSelectedResults.push(dto);
						$scope.partnerconfig.featuredProductIds.push(dto.objectID);
						var objectID = $scope.objectName(dto.objectID);
						$scope['btnDisabled_' + objectID] = true;
						if($scope.filteredFeaturedProductsSelectedResults.length!=0 && $scope.filteredFeaturedProductsSelectedResults.length%($scope.selectedNumPerPage)==0){
							$scope.selectedCurrentPage = 1 + $scope.selectedCurrentPage;
							console.log($scope.selectedCurrentPage);
						}
						$scope.selectedFeaturedProducts();
				    }else {
						alert('Featured Product '+ dto.objectID +' exists.');
					}
                }
            };

            $scope.deleteDestinationOrFeaturedProductRow = function (dto, type) {
                if(type == 'DESTINATIONS'){
                	var delModalInstance = modalService.customDeleteModal('Destinations', 'Are you sure you want to delete this item?');
                	delModalInstance.result.then(function() {
                		var indexDes = $scope.partnerconfig.destinations.indexOf(dto);
                        if (indexDes > -1) {
                            $scope.partnerconfig.destinations.splice(indexDes, 1);
                        }
                    });
                }
                if(type == 'FEATURED_PRODUCT'){
                	var delModalInstance = modalService.customDeleteModal('Featured Products', 'Are you sure you want to delete this item?');
                	delModalInstance.result.then(function() {
                		var indexFeaturedProd = $scope.partnerconfig.featuredProductIds.indexOf(dto.objectID);
                        if (indexFeaturedProd > -1) {
                        	$scope.partnerconfig.featuredProductIds.splice(indexFeaturedProd, 1);
                        }
                        var indexSelectedFeaturedProd = $scope.featuredProductsSelectedResults.indexOf(dto);
                        if (indexSelectedFeaturedProd > -1) {
                        	var objectID = $scope.objectName(dto.objectID);
                    		$scope['btnDisabled_' + objectID] = false;
                        	$scope.featuredProductsSelectedResults.splice(indexSelectedFeaturedProd, 1);
                    	}
                        var index = $scope.filteredFeaturedProductsSelectedResults.indexOf(dto);
                        if(index > -1){
                        	$scope.filteredFeaturedProductsSelectedResults.splice(index, 1);
                        	if($scope.filteredFeaturedProductsSelectedResults.length%($scope.selectedNumPerPage)==1){
                        		$scope.selectedFeaturedProducts();
    						}
                        }
                    });
            	}
            };

            $scope.isValidCMSDataForm  = function (dto) {
                if((dto!=null || dto!=undefined) && dto.title != undefined && dto.subTitle != undefined ){
                    return true;
                }else{
                    return false;
                }
            };

            $scope.isValidPartnerConfigInfo = function(dto) {
            	if((dto!=null || dto!=undefined) && dto.title != undefined && dto.subTitle != undefined ){
                    return true;
                }else{
                    return false;
                }
            };

            $scope.getLength = function(obj) {
                return Object.keys(obj).length;
            }

            angular.isUndefinedOrNull = function(val) {
                return angular.isUndefined(val) || val === null || val === "";
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

            $scope.clear = function() {

            };

            $scope.submitPartnerConfigForm = function() {
            	$scope.partnerconfig.partnerId = partnerDetails.getSelectedPartner().id;
            	$scope.partnerconfig.destinations = $scope.partnerconfig.destinations;
            	$scope.partnerconfig.featuredProductIds = $scope.partnerconfig.featuredProductIds;

            	 var isLoginUserIn = false;
                 if (userDetails.getUser().isSuperAdmin) {
                     isLoginUserIn = true;
                 }

                if (!isLoginUserIn) {
                    alertsService.addAlert({
                        title: 'Error',
                        message: 'Logged In User should be selected as partner config leader or team or support user',
                        type: 'error',
                        removeOnStateChange: 2
                    });

                }
//                else if ($scope.partnerconfig.partnerConfigLeaderIds.length === 0) {
//                    alertsService.addAlert({
//                        title: 'Error',
//                        message: 'Partner Config Leaders cannot be empty',
//                        type: 'error',
//                        removeOnStateChange: 2
//                    });
//
//                }
                else {
                    $rootScope.loader = true;

                    partnerConfigService.createPartnerConfig($scope.partnerconfig).then(function(data) {
                        $rootScope.loader = false;
                        alertsService.addAlert({
                            title: 'Success',
                            message: 'Partner Config "' + $scope.partnerconfig.hero.title + '" successfully created',
                            type: 'success',
                            removeOnStateChange: 2
                        });
                        //updating newwly created partner config and redirect to edit map screen
                        security.updateSecurityDetails();
                        localStorageService.set('partnerConfigId', data.id);
                    }, function(err) {
                        $rootScope.loader = false;
                        console.error('Could not create partner config', err);
                    });
                }
            };

            /*S3 Implementation*/

            $scope.downloadFile = function(uri) {
                s3Uploader.getDownloadSignature(uri).then(function(response) {
                    window.open(response.data.downloadLink, '_blank');
                }, function(err) {
                    $log.error("Could not save application", err);
                });
            };

            var uploadOptions = {
                destroyWithScope: $scope,
                bucket: s3config(),
                acl: 'public-read',
                folder: '/administration/' + partnerDetails.getSelectedPartner().id + '/partnerConfig/',
                maxsize: '5',
                fileTypes: ['gif', 'jpg', 'jpeg', 'png', 'gif', 'ico', 'svg', 'psd', 'raw', 'tiff'],
                imageDimentions: ['320x120', 'The logo file needs to be 320 pixels wide or less or 120 pixels tall or less to display well on your outbound email message.']
            };

            $scope.uploadManager = uploadManager.newS3UploadId(uploadOptions);

            $scope.getFileName = function(fileURL) {
                if (!fileURL) {
                    return '';
                }
                var fileName = fileURL.split("/").pop();
                fileName = fileName.substring(fileName.indexOf("_") + 1);
                return fileName.substring(0, fileName.lastIndexOf('.'));
            };
        }
    ]);

})(angular.module('aet.screens.partnerConfig'));

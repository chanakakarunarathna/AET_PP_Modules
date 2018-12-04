(function(module) {

  module.controller('EditPlacesController', ['$scope', 'placesService', 'places', 'alertsService', '$state', '$log', 'taOptions', '$rootScope', '$window', 'adminUserService', 'userDetails', 'modalService',
    function($scope, placesService, places, alertsService, $state, $log, taOptions, $rootScope, $window, adminUserService, userDetails, modalService) {

      $scope.stripFormat = function(text) {
        return text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

      $scope.latRegex = /^[-]?([1-8]?\d(\.\d+)?|90(\.0+)?)$/;
      $scope.longRegex = /^[-]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
      
      $scope.places = places;

      $scope.places.aliases = [];
      $scope.places.aliases = places.aliasList;
      
      $scope.places.state = _.findWhere($scope.stateDetails, {
        "key": places.state
      });

      $scope.subLevels = [{
        "key": "1",
        "value": "Sub Level 1"
      }, {
        "key": "2",
        "value": "Sub Level 2"
      }, {
        "key": "3",
        "value": "Sub Level 3"
      }]

      $scope.places.subLevel = _.findWhere($scope.subLevels, {
        "key": places.subLevel
      });

      $scope.locationTypes = [	'administrative_area_level_1', 'administrative_area_level_2', 
			'administrative_area_level_3', 'country','locality','political','postal_code',
			'postal_town'];

      // rich text editor tool bar customization
      taOptions.toolbar = [
        ['bold', 'italics', 'underline'],
        ['fontSize'],
        ['ul', 'ol'],
        ['indent', 'outdent'],
        ['html'],
        ['insertLink'],
        ['wordcount', 'charcount']
      ];


      $scope.submitPlaceForm = function(params) {
        $rootScope.loader = true;
        placesService.editPlaces(places).then(function(data) {
            alertsService.addAlert({
              title: 'Success',
              message: 'Places "' + places.webId + '" successfully updated',
              type: 'success',
              removeOnStateChange: 2
            });
            $rootScope.loader = false;
            $state.go('index.secured.places.search', {}, {
              reload: true
            }).then(function() {
              adminUserService.findSelfAdminUser(userDetails.getUserId()).then(function(adminUser) {
                userDetails.setUser(adminUser);

                if (adminUser.isSuperAdmin) {
                  placesService.searchPlacess({
                    count: 10
                  }).then(function(places) {
                    /*var cid = placesDetails.getSelectedPlaces().id;
                    placesDetails.clear();
                    placesDetails.setPlacess(adminUser, places);
                    placesDetails.setSelectedPlaces(cid);*/
                  }, function(err) {
                    $log.error(err);
                  });
                }
              });
            });
          },
          function(err) {
            $rootScope.loader = false;
            console.error('Could not edit places', err);
          });

      };

      $scope.manuallyAddedRowValidation = function () {
        var isManuallyAddingRecordButtonValidated = false;
        if (angular.isDefined($scope.manuallyAddedLongName) &&  $scope.manuallyAddedLongName != ''
        	&& angular.isDefined($scope.manuallyAddedShortName) &&  $scope.manuallyAddedShortName != ''
        	&& ($scope.manuallyAddedType.type.length>0)) {
          isManuallyAddingRecordButtonValidated = true;
        }
        return isManuallyAddingRecordButtonValidated;
      };

      $scope.manullayAddRow = function (manuallyAddedLongName, manuallyAddedShortName, manuallyAddedType) {
        var manuallyAddedObject = {
          'longName': manuallyAddedLongName,
          'shortName': manuallyAddedShortName,
          'types': $scope.addComponentType
        };

        var exists =  false;
        if($scope.places.addrsComponents.length > 0){
        	for (var i = 0; i < $scope.places.addrsComponents.length; i++) {
        		if (JSON.stringify(JSON.parse(angular.toJson($scope.places.addrsComponents[i]))) == JSON.stringify(JSON.parse(angular.toJson(manuallyAddedObject)))) {
                	exists = true;
                	alert('Address Components exists.');
                }
            }
        }

        if(exists == false){
        	$scope.places.addrsComponents.push(manuallyAddedObject);
        }

        $scope.manuallyAddedLongName = undefined;
        $scope.manuallyAddedShortName = undefined;
        $scope.manuallyAddedType.type = undefined;
        $scope.addComponentType = [];
      };

      $scope.deleteUniqueRow = function (addComponents) {
    	var delModalInstance = modalService.customDeleteModal('Address Components', 'Are you sure you want to delete this item?');
      	delModalInstance.result.then(function() {
	        var idx = $scope.places.addrsComponents.indexOf(addComponents);
	        // is currently selected
	        if (idx > -1) {
	          $scope.places.addrsComponents.splice(idx, 1);
	        }
      	});
      };


      $scope.manuallyAddedType = {};
      $scope.manuallyAddedType.type = [];
      $scope.addComponentType = [];

      $scope.$watchCollection('manuallyAddedType', function(newValue, oldValue) {

        //Adding values to the type
        if (newValue != undefined) {
          for (var a = 0; a < newValue.type.length; a++) {
            $scope.addComponentType.push(newValue.type[a]);
          }
        }
      });
      
    $scope.addAliasRow = function (dto) {
    		var aliasName = dto.charAt(0).toUpperCase() + dto.substr(1).toLowerCase();
			if ($.inArray(aliasName, $scope.places.aliases) < 0) {
				$scope.places.aliases.push(aliasName);
				$scope.alias = null;
			}else {
				alert('Alias '+ aliasName +' exists.');
			}	
    };
    
    $scope.aliasValidation = function (value) {
        if(value != undefined && value != ''){
            return true;
        }else{ return false; }
    };
    
    $scope.deleteAliasRow = function (dto) {
        var indexAliasName = $scope.places.aliases.indexOf(dto);
        if (indexAliasName > -1) {
        	$scope.places.aliases.splice(indexAliasName, 1);
        }          
    };
    
    $scope.getLength = function(obj) {
        return Object.keys(obj).length;
    }

    }
  ]);

})(angular.module('aet.screens.places'));

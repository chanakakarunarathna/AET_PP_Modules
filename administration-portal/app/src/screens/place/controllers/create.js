(function(module) {

  module.controller('CreatePlacesController', ['$scope', 'placesService', 'Places', 'alertsService', '$state', 'taOptions','$rootScope', 'modalService',
    function($scope, placesService, Places, alertsService, $state, taOptions,$rootScope, modalService) {

      $scope.places = new Places();

      $scope.stripFormat = function (text) {
        return  text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

      $scope.latRegex = /^[-]?([1-8]?\d(\.\d+)?|90(\.0+)?)$/;
      $scope.longRegex = /^[-]?(180(\.0+)?|((1[0-7]\d)|([1-9]?\d))(\.\d+)?)$/;
      
      $scope.locationTypes = [	'administrative_area_level_1', 'administrative_area_level_2', 
    	  						'administrative_area_level_3', 'country','locality','political','postal_code',
    	  						'postal_town'];
      
      $scope.places.addrsComponents = [];
      
      $scope.places.aliases = [];

      $scope.subLevels = [{
        "key": "1",
        "value": "Sub Level 1"
      }, {
        "key": "2",
        "value": "Sub Level 2"
      }, {
        "key": "3",
        "value": "Sub Level 3"
      }];

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

      $scope.places.rulesCond = "<p><span>These forums are here to provide you with a friendly environment where you can discuss ideas, share honest feedback and have a constructive dialog. Our company wants to learn more about your needs, your experience and how to deliver the best customer experience. Community forums are at their best when participants treat their fellow participants with respect and courtesy. Therefore, we ask that you conduct yourself in a civilized manner when participating in these forums.</span><span></span></p><p><span>The guidelines and rules listed below explain what behavior is expected of you and what behavior you can expect from other community members. Note that the following guidelines are not exhaustive, and may not address all manner of offensive behavior. The company moderators shall have full discretion to address any behavior that they feel is inappropriate.</span><span></span></p><ul><li><span>Participants agree not to post anything abusive, rude, obscene, vulgar, slanderous, hateful, threatening, advertising or marketing related, or sexually-oriented. Material that suggests illegal activity or contains illegal content is also forbidden. Users posting any content that violates this code of conduct may receive a warning. Posts which violate any part of this Code of Conduct may be edited or removed. </span></li><li><span>Finally, you agree that company moderators have the right to remove, edit, move or close any post, topic or thread at any time they see fit following the guidelines outlined above.</span></li></ul>";

      $scope.submitPlaceForm = function() {
        $rootScope.loader = true;
        placesService.createPlaces($scope.places).then(function(data) {
          alertsService.addAlert({
            title: 'Success',
            message: 'Places "' + $scope.places.webId + '" successfully created',
            type: 'success',
            removeOnStateChange: 2
          });
          $rootScope.loader = false;
          $state.go('index.secured.places.search', {}, {
            reload: true
          });
        }, function(err) {
          $rootScope.loader = false;
          console.error('Could not create places', err);
        });

      }

      	$scope.preventPastingCharactersForLatLong = function(element, e){
	      	var val = e.originalEvent.clipboardData.getData('text/plain');
	      	if(element=='lat'){
	  			if (!($scope.latRegex.test(val))){
	  				e.preventDefault();
	  			} 
	  		}else{
		  		if (!($scope.longRegex.test(val))){
		            e.preventDefault();
		  		} 
	  		}
      	};

      $scope.manuallyAddedRowValidation = function () {
        var isManuallyAddingRecordButtonValidated = false;
        if (angular.isDefined($scope.manuallyAddedLongName) &&  $scope.manuallyAddedLongName != ''
        	&& angular.isDefined($scope.manuallyAddedShortName) && $scope.manuallyAddedShortName != ''
        	&& ($scope.manuallyAddedType.type.length>0)) {
          isManuallyAddingRecordButtonValidated = true;
        }
        return isManuallyAddingRecordButtonValidated;
      }

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
        $scope.manuallyAddedType.type = [];
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
        //if (newValue.length > oldValue.length) {
        if(newValue.type != undefined){
        	for (var a = 0; a < newValue.type.length; a++) {
        		$scope.addComponentType.push(newValue.type[a]);
        	}
        }
        //}
        if(oldValue.type != undefined){
            for (var a = 0; a < oldValue.type.length; a++) {
            	var idx = $scope.addComponentType.indexOf(oldValue.type[a]);
	  	      	if (idx > -1) {
	  	      		$scope.addComponentType.splice(idx, 1);
	  	      	}
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

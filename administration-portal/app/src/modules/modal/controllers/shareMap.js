(function (module) {

  module.controller('ShareMapController', ['$scope', '$stateParams', '$modalInstance', '$log', 'ShareMap', 'mapService', 'alertsService', '$rootScope',
    function ($scope, $stateParams, $modalInstance, $log, ShareMap, mapService, alertsService, $rootScope) {

      $scope.displayCloseBtn = false;
      $scope.shareMap = new ShareMap();
      $scope.shareMap.assignment.itemsToFocus = [];

      $scope.tabActivity = [{
        title: 'How it Works'
      }, {
        title: 'Assignment',
        disabled: false
      }, {
        title: 'Select Participants',
        disabled: false
      }, {
        title: 'Send',
        disabled: false
      }];

      //Participant List
      $scope.participants = $scope.partnerPlaceTeamMembers.concat($scope.partnerPlaceSMEs);


      //all items to be displayed in the dropdown
      $scope.items = [];

      //item object
      function Item(type, item, stage, touchpoint) {
        this.type = type;
        this.id = item.id;
        this.index = (
          type === 'stage' ? item.positionIndex + 1 :
            type === 'touchpoint' ? (stage.positionIndex + 1) + '.' + (item.positionIndex + 1) :
            (stage.positionIndex + 1) + '.' + (touchpoint.positionIndex + 1) + '.' + (item.positionIndex + 1)
        );
        this.title = this.index + ' - ' + item.title;

      }

      var item;

      //Focusing items
      $scope.itemsToFocus = [];

      //Action dropdown name
      var actionSelection = '';
      //Action number (eg:1.4.2)
      var itemNo = '';

      //To Set blank item for Action Selection Dropdown
      // $scope.actionItems[0] = {
      //   itemNo:undefined,
      //   actionSelection:undefined,
      //   actionId:undefined
      // };

      //set items to items array
      angular.forEach($scope.stages, function (stage, sKey) {
        if (stage.isAssignedToMission) {
          item = new Item('stage', stage);
          $scope.items.push(item);
        }
        angular.forEach(stage.touchpoints, function (touchpoint, tKey) {
          if (touchpoint.isAssignedToMission) {
            item = new Item('touchpoint', touchpoint, stage);
            $scope.items.push(item);
          }
          angular.forEach(touchpoint.actions, function (action, aKey) {
            if (action.isAssignedToMission) {
              item = new Item('action', action, stage, touchpoint);
              $scope.items.push(item);
            }
          })
        })
      });

      //Participant List check box selection function
      $scope.updateParticipants = function (isChecked, index) {
        if (isChecked === true) {
          $scope.shareMap.participants.push($scope.participants[index].id);
        } else {
          $scope.shareMap.participants.forEach(function (participant, selectedParticipantIndex) {
            if (participant == $scope.participants[index].id) {
              $scope.shareMap.participants.splice(selectedParticipantIndex, 1);
            }
          });
        }

      };

      //Creating initial focusing action item
      $scope.shareMap.assignment.itemsToFocus[0] = {
        itemLabel: null,
        focusOn: null,
        itemId: null
      };

      //Adding focusing action item
      $scope.addItem = function () {
        if ($scope.shareMap.assignment.itemsToFocus.length < 10) {
          $scope.shareMap.assignment.itemsToFocus.push({
            itemLabel: null,
            focusOn: null,
            itemId: null
          });
        }
      };

      //item dropdown change function
      $scope.updateItems = function (focusItem, selectedItem) {
        if (selectedItem) {
          focusItem.itemLabel = selectedItem.title;
          focusItem.itemType = selectedItem.type;
          focusItem.itemId = selectedItem.id;
        } else {
          focusItem.itemLabel = null;
          focusItem.itemId = null;
        }

      };


      //Next button function
      $scope.next = function (thisPageNo) {
        $scope.tabActivity[thisPageNo].active = true;
      };

      //Previous button function
      $scope.previous = function (thisPageNo) {
        $scope.tabActivity[thisPageNo].active = true;
      };

      //Focus item Delete function
      $scope.deleteFocusItem = function (index) {
        $scope.shareMap.assignment.itemsToFocus.splice(index, 1);
      };

      //Cancel button function
      $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
      };

      //Send button function
      $scope.send = function () {
        $scope.shareMap.dataType = $scope.dataType;
        //$log.debug("Share Map: ",$scope.shareMap);
        $rootScope.showLoader("Sending...");
        mapService.shareMap($scope.shareMap).then(function (data) {
          $rootScope.hideLoader();
          $scope.displayCloseBtn = true;

        }, function (err) {
          $rootScope.hideLoader();
          $scope.displayCloseBtn = false;
        });

      };


    }
  ]);

})(angular.module('aet.modals'));

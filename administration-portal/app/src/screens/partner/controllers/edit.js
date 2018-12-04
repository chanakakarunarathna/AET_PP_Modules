(function(module) {

  module.controller('EditPartnerController', ['$scope', 'partnerService', 'partner', 'alertsService', '$state', '$log', 'taOptions', '$rootScope', '$window', 'partnerDetails', 'adminUserService', 'userDetails',
    function($scope, partnerService, partner, alertsService, $state, $log, taOptions, $rootScope, $window, partnerDetails, adminUserService, userDetails) {

      $scope.stripFormat = function(text) {
        return text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

      $scope.partner = partner;
      $scope.stateDetails = [{
        "key": "AL",
        "value": "Alabama"
      }, {
        "key": "AK",
        "value": "Alaska"
      }, {
        "key": "AZ",
        "value": "Arizona"
      }, {
        "key": "AR",
        "value": "Arkansas"
      }, {
        "key": "CA",
        "value": "California"
      }, {
        "key": "CO",
        "value": "Colorado"
      }, {
        "key": "CT",
        "value": "Connecticut"
      }, {
        "key": "DE",
        "value": "Delaware"
      }, {
        "key": "FL",
        "value": "Florida"
      }, {
        "key": "GA",
        "value": "Georgia"
      }, {
        "key": "HI",
        "value": "Hawaii"
      }, {
        "key": "ID",
        "value": "Idaho"
      }, {
        "key": "IL",
        "value": "Illinois"
      }, {
        "key": "IN",
        "value": "Indiana"
      }, {
        "key": "IA",
        "value": "Iowa"
      }, {
        "key": "KS",
        "value": "Kansas"
      }, {
        "key": "KY",
        "value": "Kentucky"
      }, {
        "key": "LA",
        "value": "Louisiana"
      }, {
        "key": "ME",
        "value": "Maine"
      }, {
        "key": "MD",
        "value": "Maryland"
      }, {
        "key": "MA",
        "value": "Massachusetts"
      }, {
        "key": "MI",
        "value": "Michigan"
      }, {
        "key": "MN",
        "value": "Minnesota"
      }, {
        "key": "MS",
        "value": "Mississippi"
      }, {
        "key": "MO",
        "value": "Missouri"
      }, {
        "key": "MT",
        "value": "Montana"
      }, {
        "key": "NE",
        "value": "Nebraska"
      }, {
        "key": "NV",
        "value": "Nevada"
      }, {
        "key": "NH",
        "value": "New Hampshire"
      }, {
        "key": "NJ",
        "value": "New Jersey"
      }, {
        "key": "NM",
        "value": "New Mexico"
      }, {
        "key": "NY",
        "value": "New York"
      }, {
        "key": "NC",
        "value": "North Carolina"
      }, {
        "key": "ND",
        "value": "North Dakota"
      }, {
        "key": "OH",
        "value": "Ohio"
      }, {
        "key": "OK",
        "value": "Oklahoma"
      }, {
        "key": "OR",
        "value": "Oregon"
      }, {
        "key": "PA",
        "value": "Pennsylvania"
      }, {
        "key": "RI",
        "value": "Rhode Island"
      }, {
        "key": "SC",
        "value": "South Carolina"
      }, {
        "key": "SD",
        "value": "South Dakota"
      }, {
        "key": "TN",
        "value": "Tennessee"
      }, {
        "key": "TX",
        "value": "Texas"
      }, {
        "key": "UT",
        "value": "Utah"
      }, {
        "key": "VT",
        "value": "Vermont"
      }, {
        "key": "VA",
        "value": "Virginia"
      }, {
        "key": "WA",
        "value": "Washington"
      }, {
        "key": "WV",
        "value": "West Virginia"
      }, {
        "key": "WI",
        "value": "Wisconsin"
      }, {
        "key": "WY",
        "value": "Wyoming"
      }]

      $scope.partner.state = _.findWhere($scope.stateDetails, {
        "key": partner.state
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

      $scope.partner.subLevel = _.findWhere($scope.subLevels, {
        "key": partner.subLevel
      });

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


      $scope.submitPartnerForm = function(params) {
        $rootScope.loader = true;
        partnerService.editPartner(partner).then(function(data) {
            alertsService.addAlert({
              title: 'Success',
              message: 'Partner [' + partner.name + '] successfully updated',
              type: 'success',
              removeOnStateChange: 2
            });
            $rootScope.loader = false;
            $state.go('index.secured.partner.search', {}, {
              reload: true
            }).then(function() {
              adminUserService.findSelfAdminUser(userDetails.getUserId()).then(function(adminUser) {
                userDetails.setUser(adminUser);

                if (adminUser.isSuperAdmin) {
                  partnerService.searchPartners({
                    count: 0
                  }).then(function(partners) {
                    var cid = partnerDetails.getSelectedPartner().id;
                    partnerDetails.clear();
                    partnerDetails.setPartners(adminUser, partners);
                    partnerDetails.setSelectedPartner(cid);
                  }, function(err) {
                    $log.error(err);
                  });
                }
              });
            });
          },
          function(err) {
            $rootScope.loader = false;
            console.error('Could not edit partner', err);
          });

      }

    }
  ]);

})(angular.module('aet.screens.partner'));

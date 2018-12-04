(function(module) {

  module.controller('CreatePartnerController', ['$scope', 'partnerService', 'Partner', 'alertsService', '$state', 'taOptions','$rootScope',
    function($scope, partnerService, Partner, alertsService, $state, taOptions,$rootScope) {

      $scope.partner = new Partner();

      $scope.stripFormat = function (text) {
        return  text ? String(text).replace(/<[^>]+>/gm, '') : '';
      };

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

      $scope.partner.rulesCond = "<p><span>These forums are here to provide you with a friendly environment where you can discuss ideas, share honest feedback and have a constructive dialog. Our company wants to learn more about your needs, your experience and how to deliver the best customer experience. Community forums are at their best when participants treat their fellow participants with respect and courtesy. Therefore, we ask that you conduct yourself in a civilized manner when participating in these forums.</span><span></span></p><p><span>The guidelines and rules listed below explain what behavior is expected of you and what behavior you can expect from other community members. Note that the following guidelines are not exhaustive, and may not address all manner of offensive behavior. The company moderators shall have full discretion to address any behavior that they feel is inappropriate.</span><span></span></p><ul><li><span>Participants agree not to post anything abusive, rude, obscene, vulgar, slanderous, hateful, threatening, advertising or marketing related, or sexually-oriented. Material that suggests illegal activity or contains illegal content is also forbidden. Users posting any content that violates this code of conduct may receive a warning. Posts which violate any part of this Code of Conduct may be edited or removed. </span></li><li><span>Finally, you agree that company moderators have the right to remove, edit, move or close any post, topic or thread at any time they see fit following the guidelines outlined above.</span></li></ul>";

      $scope.submitPartnerForm = function() {
        $rootScope.loader = true;
        partnerService.createPartner($scope.partner).then(function(data) {
          alertsService.addAlert({
            title: 'Success',
            message: 'Partner "' + $scope.partner.name + '" successfully created',
            type: 'success',
            removeOnStateChange: 2
          });
          $rootScope.loader = false;
          $state.go('index.secured.partner.search', {}, {
            reload: true
          });
        }, function(err) {
          $rootScope.loader = false;
          console.error('Could not create partner', err);
        });

      }

    }
  ]);

})(angular.module('aet.screens.partner'));

(function(module) {

    /**
     * @ngdoc object
     * @name aet.customfilters.participationreport
     *
     * @description The filter is used to filter participation report dropdowns
     *
     *
     *
     */
    module.filter('checkParticipantType', function() {
        return function(inputs, checkType) {
            var discussionIndex = -1;
            for (i = 0; i < inputs.length; i++) {
              if (inputs[i].key === 'discussion') {
                  discussionIndex = i;
                  break;
              }
            }

            if (discussionIndex > -1 && (checkType === 'partnerplaceteam' || checkType === 'employee')) {
                inputs.splice(discussionIndex, 1);
            }
            return inputs;

        };
    });

    module.filter('checkItemType', function() {
        return function(inputs, checkType) {
            if (checkType === 'mission') {
                return inputs;
            } else if (checkType === 'discoverymission') {
                return inputs;
            } else {
                var output = [];
                output.push(inputs[0]);
                return output;
            }

        };
    });

})(angular.module('aet.customfilters.participationreport'));

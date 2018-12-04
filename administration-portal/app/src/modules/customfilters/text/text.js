(function(module) {

    /**
     * @ngdoc object
     * @name aet.customfilters.text
     *
     * @description The filter is used to capitalize the first letter in a word
     * Eg :- input : aeturnum lanka
     * capitalize First word only: Aeturnum lanka
     * capitalize:true All words: Aeturnum Lanka
     *
     */
    module.
    filter('capitalize', function() {
        return function(input, all) {
            var reg = (all) ? /([^\W_]+[^\s-]*) */g : /([^\W_]+[^\s-]*)/;
            return (!!input) ? input.replace(reg, function(txt) {
                return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
            }) : '';
        };
    });

    module.
    filter('shortenName', function() {
        return function(input) {
            if (!(angular.isUndefined(input) || input === null || input === "")) {
                var splitInput = input.replace('.', '').split(' ');
                input = splitInput[0].concat(' ').concat(splitInput[1].charAt(0)).concat('.');
            }
            return input;
        };
    });

})(angular.module('aet.customfilters.text'));

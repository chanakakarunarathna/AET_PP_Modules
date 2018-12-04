(function(module) {

    module.provider('appVersion', [function() {

        var version = '/* @echo appVersion */';

        return {
            $get: [function() {

                return function() {
                    return version;
                };

            }]
        };
    }]);

})(angular.module('aet.config'));

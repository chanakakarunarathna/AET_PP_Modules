(function(module) {

    module.provider('envConfig', ['__env', function(__env) {

        var bucket = __env.s3bucket;
        var defaultEmailSender = __env.defaultEmailSender;

        return {
            $get: [function(property) {

                return function(property) {
                    var value;
                    if (property === 'bucket') {
                        value = bucket;
                    } else if (property === 'defaultEmailSender') {
                        value = defaultEmailSender;
                    }
                    return value;
                };

            }]
        };
    }]);

})(angular.module('aet.config'));

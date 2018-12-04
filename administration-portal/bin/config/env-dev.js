(function (window) {
    window.__env = window.__env || {};

    // API url
    window.__env.apiBaseUrl = 'http://plac-AAPI-ONXTR35OXVI4.pgtjyzmdx9.us-east-1.elasticbeanstalk.com';

    // s3 bucket
    window.__env.s3bucket = 'placepassdev';
    window.__env.defaultEmailSender = 'researchdevstag@placepass.com';

    window.__env.protractor = {};
    window.__env.protractor.baseUrl = 'http://plac-APor-TQ6VUNRRR989.2g8g3xgr6x.us-east-1.elasticbeanstalk.com';

    // Whether or not to enable debug mode
    // Setting this to false will disable console output
    window.__env.enableDebug = true;
}(this));

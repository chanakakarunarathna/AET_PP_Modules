(function (window) {
    window.__env = window.__env || {};

    // API url
    window.__env.apiBaseUrl = 'https://admin-stage2-api.placepass.com';

    // s3 bucket
    window.__env.s3bucket = 'placepassdev';
    window.__env.defaultEmailSender = 'researchdevstag@placepass.com';

    window.__env.protractor = {};
    window.__env.protractor.baseUrl = 'https://admin-stage2-api.placepass.com';

    // Whether or not to enable debug mode
    // Setting this to false will disable console output
    window.__env.enableDebug = false;
}(this));

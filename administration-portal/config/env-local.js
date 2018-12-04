(function (window) {
    window.__env = window.__env || {};

    //connect
    window.__env.connect = {};
    window.__env.connect.hostname = 'localhost';
    window.__env.connect.port = '8888';
    window.__env.connect.basePath = 'build';

    // API url
    window.__env.apiBaseUrl = 'http://localhost:8080/admin-api';

    // s3 bucket
    window.__env.s3bucket = 'placepassdev';
    window.__env.defaultEmailSender = 'researchdevstag@placepass.com';

    window.__env.protractor = {};
    window.__env.protractor.baseUrl = 'researchdevstag@placepass.com';

    // Whether or not to enable debug mode
    // Setting this to false will disable console output
    window.__env.enableDebug = true;
}(this));
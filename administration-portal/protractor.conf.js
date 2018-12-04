var HtmlReporter = require('protractor-jasmine2-html-reporter');
var SpecReporter = require('jasmine-spec-reporter');

exports.config = {

    // The address of a running selenium server.
    seleniumAddress: 'http://localhost:4444/wd/hub',

    // Capabilities to be passed to the webdriver instance.
    //  capabilities: {
    //    'browserName': 'chrome' // 'internet explorer', "firefox", "safari"
    //  },
    // Override the timeout for webdriver to 10 seconds.
    allScriptsTimeout: 13000,

    //MultiCapabilities - Running tests on multiple browsers simultaniously
    multiCapabilities: [
        //      {
        //        'browserName': 'firefox'
        //      },
        {
            'browserName': 'chrome',
            'chromeOptions': {
                'args': ['--disable-extensions']
            }
        }
    ],

    // Spec patterns are relative to the current working directly when
    // protractor is called.
    //specs: ['specs/loginPageFeatureSpec.js','specs/forgotPWPage_01_Spec.js'],
    specs: ['protractor/tests/*/createProject_01_Spec.js'],

    //Command-line parameters
    params: {
        login: {
            userName: 'shihan.s@aeturnum.com',
            password: 'Asdf123$'
        }
    },

    // Spec patterns are relative to the location of the spec file. They may
    // include glob patterns.
    suites: {
        feature: 'tests/feature/  *Spec.js',
        ui: 'tests/feature/*Spec.js',
        temp: 'tests/loginPage/loginPage_05_Spec.js',
        loginPage: ['tests/loginPage/loginPage_*_Spec.js'],
        forgotPasswordPage: ['tests/forgotPasswordPage/forgotPWPage_*_Spec.js'],
        listProjectsPage: ['tests/listProjectsPage/listProjects_01_Spec.js'],
        createProjectPage: ['tests/createProjectPage/createProject_*_Spec.js']
    },

    // Options to be passed to Jasmine-node.
    jasmineNodeOpts: {
        // onComplete will be called just before the driver quits.
        onComplete: null,
        // If true, display spec names.
        isVerbose: true,
        // If true, print colors to the terminal.
        showColors: true,
        // If true, include stack traces in failures.
        includeStackTrace: false,
        // Default time to wait in ms before a test fails.
        defaultTimeoutInterval: 300000,
        print: function() {}
    },

    framework: 'jasmine2',

    onPrepare: function() {

        jasmine.getEnv().addReporter(
            new SpecReporter({
                displayStacktrace: 'all'
            })
        );

        jasmine.getEnv().addReporter(
            new HtmlReporter({
                savePath: 'protractor/results/',
                takeScreenshotsOnlyOnFailures: true
            })
        );
    }
};

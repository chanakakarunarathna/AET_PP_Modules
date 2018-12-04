module.exports = function(grunt) {

    //var configFile = './config/config-local.json';
    var configFile = './config/env-local.js';
    var environment = 'local';

    if (grunt.option('env')) {
        // grunt.log.writeln('config: '+grunt.option('config'));
        environment = grunt.option('env');
        configFile = './config/env-' + environment + '.js';
    }
    var configJson = require(configFile);
    var npmFile = './package.json';
    var npmJson = require(npmFile);

    if (!configJson.__env.connect) {
        configJson.__env.connect = {
            hostname: '',
            port: '',
            basePath: ''
        }
    }

    var devTasks = [
        'clean',
        'angular-builder:dev',
        'ngtemplates',
        'less',
        'preprocess',
        'copy:dev'
    ];

    var buildTasks = [
        'clean',
        'angular-builder:dev',
        'ngtemplates',
        'less',
        'preprocess',
        'copy:dev'
    ];

    var debugBuildTasks = [
        'clean',
        'angular-builder:dev:debug',
        'ngtemplates',
        'less',
        'preprocess',
        'copy:dev',
        'concat:dev'
    ];
    var testTasks = debugBuildTasks.concat(['concat:test']);

    // These are app dependencies that are not compatible with the angular-builder
    // plugin, or are not angular plugins. We will manually concat these with the
    // result of the angular-builder task
    var appDependencies = [
        'app/assets/js/jquery-2.1.4.min.js',
        'app/assets/js/jquery-ui.min.js',
        'app/bower_components/angular/angular.js',
        'app/bower_components/lodash/lodash.js',
        'app/bower_components/angular-ui-router/release/angular-ui-router.js',
        'app/bower_components/angular-ui-utils/ui-utils.js',
        'app/bower_components/angular-bootstrap/ui-bootstrap.js',
        'app/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
        'app/bower_components/**/angular-cookies.js',
        'app/bower_components/**/angular-local-storage.js',
        'app/bower_components/**/angular-animate.js',
        'app/bower_components/**/angularjs-color-picker.js',
        'app/bower_components/**/tinycolor.js',
        'app/bower_components/textAngular/dist/textAngular-rangy.min.js',
        'app/bower_components/textAngular/dist/textAngular-sanitize.min.js',
        'app/bower_components/textAngular/dist/textAngular.min.js',
        'app/bower_components/**/dist/select.min.js',
        'app/bower_components/checklist-model/checklist-model.js',
        'app/bower_components/**/sortable.js',
        'app/bower_components/angular-awesome-slider/dist/angular-awesome-slider.min.js',
        'app/bower_components/**/slider.js',
        'app/bower_components/chart.js/dist/Chart.js',
        'app/assets/js/FileSaver.js',
        'app/assets/js/jspdf.js',
        'app/assets/js/jspdf.plugin.addimage.js',
        'app/assets/js/jspdf.plugin.from_html.js',
        'app/assets/js/jspdf.plugin.ie_below_9_shim.js',
        'app/assets/js/saveSvgAsPng.js',
        'app/bower_components/**/angular-chart.js',
        'app/bower_components/**/html2canvas.js',
        'app/bower_components/**/canvas-to-blob.js',
        'app/bower_components/**/jquery.webui-popover.js',
        'app/bower_components/**/d3.js',
        'app/bower_components/**/nv.d3.js',
        'app/bower_components/**/angular-nvd3.js',
        'app/bower_components/**/highcharts-ng.js',
        'app/bower_components/videogular/videogular.js',
        'app/bower_components/videogular-controls/vg-controls.js',
        'app/bower_components/videogular-buffering/vg-buffering.js',
        'app/bower_components/exif-js/exif.js',
        'app/assets/js/angular-fix-image-orientation.js',
        'app/bower_components/commons-validator-js/commons-validator-js.js',
        'app/bower_components/angular-dynamic-number/release/dynamic-number.min.js',
        'app/bower_components/clipboard/dist/clipboard.js',
        'app/bower_components/ngclipboard/dist/ngclipboard.js',
        'app/bower_components/**/jquery.webui-popover.js',
        'app/bower_components/perfect-scrollbar/min/perfect-scrollbar.min.js',
        'app/bower_components/perfect-scrollbar/min/perfect-scrollbar.with-mousewheel.min.js',
        'app/bower_components/angular-perfect-scrollbar/src/angular-perfect-scrollbar.js',
        'node_modules/angular-pageslide-directive/dist/angular-pageslide-directive.min.js',
        'node_modules/angulargrid/angulargrid.js'
    ];

    var watchFiles = [
        'Gruntfile.js',
        'app/src/**/*.js',
        'app/src/**/*.html',
        'app/src/**/*.less',
        'app/index.tmpl'
    ];

    var testDependencies = [
        'node_modules/angular-mocks/angular-mocks.js'
    ];

    grunt.initConfig({
        clean: {
            dev: ['build', 'tmp']
        },

        copy: {
            dev: {
                files: [
                    // WEB-INF
                    {
                        expand: true,
                        cwd: 'app/WEB-INF/',
                        src: '**',
                        dest: 'build/WEB-INF/'
                    },
                    // html
                    {
                        expand: 'true',
                        cwd: 'app/',
                        src: 'index.html',
                        dest: 'build/'
                    },
                    //fonts
                    {
                        expand: true,
                        cwd: 'app/assets/fonts/',
                        src: '**',
                        dest: 'build/assets/fonts/'
                    },
                    // images
                    {
                        expand: true,
                        cwd: 'app/assets/images/',
                        src: '**',
                        dest: 'build/assets/images/'
                    },
                    // css
                    {
                        expand: true,
                        cwd: 'app/assets/css/',
                        src: 'videogular.css',
                        dest: 'build/assets/css/'
                    },
                    //
                    {
                        expand: true,
                        cwd: 'config/',
                        src: 'env-'+environment+'.js',
                        dest: 'build/assets/js/',
                        rename: function(dest, src) {
                            return dest + 'env.js';
                        }
                    },
                ]
            }
        },

        'angular-builder': {
            options: {
                mainModule: 'aet.bootstrap',
                releaseBuild: {
                    moduleVar: 'module'
                },
                debugBuild: {
                    rebaseDebugUrls: [{
                        match: /^src\//,
                        replaceWith: 'app/src/'
                    }]
                },
                renameModuleRefs: true,
                externalModules: [
                    'ui.router',
                    'ui.scrollfix',
                    'ui.mask',
                    'ngCookies',
                    'LocalStorageModule',
                    'ui.bootstrap',
                    'ngAnimate',
                    'color.picker',
                    'textAngular',
                    'ui.select',
                    'checklist-model',
                    'ui.sortable',
                    'angularAwesomeSlider',
                    'chart.js',
                    'nvd3',
                    'highcharts-ng',
                    'com.2fdevs.videogular',
                    'com.2fdevs.videogular.plugins.controls',
                    'com.2fdevs.videogular.plugins.buffering',
                    'cp.ng.fix-image-orientation',
                    'dynamicNumber',
                    'ngclipboard',
                    'perfect_scrollbar',
                    'pageslide-directive',
                    'angularGrid',
                    'ui.slider'
                ]
            },
            dev: {
                src: ['app/src/**/*.js'],
                dest: 'tmp/js/aet.js'
            }
        },

        ngtemplates: {
            options: {
                module: 'aet.bootstrap',
                htmlmin: {
                    collapseWhitespace: true,
                    collapseBooleanAttributes: true, // <input disabled="disabled"> => <input disabled>
                    removeAttributeQuotes: true, // Remove attribute quotes when it's safe to do so.
                    removeRedundantAttributes: false, // Remove redundant attributes like type="text/javascript".
                    removeEmptyAttributes: false, // Remove empty (or blank) attributes.
                    removeOptionalTags: false, // Some elements are allowed to have their tags omitted, like </td>.
                    removeEmptyElements: false // Experimental
                }
            },
            dev: {
                cwd: 'app/',
                src: 'src/**/*.html',
                dest: 'tmp/js/templates.js'
            }
        },

        concat: {
            options: {
                separator: ';\n'
            },
            dev: {
                files: {
                    'build/assets/js/dependencies.js': appDependencies,
                    'build/assets/js/aet.js': ['tmp/js/aet.processed.js', 'tmp/js/templates.js'],
                    'build/assets/css/aet.css': ['tmp/css/*.css']
                }
            },
            build: {
                files: {
                    'tmp/js/dependencies.js': appDependencies,
                    'tmp/js/aet.js': 'tmp/js/aet.processed.js'
                }
            },
            test: {
                files: {
                    'tmp/js/testdeps.js': testDependencies,
                    'test/build/aet.js': [
                        'build/assets/js/aet.js',
                        'tmp/js/testdeps.js'
                    ]
                }
            }
        },

        watch: {
            dev: {
                files: watchFiles,
                tasks: buildTasks.concat('concat:dev'),
                options: {
                    atBegin: true,
                    livereload: true
                },
                livereload: {
                    options: {
                        livereload: 'connect.options.livereload'
                    },
                    files: [
                        'build/assets/js/env.js',
                        'build/assets/js/dependencies.js',
                        'build/assets/js/aet.js',
                        'build/assets/css/aet.css'
                    ]
                }
            },
            debug: {
                files: watchFiles,
                tasks: debugBuildTasks,
                options: {
                    atBegin: true
                }
            }
        },

        connect: {
            options: {
                hostname: configJson.__env.connect.hostname,
                port: configJson.__env.connect.port,
                base: configJson.__env.connect.basePath,
                livereload: 35729
            },
            server: {
                middleware: function(connect, options) {
                    var optBase = (typeof options.base === 'string') ? [options.base] : options.base;
                    return [require('connect-modrewrite')(['!(\\..+)$ / [L]'])].concat(optBase.map(function(path) {
                        return connect.static(path);
                    }));
                }
            },
            livereload: {
                options: {
                    open: true,
                    middleware: function(connect, options) {
                        var optBase = (typeof options.base === 'string') ? [options.base] : options.base;
                        return [require('connect-modrewrite')(['!(\\..+)$ / [L]'])].concat(optBase.map(function(path) {
                            return connect.static(path);
                        }));
                    }
                }
            }
        },

        less: {
            dev: {
                files: {
                    'tmp/css/aet-less.css': 'app/src/styles/main.less'
                },
                options: {
                    plugins: [require('less-plugin-glob')]
                }
            }
        },

        uglify: {
            dep: {
                files: {
                    'build/assets/js/dependencies.js': 'tmp/js/dependencies.js',
                }
            },
            all: {
                options: {
                    mangle: false
                },
                files: {
                    'build/assets/js/aet.js': ['tmp/js/aet.js', 'tmp/js/templates.js']
                }
            }
        },

        cssmin: {
            options: {
                shorthandCompacting: false,
                roundingPrecision: -1,
                keepSpecialComments: 0
            },
            target: {
                files: {
                    'build/assets/css/aet.css': 'tmp/css/aet-less.css'
                }
            }
        },

        ngdocs: {
            options: {
                dest: 'docs',
                html5Mode: false,
                styles: ['docs_src/css/custom-docs.css'],
                navTemplate: 'docs_src/templates/custom-nav.html'
            },
            api: {
                src: ['app/src/**/*.js'],
                title: 'Docs'
            }
        },

        preprocess: {
            options: {
                context: {
                    NODE_ENV: environment,
                    cacheTimestamp: Date.now(),
                    appVersion: npmJson.version
                }
            },
            html: {
                src: 'app/index.tmpl',
                dest: 'build/index.html'
            },
            js: {
                src: 'tmp/js/aet.js',
                dest: 'tmp/js/aet.processed.js'
            }
        },

        protractor: {
            options: {
                configFile: "./protractor.conf.js", // Default config file
                keepAlive: true,
                noColor: false,
                debug: false,
            },
            dev: {
                options: {
                    configFile: "./protractor.conf.js", // Target-specific config file
                    args: {
                        specs: ["protractor/tests/CRUDAdminUser/editAdminUser.js"],
                        baseUrl: configJson.__env.protractor.baseUrl
                    }
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-angular-builder');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-angular-templates');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-ngdocs');
    grunt.loadNpmTasks('grunt-preprocess');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-protractor-runner');

    grunt.registerTask('debug', ['connect:server', 'watch:debug']);
    grunt.registerTask('dev', ['connect:livereload', 'watch:dev']);
    grunt.registerTask('test', testTasks);
    grunt.registerTask('build', buildTasks.concat(['concat:build', 'uglify:dep', 'uglify:all', 'cssmin']));
    grunt.registerTask('docs', buildTasks.concat(['ngdocs']));
    grunt.registerTask('test:p', ['build', 'connect:server', 'protractor']);
};

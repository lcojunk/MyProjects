//TODO: files aus gulpfile ziehen!
module.exports = function (config) {
    config.set({
        browsers: ['PhantomJS'],
        frameworks: ['jasmine','jasmine-matchers'],
        singleRun: true,
        reporters: ['progress', 'testng'],
        // the default configuration
        testngReporter: {
            outputFile: 'unit/testng-results.xml',
            suite: ''
        },
        files: [
            'node_modules/angular/angular.min.js',
            'node_modules/angular-mocks/angular-mocks.js',
            'node_modules/angular-strap/dist/angular-strap.min.js',
            'node_modules/angular-strap/dist/angular-strap.tpl.min.js',
            'node_modules/angularjs-slider/dist/rzslider.min.js',
            'node_modules/angularjs-slider/dist/rzslider.min.css',
            'node_modules/ng-tags-input/build/ng-tags-input.min.js',
            'node_modules/ng-tags-input/build/ng-tags-input.min.css',
            'node_modules/bootstrap/dist/css/bootstrap.css',
            'node_modules/underscore/underscore-min.js',
            'node_modules/angular-ui-router/release/angular-ui-router.min.js',
            'node_modules/angular-animate/angular-animate.min.js',
            'node_modules/angular-cookies/angular-cookies.min.js',
            'node_modules/angular-jwt/dist/angular-jwt.min.js',
            'node_modules/font-awesome/css/font-awesome.css',
            'node_modules/angular-loading-bar/build/loading-bar.min.css',
            'node_modules/angular-loading-bar/build/loading-bar.min.js',
            'node_modules/angular-utils-pagination/dirPagination.js',

            'app/**/*.module.js',
            'app/app.js',
            'app/**/*.js',

            'app/**/*.spec.js'
        ],
        angularFilesort: {
            whitelist: [
                'app/**/*.js'
            ]
        }
    });
};
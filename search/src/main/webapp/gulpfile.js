var gulp = require('gulp');
var inject = require('gulp-inject');
var gulpUtil = require('gulp-util');
var runSequence = require('run-sequence');
var del = require('del');
var wait = require('gulp-wait');
var angularFilesort = require('gulp-angular-filesort');
var streamSeries = require('stream-series');
var Server = require('karma').Server;

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

gulp.task('test', function (done) {
    new Server({
        configFile: __dirname + '/karma.conf.js',
        singleRun: true
    }, done).start();
});

var PATHS = {
    src: {
        js: 'app/**/*.js',
        spec: 'app/**/*.spec.js',
        html: 'app/**/*.html',
        css: 'app/**/*.css',
        images: ['app/**/*.jpg',
            'app/**/*.jpeg',
            'app/**/*.gif',
            'app/**/*.png']
    },
    lib: [
        'node_modules/angular/angular.js',
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
        'node_modules/angular-utils-pagination/dirPagination.js'
    ],
    customCopy: [
        {
            from: ['node_modules/bootstrap/dist/fonts/**', 'node_modules/font-awesome/fonts/**'],
            to: '../resources/static/fonts'
        }
    ],
    dest: {
        root: '../resources/static',
        lib: '../resources/static/lib'
    },
};

gulp.task('js', function () {
    return gulp.src([PATHS.src.js, '!' + PATHS.src.spec])
        .pipe(wait(200))
        .pipe(gulp.dest(PATHS.dest.root));
});

gulp.task('deleteJs', function () {
    del([PATHS.dest.root + '/**/**.js'], {force: true, read: false});
});

gulp.task('html', function () {
    return gulp.src(PATHS.src.html)
        .pipe(wait(200))
        .pipe(gulp.dest(PATHS.dest.root));
});

gulp.task('deleteHtml', function () {
    del([PATHS.dest.root + '/**/**.html'], {force: true, read: false});
});

gulp.task('css', function () {
    return gulp.src(PATHS.src.css)
        .pipe(wait(200))
        .pipe(gulp.dest(PATHS.dest.root));
});

gulp.task('deleteCss', function () {
    del([PATHS.dest.root + '/**/**.css'], {force: true, read: false})
});

gulp.task('images', function () {
    return gulp.src(PATHS.src.images)
        .pipe(wait(200))
        .pipe(gulp.dest(PATHS.dest.root));
});

gulp.task('deleteImages', function () {
    del([PATHS.dest.root + '/**/**.jpg',
        PATHS.dest.root + '/**/**.jpeg',
        PATHS.dest.root + '/**/**.gif',
        PATHS.dest.root + '/**/**.png'
    ], {force: true, read: false})
});

gulp.task('libs', function () {
    return gulp.src(PATHS.lib)
        .pipe(gulp.dest(PATHS.dest.lib));
});

gulp.task('customCopy', function () {
    for (var i = 0; i < PATHS.customCopy.length; i++) {
        gulp.src(PATHS.customCopy[i].from)
            .pipe(gulp.dest(PATHS.customCopy[i].to));
    }
});

gulp.task('inject', function () {
    gulpUtil.log('Inject js and css files into index.html.');

    var target = gulp.src(PATHS.dest.root + '/index.html');

    var angularJs = gulp.src([
        PATHS.dest.root + '/lib/**/angular.js',
        PATHS.dest.root + '/lib/**/angular.min.js'
    ], {read: false});
    var libJs = gulp.src([
        PATHS.dest.root + '/lib/**/*.js',
        '!' + PATHS.dest.root + '/lib/**/angular.js',
        '!' + PATHS.dest.root + '/lib/**/angular.min.js'
    ], {read: false});
    var sourcesJs = gulp.src([
        PATHS.dest.root + '/**/*.js',
        '!' + PATHS.dest.root + '/lib',
        '!' + PATHS.dest.root + '/lib/**',
        '!' + PATHS.dest.root + '/lib/**/angular.js',
        '!' + PATHS.dest.root + '/lib/**/angular.min.js'
    ]).pipe(angularFilesort());
    var sourcesCss = gulp.src(PATHS.dest.root + '/**/*.css', {read: false});

    target.pipe(inject(streamSeries(angularJs, libJs, sourcesJs), {relative: true}))
        .pipe(gulp.dest(PATHS.dest.root));
    target.pipe(inject(sourcesCss, {relative: true}))
        .pipe(gulp.dest(PATHS.dest.root));
});

//watch changes to copy files
gulp.task('watch', function () {
    gulpUtil.log('Watch html, js, css and image file changes.');

    var htmlWatcher = gulp.watch(PATHS.src.html, function (event) {
        if (event.type === 'deleted') {
            runSequence('deleteHtml', 'html', 'inject');
        } else {
            runSequence('html', 'inject');
        }
    });

    gulp.watch(PATHS.src.js, function (event) {
        if (event.type === 'added') {
            runSequence('js', 'inject');
        } else if (event.type === 'deleted') {
            runSequence('deleteJs', 'js', 'inject');
        } else {
            runSequence('js');
        }
    });

    gulp.watch(PATHS.src.css, function (event) {
        if (event.type === 'added') {
            runSequence('css', 'inject');
        } else if (event.type === 'deleted') {
            runSequence('deleteCss', 'css', 'inject');
        } else {
            runSequence('css');
        }
    });

    gulp.watch(PATHS.src.images, function (event) {
        if (event.type === 'deleted') {
            runSequence('deleteImages', 'images');
        } else {
            runSequence('images');
        }
    });
});

//copy files and inject after everything is copied 
gulp.task('build', function () {
    runSequence(['js', 'html', 'css', 'images', 'libs', 'customCopy'], 'inject', 'test');
});


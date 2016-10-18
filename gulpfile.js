"use strict";
let gulp = require('gulp');
let sass = require('gulp-sass');
let browserify = require('gulp-browserify');

gulp.task('default', ['html', 'scss', 'js', 'components']);

gulp.task('html', function () {
    return gulp.src(['index.html', 'login.html'])
        .pipe(gulp.dest('../src/main/resources/templates'));
});

gulp.task('components', function () {
    return gulp.src('components/*')
        .pipe(gulp.dest('../src/main/resources/public/components'));
});

gulp.task('scss', function () {
    return gulp.src('style.scss')
        .pipe(sass())
        .pipe(gulp.dest('../src/main/resources/public'));
});

gulp.task('js', function () {
    return gulp.src('app.js')
        .pipe(browserify())
        .pipe(gulp.dest('../src/main/resources/public'));
});

gulp.task('watch', function () {
    gulp.watch('index.html', ['html']);
    gulp.watch('login.html', ['html']);
    gulp.watch('components/*', ['components']);
    gulp.watch('*.scss', ['scss']);
    gulp.watch('app.js', ['js']);
});
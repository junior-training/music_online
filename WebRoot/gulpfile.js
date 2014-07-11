var gulp = require('gulp'),
    util = require('gulp-util'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat');

gulp.task('concat', function(){
    gulp.src('src/player/tpl/tListItem-xtpl.js')
    .pipe(uglify())
    .pipe(concat('tListItem-xtpl.min.js'))
    .pipe(gulp.dest('./build/player/tpl/'));
});

gulp.task('default', ['concat']);
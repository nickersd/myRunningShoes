module.exports = function(grunt) {

grunt.initConfig({
  uglify: {
    all_src : {
      options : {
        sourceMap : true,
        sourceMapName : 'sourceMap.map'
      },
      src : 'Webcontent/*.js',
      dest : 'build/mrs.all.min.js'
    }
  }
});

  // Load the plugin that provides the "uglify" task.
  grunt.loadNpmTasks('grunt-contrib-uglify');

  // Default task(s).
  grunt.registerTask('default', ['uglify']);
};


var avconv = require('avconv');
/*
 * "constructor" function implements all need variable initialization 
 */
function Converter (camera_files, screen_captures, log, output ) {

	// if camera_files is array just add it
	if ( typeof camera_files === '[object Array]' ) {
		this.camera_files = camera_files;
	}

	// if camera_files is a string, we create an array and add em to it.
	if ( typeof camera_files === '[object String]') {
		this.camera_files = [];
		this.camera_files.push(camera_files);
	}

	// output shouldnt be undefined, should be an not empty string
	this.output = (typeof output === 'string' && output != '') ? output : null;
	
	// screen captures should be an array > 0
	this.screen_captures = (typeof screen_captures === 'array') ? screen_captures : null; 
	
	// logs should be an json (object)
	this.logs = (typeof log === 'object') ? log : null;

	this.proc = new ffmpeg({
		// input source, required
		source: this.camera_files[0],
		// timout of the spawned ffmpeg sub-processes in seconds (optional, defaults to 30)
		timeout: 300,
		// default priority for all ffmpeg sub-processes (optional, defaults to 0 which is no priorization)
		priority: 0,
		// set a custom [winston](https://github.com/flatiron/winston) logging instance (optional, default null which will cause fluent-ffmpeg to spawn a winston console logger)
		logger: null,
		// completely disable logging (optional, defaults to false)
		nolog: false,

	});}

/*
 * Getters and Setters
 * TODO add type check
 */
Converter.prototype = {
	// Camera File Getters Setters

	get cameraFiles() {
		return this._camera_file;
	},
	
	set cameraFiles(path) {
		this._camera_file = path;
	},
	
	// screen captures 
	get screenCaptures() {
		return this._screen_captures;
	},
	
	set screenCaptures(screens) {
		this._screencaptures = screens;
	},
	
	// logs
	get logs() {
		return this._logs;
	},
	
	set logs(logs) {
		this._logs = logs;
	},
	
	// output
	get output() {
		return this._camera_file;
	},
	
	set output(path) {
		this._camera_file = path;
	}
}

Converter.prototype.addCameraFile = function(camera_file) {

	if ( typeof camera_files === '[object String]') {
		this.camera_files = [];
		this.camera_files.push(camera_files);
	}

}

Converter.prototype.addScreenshot = function(screenshot) {
	this.screenCaptures.push(screenshot);
}

/**
 *
 * creates a video file from screenshots
 *  
 *
 */
Converter.prototype.createVideoFromScreenshots = function(user, session, intervall) {
	var input = '/uploads/'+user+'/'+session+'/images/&d.jpg'; // 
	var output = '/uploads/'+user+'/'+session+'/images/result.mpg';

	// avconv parameters
	var params = [
		'-r', intervall,
		'-i', input,
		'-r', '25',
		'-s', '720x1280',
		'-vsync', 'cfr',
		output
	];

	var stream = avconv(params);

	stream.on('data', function(data) {
	    // handling
	});

	stream.on('progress', function(progress) {
	    /*
	    Progress is a floating number between 0 ... 1 that keeps you
	    informed about the current avconv conversion process.
	    */
	});

	stream.once('end', function(exitCode, signal) {
		console.log('fin ' + exitCode);
		// errorhandling
	});
}

/**
 *
 * Creates a video from captured videos 
 *
 */
Converter.prototype.createVideoFromVideos = function() {

}

/*
 * converts video files into one resultsvideo 
 * 
 */
Converter.prototype.createResults = function () {
	// TODO implementation


};

var fluent_ffmpeg = require('fluent-ffmpeg');

/*
 * "constructor" function implements all need variable initialization 
 */
function Converter (camera_file, screen_captures, log, output ) {
	// camera file/output shouldnt be undefined, should be an not empty string
	this.camera_file = (typeof camera_file === 'string' && camera_file != '') ? camera_file : null;
	this.output = (typeof output === 'string' && output != '') ? output : null;
	
	// screen captures should be an array > 0
	this.screen_captures = (typeof screen_captures === 'array') ? screen_captures : null; 
	
	// logs should be an json (object)
	this.logs = (typeof log === 'object') ? log : null;
}

/*
 * Getters and Setters
 * TODO add type check
 */
Converter.prototype = {
	// Camera File Getters Setters
	get cameraFile() {
		return this._camera_file;
	},
	
	set cameraFile(path) {
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

/*
 * converts video files into one resultsvideo 
 * 
 */
Converter.prototype.createResults = function () {
	// TODO implementation
};


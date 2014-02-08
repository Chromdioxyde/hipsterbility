
/**
 * Represents a Session
 * @constructor
 * @param {integer} id - session id
 * @param {string} name - name of the session
 * @param {bool} isActive - activity state
 * @param {integer} user - id of associated user
 * @param {array} captures - list of screen captures for the session
 * @param {array} logs - list of logfile for the session
 * @param {array} audios - list of audio files 
 * @param {array} todos - list of todos for the session
 * @param {string} result - path to result video
 */
function Session(id, name, isActive, user, captures, logs, audios, video, todos, result) {
	
	this.id = id;
	this.name = name;
	if (isActive == 1) ? this.isActive = true : this.isActive = false;
	this.user = user;
	this.captures = captures;
	this.logs = logs;
	this.audios = audios;
	this.video = video;
	this.todos = todos;
	this.result = result;
	
}

Session.prototype = {
	
	get id() {
		return this._id;
	},
	
	get name() {
		return this._name;
	},
	
	get isActive() {
		return this._isActive;
	},
	
	get user() {
		return this._user;
	},
	
	get captures() {
		return this._captures;
	},
	
	get logs() {
		return this._logs;
	},
	
	get audios() {
		return this._audios;
	},
	
	get video() {
		return this._video;
	},
	
	get todos() {
		return this._todos;
	},
	
	get result() {
		return this._result;
	}	
}
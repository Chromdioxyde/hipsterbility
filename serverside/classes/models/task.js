/** 
 * Represents a Task.
 * @constructor
 *
 * @param {integer} id - id of Task
 * @param {string} name - name of task
 * @param {bool} isDone - state of Task 
 */
function Task(id, name, isDone) {
	this.id = id;
	this.name = name;
	if (isDone == 1) ? this.isDone = true : this.isDone = false;
};

Task.prototype = {
	
	get id() {
		return this._id;
	},
	
	get name() {
		return this._name;
	},
	
	get isDone() {
		return this._isDone;
	}
	
}

/**
 * Represents a ToDo
 * @constructor
 * 
 * @param {integer} id - id of ToDo
 * @param {string} name - name of ToDo
 * @param {string} description - description of ToDo
 * @param {bool} isActive - state of ToDo 
 */
function ToDo(id, name, description, isActive) {
	this.id = id;
	this.name = name;
	this.description = description;
	if ( isActive == 1) ? this.isActive = true : this.isActive = false;
}

Task.prototype = {
	get id() {
		return this._id;
	},
	
	get name() {
		return this._name;
	},
	
	get description() {
		return this._description;
	},
	
	get isActive() {
		return this._isActive;
	}
}
var Query = require('../../classes/query');
var converter = require('../../classes/converter');

/**
 * creates a new session.
 * @param {object} req - Request object
 * @param {object} res - Response object
 */
exports.post = function(req, res) {
	
	// if username exists and if username is not empty
	if ( req.params.user_id != undefined && req.params.user_id != "") {
		// if sessionname exists and not empty
		if( req.body.sessionname != undefined && req.body.sessionname != "") {
			var query = new Query;
			var qstr = 'INSERT INTO sessions (name, users_idusers) VALUES ("'+ req.body.sessionname +'", '+ req.params.user_id +')';
			query.execute(qstr, '', function(rows){
				
				qstr = 'SELECT * FROM sessions WHERE idsessions = ' + rows.insertId;
				
				query.execute(qstr, '', function(rows){
					// send to user
					res.send(rows);
				});
			});
		}
	} else {	
		res.send("invalid parameters");
	}	
};

/**
 * get list of sesssions for user.
 * @param {object} req - request object.
 * @param {object} res - response object.
 */
exports.all = function(req, res) {
	var qstr = 'SELECT * FROM sessions WHERE users_idusers = ' + req.params.user_id;
	var query = new Query;
	
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 * get one session for user.
 * @param {object} req - request object.
 * @param {object} res - response object.
 */
exports.get = function(req, res) {
	
	var qstr = 'SELECT * FROM sessions WHERE users_idusers = ' + req.params.user_id + ' AND idsessions = ' + req.params.session_id;
	var query = new Query;
	
	query.execute(qstr, '', function(rows){
		res.send(rows);
	});
	
}

/**
 * updates state of a session.
 * @param {object} req - request object.
 * @param {object} res - response object.
 */
exports.finish = function(req, res) {
	
	// TODO implementation
	
	// updates activation state for session (1 to 0)
	
	// begin converting process
	
	// response to client
}




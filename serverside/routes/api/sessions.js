var Query = require('../../classes/query');
var converter = require('../../classes/converter');

/**
 * creates a new session.
 * @param {object} req - Request object
 * @param {object} res - Response object
 */
exports.post = function(req, res) {
	
	// if username exists and if username is not empty
	if ( req.body.username != undefined && req.body.username != "") {
		
		// if sessionname exists and not empty
		if( req.body.sessionname != undefined && req.body.sessionname != "") {
			
			var session = {
				id: 1, // TODO make it better!
				name: req.body.sessionname,
				user: req.body.username
			};
			
			// TODO write session to cache / db
			
			// send to user
			res.send(session);
			
		}
	} else {
		
		res.send("invalid parameters");
	}	
};

/**
 *
 */
exports.all = function(req, res) {
	
	var qstr = 'SELECT * FROM sessions WHERE users_idusers = ' + req.body.user_id;
	var query = new Query;
	
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 *
 */
exports.get = function(req, res) {
	
	var qstr = 'SELECT * FROM sessions WHERE users_idusers = ' + req.body.user_id + ' AND idsessions = ' + req.body.session_id;
	var query = new Query;
	
	query.execute(qstr, '', function(rows){
		res.send(rows);
	});
	
}

/**
 * updates state of a session.
 * 
 */
exports.finish = function(req, res) {
	
	// TODO implementation
	
	// updates activation state for session (1 to 0)
	
	// begin converting process
	
	// response to client
}




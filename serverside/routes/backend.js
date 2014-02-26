var Query = require('../classes/query');		

/**
 * renders the admin index page (after login)
 */
exports.index = function(req, res) {

	res.render('backend/admin', {type: 'backend', id_user: req.user.idusers});
	
}

/**
 * renders web view for all done tests
 * 
 */ 
exports.sessions = function(req, res) {
	
	// get all sessions 
	qstr = "SELECT * FROM sessions WHERE users_idusers = " + req.user.idusers;

	var query = new Query;

	query.execute(qstr, '', function(rows) {

		if (rows.length > 0) {
			console.log(rows);

			// render sessions backend
			res.render('backend/sessions', {type: 'backend', id_user: req.user.idusers, sessions: rows});
		} else {
			// error handling
		}
	});
}

exports.session = function(req, res) {

	console.log(req.params);

	res.render('backend/session', {type: 'backend', id_user: req.user.idusers, session: null });
}

/*`
 * renders web view for a new session
 */
exports.newSession = function(req, res) {
	
	// TODO implementation
}

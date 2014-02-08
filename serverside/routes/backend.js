
/**
 * renders the admin index page (after login)
 */
exports.index = function(req, res) {
	
	res.render('admin', {type: 'backend'});
	
}

/**
 * renders web view for all done tests
 * 
 */ 
exports.sessions = function(req, res) {
	
	res.render('backend/tests', {type: backend});
	
}

exports.session = function(req, res) {
	res.render('backend/test', {type: 'backend'});
}

/*`
 * renders web view for a new session
 */
exports.newSession = function(req, res) {
	
	// TODO implementation
}

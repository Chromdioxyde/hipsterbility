var Query = require('../../classes/query');
var converter = require('../../classes/converter');

/**
 * get list of sesssions for user which are not finished.
 * @param {object} req - request object.
 * @param {object} res - response object.
 * @returns {array} - array with objects containing id and name of session
 */
exports.all = function(req, res) {
	var qstr = 'SELECT idsessions, name FROM sessions WHERE users_idusers = ' + req.params.user_id ' AND finished = 0';

	var query = new Query;
	
	// TODO: session filter devices, apps 

	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 * get one session for user.
 * @param {object} req - request object.
 * @param {object} res - response object.
 *
 * @returns {object} - JSON object containing the session information
 */
exports.get = function(req, res) {
	
	console.log(req.params.session_id);
	
	var qstr = 'SELECT * FROM sessions WHERE users_idusers = ' + req.params.user_id + ' AND idsessions = ' + req.params.session_id;
	var query = new Query;
	
	query.execute(qstr, '', function(rows){
		res.send(rows);
	});
	
}

/**
 * updates a session.
 * @param {object} req - Request object
 * @param {object} res - Response object
 *
 */
exports.put = function(req, res) {
	
	// TODO check params 

	qstr = 'UPDATE sessions SET ';

	if( req.params.active != undefinded && req.params.active != '') { 
		// set active
		qstr += ' active = ' + req.params.active;
	} else if (req.params.name != undefined && req.params.name != '') { 
		// change name
		qstr += ' name = ' + req.params.name;
	} else if (req.params.finished != undefined) {
		// set finished
		
		// TODO  send 409 on finish not successfully
		// successfully is when logs and captures are already uploaded/added to the session. 
		qstr += ' name = ' + req.params.finished;
	}

	qstr += 'WHERE idsessions = ' req.params.session_id; 

	// TODO error check
	query.execute(qstr, '', function(rows){
		// send to user
		res.send(rows);
	});

	// if username exists and if username is not empty
// 	if ( req.params.user_id != undefined && req.params.user_id != "") {
// 		// if sessionname exists and not empty
// 		if( req.body.sessionname != undefined && req.body.sessionname != "") {
// 			var query = new Query;
// 			var qstr = 'INSERT INTO sessions (name, users_idusers) VALUES ("'+ req.body.sessionname +'", '+ req.params.user_id +')';
// 			query.execute(qstr, '', function(rows){
				
// 				qstr = 'SELECT * FROM sessions WHERE idsessions = ' + rows.insertId;
				
// 				query.execute(qstr, '', function(rows){
// 					// send to user
// 					res.send(rows);
// 				});
// 			});
// 		}
// 	} else {	
// 		res.send("invalid parameters");
// 	}	
};
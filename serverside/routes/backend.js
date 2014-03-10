var Query = require('../classes/query');		

/**
 * renders the admin index page (after login)
 */
exports.index = function(req, res) {

    // TODO check if user exists and redirect
	res.render('backend/admin', {type: 'backend', id_user: req.user.idusers});
	
}

/**
 * renders web view for all done tests
 * 
 */ 
exports.sessions = function(req, res) {
	
	// get all sessions 
	var qstr = "SELECT * FROM sessions WHERE users_idusers = " + req.user.idusers;

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

/**
 *
 * @param req
 * @param res
 */
exports.session = function(req, res) {

	console.log(req.params);


    var qstr = 'SELECT * FROM sessions WHERE users_idusers = '
        + req.params.user_id + ' AND idsessions = ' + req.params.id;

    var query = new Query;
    // get session

    query.execute(qstr, '', function (rows) {
        if( rows.length == 1) {

            var session = rows[0];
            qstr = 'SELECT * FROM todos WHERE todos.sessions_idsessions = ' + req.params.id;

            console.log(qstr);

            // get todos
            query.execute(qstr, '', function (rows) {
                console.log(rows);

                if ( rows.length > 0) {

                    var todos = rows;

                    res.render('backend/session', {type: 'backend', id_user: req.params.user_id, session: session, todos: todos});

                }
            });
        }
    });
}

/**
 *
 * @param req
 * @param res
 */
exports.newSession = function(req, res) {
	
	// TODO implementation
}

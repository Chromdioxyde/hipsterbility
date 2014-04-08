var Query = require('../classes/query');		
var S = require('string');

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

	query.execute(qstr, function(rows) {

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

    var qstr = 'SELECT * FROM sessions WHERE users_idusers = '
        + req.params.user_id + ' AND idsessions = ' + req.params.id;

    var query = new Query;
    // get session

    query.execute(qstr, function (rows) {
        if( rows.length == 1) {

            var session = rows[0];
            qstr = 'SELECT * FROM todos WHERE todos.sessions_idsessions = ' + req.params.id;

            console.log(qstr);

            // get todos
            query.execute(qstr, function (rows) {
                console.log(rows);

                if ( rows.length > 0) {

                    var todos = rows;

                    res.render('backend/session', {type: 'backend', id_user: req.params.user_id, session: session, todos: todos});

                }
            });
        }
    });
};

/**
 *
 * @param req
 * @param res
 */
exports.sessionPartial = function(req, res) {

}

/**
 *
 * @param req
 * @param res
 */
exports.newSession = function(req, res) {

    res.render('backend/newSession', {type: 'backend', id_user: req.params.user_id });

	// TODO implementation
};

/**
 * Inserts a new session
 * @param req
 * @param res
 */
exports.insertSession = function(req, res) {

    // validate inputs
    console.log(req.body);

    var sessionData = {
        name : '',
        description : '',
        todo1: {
            name: '',
            tasks: []
        },
        todo2: {
            name: '',
            tasks: []
        },
        todo3: {
        name: '',
            tasks: []
    }
    };

    var hasTodo = 0;

    if (req.body.inputname != '' ) {
        sessionData.name = req.body.inputname;
    }

    if (req.body.inputdescription != '') {
        sessionData.description = req.body.inputdescription;
    }

    console.log(sessionData);

    if (req.body.inputtodoname != '' ) {
        hasTodo = 1;

       // console.log(req.body[i]);

        sessionData.todo1.name = req.body.inputtodoname;

        for (var key in req.body) {
            if( S(key).startsWith('input_task_')) {
                var item = req.body[key];

                if( item != null && item != '') {
                    sessionData.todo1.tasks.push(item);
                }
            }
        }
    }

    if (req.body.inputtodoname2 != '' ) {
        hasTodo = 1;

        // console.log(req.body[i]);
        sessionData.todo2.name = req.body.inputtodoname2;


        for (var key in req.body) {
            if( S(key).startsWith('input_task2_')) {
                var item = req.body[key];

                if( item != null && item != '') {
                    sessionData.todo2.tasks.push(item);
                }
            }
        }
    }

    if (req.body.inputtodoname3 != '' ) {
        hasTodo = 1;

        // console.log(req.body[i]);

        sessionData.todo3.name = req.body.inputtodoname3;

        for (var key in req.body) {
            if( S(key).startsWith('input_task3_')) {
                var item = req.body[key];

                if( item != null && item != '') {
                    sessionData.todo3.tasks.push(item);
                }
            }
        }
    }

    console.log(sessionData);

    var qstr = 'INSERT INTO sessions ' +
        '(name, description, users_idusers, apps_idapps, devices_iddevices) VALUES ' +
        '("' + sessionData.name + '", "' + sessionData.description + '", '+ req.params.user_id + ', 1, 1)';

    console.log(qstr);

    var query = new Query;
    query.execute(qstr, function(rows) {
        var insertId = rows.insertId;
        var insertIsDone = 0;
        console.log('now inserting TODOS and Tasks');
        if (sessionData.todo1.tasks.length > 0) {

            qstr = 'INSERT INTO todos ' +
                '(name, sessions_idsessions) VALUES ' +
                '("' + sessionData.todo1.name + '",' + insertId + ')';
            console.log(qstr);
            query.execute(qstr, function(rows) {
                var insertTodoId = rows.insertId;

                qstr = 'INSERT INTO tasks ' +
                    '(name, todos_idtodos) VALUES';

                for (var i = 0; i < sessionData.todo1.tasks.length; i++) {
                    qstr += '("'+ sessionData.todo1.tasks[i] +'", '+ insertTodoId + ')';
                    if ( i < sessionData.todo1.tasks.length-1) {
                        qstr +=',';
                    }
                }


                console.log(qstr);
                query.execute(qstr, function(rows) {

                    insertIsDone++;

                });
            });
        } else {
            insertIsDone++;
        }

        if (sessionData.todo2.tasks.length > 0) {

            qstr = 'INSERT INTO todos ' +
                '(name, sessions_idsessions) VALUES ' +
                '("' + sessionData.todo2.name + '",' + insertId;

            query.execute(qstr, function(rows) {
                var insertTodoId = rows.insertId;

                qstr = 'INSERT INTO tasks ' +
                    '(name, todos_idtodos) VALUES ';

                for (var i = 0; i < sessionData.todo2.tasks.length; i++) {
                    qstr += '("'+ sessionData.todo2.tasks[i] +'", '+ insertTodoId + ')';
                    if ( i <= sessionData.todo2.tasks.length-1) {
                        qstr +=',';
                    }
                }



                query.execute(qstr, function(rows) {

                    insertIsDone++;

                });
            });
        } else {
            insertIsDone++;
        }

        if (sessionData.todo3.tasks.length > 0) {

            qstr = 'INSERT INTO todos ' +
                '(name, sessions_idsessions) VALUES ' +
                '("' + sessionData.todo3.name + '",' + insertId;

            query.execute(qstr, function(rows) {
                var insertTodoId = rows.insertId;

                qstr = 'INSERT INTO tasks ' +
                    '(name, todos_idtodos) VALUES ';

                for (var i = 0; i < sessionData.todo3.tasks.length; i++) {
                    qstr += '("'+ sessionData.todo3.tasks[i] +'", '+ insertTodoId + ')';
                    if ( i <= sessionData.todo3.tasks.length-1) {
                        qstr +=',';
                    }
                }

                query.execute(qstr, function(rows) {

                    insertIsDone++;

                });
            });
        } else {
            insertIsDone++;
        }

        function checkIsDone() {
            console.log('check is done: ' + insertIsDone);
            if ( insertIsDone == 3) {
                res.redirect('/'+req.params.user_id+'/admin/sessions/'+insertId);
            }
        }

        setInterval(checkIsDone,200);


    });

};

exports.sessionResult = function (req, res) {

    res.render('backend/result', {type: 'backend', id_user: req.params.user_id, id_session: req.params.session_id });

};

exports.sessionResultVideo = function (req, res) {

    var qstr = "SELECT * FROM "
};

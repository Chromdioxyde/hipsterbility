var Query = require('../../classes/query');

exports.all = function(req, res) {
	var query = new Query;
	qstr = 'SELECT * FROM todos WHERE sessions_idsessions = ' + req.params.session_id;

	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 *
 * @param req
 * @param res
 */
exports.get = function(req, res) {
	var query = new Query;
	qstr = 'SELECT * FROM todos WHERE sessions_idsessions = ' + req.params.session_id + ' AND idtodos =' + req.params.todo_id;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 *
 * @param req
 * @param res
 */
exports.post = function(req, res) {
	// TODO implementation

//    if( req.body.name == undefined || req.body.name == "") {
//
//    }
};

/**
 *
 * @param req
 * @param res
 */
exports.put = function(req, res) {

    if (req.body.active != undefined && req.body.active != "") {

        var qstr = "UPDATE todos SET active = " + req.body.active + ' WHERE sessions_idsessions = ' + req.params.session_id;

        var query = new Query;

        console.log(qstr);

        query.execute(qstr, '', function(rows) {

            console.log(rows);

            if( rows.affectedRows == 1) {

                res.status(200);
                res.send('ACK');
            }
        });
    }
};
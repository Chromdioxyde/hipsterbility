var Query = require('../../classes/query');

exports.all = function(req, res) {
	var query = new Query;

	var qstr = 'SELECT * FROM tasks WHERE todos_idtodos = ' + req.params.todo_id;

	console.log(qstr);

	query.execute(qstr, function(rows) {
        console.log(rows);

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
	var qstr = 'SELECT * FROM tasks WHERE todos_idtodos = ' + req.params.todo_id + ' AND idtasks =' + req.params.task_id;
	query.execute(qstr, function(rows) {
		res.send(rows);
	});
};

exports.post = function(req, res) {
	// TODO implementation
};

/**
 *
 * @param req
 * @param res
 */
exports.put = function(req, res) {

    if (req.body.isDone != undefined && req.body.isDone != "") {

        var qstr = "UPDATE tasks SET done = " + req.body.isDone + ' WHERE todos_idtodos = ' + req.params.todo_id;

        //console.log(qstr);

        var query = new Query;

        query.execute(qstr, function(rows) {

            console.log(rows);

            if( rows.affectedRows == 1) {

                res.status(200);
                res.send('ACK');
            }
        });
    }

};
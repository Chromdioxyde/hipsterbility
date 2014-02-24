var Query = require('../../classes/query');

exports.all = function(req, res) {
	var query = new Query;

	qstr = 'SELECT * FROM tasks WHERE todos_idtodos = ' + req.params.todo_id;

	console.log(qstr);

	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

exports.get = function(req, res) {
	var query = new Query;
	qstr = 'SELECT * FROM tasks WHERE todos_idtodos = ' + req.params.todo_id + ' AND idtasks =' + req.params.task_id;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

exports.post = function(req, res) {
	// TODO implementation
};

var Query = require('../../classes/query');

exports.all = function(req, res) {
	var query = new Query;
	qstr = 'SELECT idtodos, name, description, active FROM tasks WHERE sessions_idsessions = ' + req.params.session_id;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

exports.get = function(req, res) {
	var query = new Query;
	qstr = 'SELECT idtodos, name, description, active FROM tasks WHERE sessions_idsessions = ' + req.params.session_id + ' AND idtodos =' + req.params.id_logs;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

exports.post = function(req, res) {
	// TODO implementation
};

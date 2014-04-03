var Query = require('../../classes/query');

/**
 *
 */
exports.all = function(req, res) {
	var query = new Query;
	qstr = 'SELECT logs.idlogs, logs.file FROM logs WHERE sessions_idsessions = ' + req.params.session_id; 
	query.execute(qstr, function(rows){
		res.send(rows);
	}); 
};

/**
 *
 */
exports.get = function(req, res) {
	var query = new Query;
	qstr = 'SELECT logs.idlogs, logs.file FROM logs WHERE sessions_idsessions = ' + req.params.session_id + ' AND idlogs =' + req.params.id_logs;
	query.execute(qstr, function(rows) {
		res.send(rows);
	});
};

/**
 *
 */
exports.post = function(req, res) {
	
	// TODO implementation (how log is send?)
};
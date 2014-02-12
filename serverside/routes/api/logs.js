var Query = require('../../classes/query');

exports.all = function(req, res) {
	var query = new Query;
	
	qstr = 'SELECT logs.idlogs, logs.file FROM logs WHERE sessions_idsessions = ' + req.params.session_id; 
	console.log(qstr);
	
	query.execute(qstr, '', function(rows){
		res.send(rows);
	}); 
	
};

exports.get = function(req, res) {
	// TODO implementation
};

exports.post = function(req, res) {
	// TODO implementation
};

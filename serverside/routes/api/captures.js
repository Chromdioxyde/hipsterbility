var fs = require('fs');
var Query = require('../../classes/query');


exports.all = function (req, res) {
	var query = new Query;
	qstr = 'SELECT idcaptures, file FROM captures WHERE sessions_idsessions = ' + req.params.session_id; 
	query.execute(qstr, '', function(rows){
		res.send(rows);
	}); 
};

exports.get = function (req, res) {
	var query = new Query;
	qstr = 'SELECT idcaptures, file FROM captures WHERE sessions_idsessions = ' + req.params.session_id + ' AND idcaptures =' + req.params.id_logs;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 * 
 */
exports.post = function (req, res) {
	
	if (req.files.screenshot.type != 'image/png') {
		res.send("Nope!");
	}

	var tmp_path = req.files.screenshot.path;
	var target_path = 'uploads/'+req.params.user_id+'/'+req.params.session_id + '/captures/' + req.files.screenshot.name;

	fs.rename(tmp_path, target_path, function(err) {
		if (err) throw err;
		
		// delete the temporary file and send result as callback
		fs.unlink(tmp_path, function() {
			
			if (err) {
				throw err;
			} 

			var query = new Query;
			// execute query and send response in callback
			qstr = 'INSERT INTO captures (file, sessions_idsessions) VALUES ("' + target_path + '", '+ req.params.session_id +')';
			
			query.execute(qstr, '', function(rows) {
				res.send('screenshot uploaded to: ' + target_path); // which request string?
			});
		});
	});
};
var Query = require('../../classes/query');


/**
 *
 */
exports.all = function(req, res) {
	
	if( req.params.session_id != undefined && req.params.session_id != '') {

		var qstr = 'SELECT idaudios, name FROM audios WHERE session_id = '+ req.params.session_id;
		var query = new Query;

		query.execute(qstr, '', function(rows){
			res.send(rows);
		});
	}
};

/**
 *
 */
exports.get = function(req, res) {
	if( (req.params.session_id != undefined && req.params.session_id != '') &&
			(req.params.audio_id != undefined && req.params.audio_id != '')) {

		var qstr = 'SELECT * FROM audios WHERE session_id = '+ req.params.session_id + ' AND idaudios =' + req.params.audio_id;
		var query = new Query;

		query.execute(qstr, '', function(rows){
			res.send(rows);
		});
	}
};

/**
 * adds new audio file to session. 
 * 
 * 
 */
exports.post = function(req, res) {

	if ( (req.params.file != undefined && req.params.file != "") && 
			 (req.params.session_id != undefined && req.params.session_id != '') &&
			 (req.params.user_id != undefined && req.params.user_id != '') ) {

			var qstr = 'INSERT INTO audios (file, sessions_idsessions) VALUES (' + req.params.file + ', '+ req.params.session_id +')';
			var query = new Query;

			query.execute(qstr, '', function(rows){
				res.send(rows);
			});

	} else {
		// send ERROR state
		res.send('invalid parameters, could not read file');
	}
};
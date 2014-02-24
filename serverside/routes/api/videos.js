var fs = require('fs'); // file system access 
var Query = require('../../classes/query');

/**
 * gets a list of videos
 */
exports.all = function (req, res) {
	var query = new Query;
	qstr = 'SELECT idvideos, file FROM videos WHERE sessions_idsessions = ' + req.params.session_id;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 * returns json of a sepcific video file
 */
exports.get = function (req, res) {
	var query = new Query;
	qstr = 'SELECT idvideos, file FROM videos WHERE sessions_idsessions = ' + req.params.session_id + ' AND idvideos =' + req.params.id_logs;
	query.execute(qstr, '', function(rows) {
		res.send(rows);
	});
};

/**
 * route to upload files
 */
exports.post = function(req, res) {
	
	var tmp_path = req.files.video.path;
	var target_path = './uploads/1/' + req.files.video.name; // TODO: 1 is session_id and should be generated before
	
	fs.rename(tmp_path, target_path, function(err) {
		if (err) throw err;
		
		// delete the temporary file and send result as callback
		fs.unlink(tmp_path, function() {
			if (err) throw err;
				res.send('video uploaded to: ' + target_path + ', with ' + req.files.video.size + ' bytes');
			});
		});
};
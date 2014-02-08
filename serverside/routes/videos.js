var fs = require('fs'); // file system access 

/**
 * gets a list of videos
 */
exports.getAll = function (req, res) {
	
};

/**
 * returns json of a sepcific video file
 */
exports.get = function (req, res) {
	
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
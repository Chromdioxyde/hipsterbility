/**
 * creates a new session
 * req : Request object
 * res : Response object
 */
exports.post = function(req, res) {
	
	// if username exists and if username is not empty
	if ( req.body.username != undefined && req.body.username != "") {
		
		// if sessionname exists and not empty
		if( req.body.sessionname != undefined && req.body.sessionname != "") {
			
			var session = {
				id: 1, // TODO make it better!
				name: req.body.sessionname,
				user: req.body.username
			};
			
			// TODO write session to cache / db
			
			// send to user
			res.send(session);
			
		}
	} else {
		
		res.send("invalid parameters");
	}	
};






// index.js is used to render web pages

/*
 * GET index page.
 */
exports.index = function(req, res){
        res.render('index');
};

/**
 * Test function to test connection
 */
exports.pong = function(req, res) {
	
	res.send("pong");
};
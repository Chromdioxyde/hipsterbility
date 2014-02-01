/*
 * GET home page.
 */

exports.index = function(req, res){
        res.render('index', { megatrends: megatrends});
};


/*
 * GET admin panel
 */
exports.admin = function(req, res) {
        res.render('admin', {megatrends: rows });
}

/**
 * Test function to test connection
 */
exports.pong = function(req, res) {
	
	res.send("pong");
}
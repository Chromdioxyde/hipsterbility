function Query() {
	
  this.host = 'localhost';
  this.user = 'root';
  this.password = 'mmk2014'; 
  this.mysql = require('mysql');

  this.connection = this.connect();
	
}

Query.prototype.connect = function() {
    var connection = this.mysql.createConnection({
        host     : this.host,
        user     : this.user,
        password : this.password
    });

    connection.query('USE hipsterbility');
    return connection;
};

/**
 * 
 */
Query.prototype.disconnect = function() {
    this.connection.end;
}

/**
 * executes a given query. 
 * 
 * @param {string} query - The Query to execute.
 * @param {array} params - some more parameters.
 * @param {function} callback - callback to be executed. 
 */
Query.prototype.execute = function(query, params, callback) {
	
	var con = this.connect();
	
	con.query(query, function(err, rows) {
		if (!err) {
			callback(rows);
		}
	});
}

/**
 * testing function
 */
Query.prototype.test = function(callback) {
	var query = 'SELECT * FROM users';
	this.execute(query, '', callback);
}


module.exports = Query;
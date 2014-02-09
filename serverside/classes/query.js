
function Query() {
	
  this.host = 'localhost';
  this.user = 'root';
  this.password = 'r00t';
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
 */
Query.prototype.execute = function(query) {
	
	var con = this.connect();
	var result = con.query(query)
	
	this.disconnect();
	
	return result;
}


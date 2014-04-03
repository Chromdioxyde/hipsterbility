/**
 * Created by olivererxleben on 04.12.13.
 */
mysql = require('mysql');

function Query() {

}

/**
 *
 * @param qstr
 * @param callback
 */
Query.prototype.execute = function(qstr, callback) {

    var connection = mysql.createConnection({
        host     : 'localhost',
        user     : 'root',
        password : 'mmk2014',
        database : 'hipsterbility'
    });

    connection.query(qstr, function( err, rows) {
        if (err) {
            console.log(err); // TODO better
        } else {


            callback(rows);

        }

        connection.end();
    });

};

module.exports = Query;
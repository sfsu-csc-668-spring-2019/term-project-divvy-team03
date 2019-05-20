/**
 * @class
 * this class serves as setup to connect to mysql and create mysql pools.
 */
var mysql = require('promise-mysql');

const pool = mysql.createPool({
    host: 'localhost',
    user: 'root',
    password: 'AA23@xla@VbQ239S1',
    database: 'savvyAPP',
    connectionLimit: 10
});


module.exports = pool;
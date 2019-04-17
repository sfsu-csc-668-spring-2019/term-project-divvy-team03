/**
 * @class
 * this class serves as setup to connect to mysql and create mysql pools.
 */
const mysql = require('mysql')

const db = mysql.createPool({
    connectionLimit: 10,
    host: 'localhost',
    user: 'root',
    password: '23@NsX44A062xMa',
    database: 'CSC_668'

})


module.exports = db
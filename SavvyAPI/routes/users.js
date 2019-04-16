/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

const express = require('express')
const db = require('../config/db.config.js');
const bcrypt = require("bcryptjs");
const bodyParser = require("body-parser");
const cors = require('cors');
const cookieParser = require('cookie-parser');

//create router
const router = express.Router()
    /** bodyParser.urlencoded(options)
     * Parses the text as URL encoded data (which is how browsers tend to send form data from regular forms set to POST)
     * and exposes the resulting object (containing the keys and values) on req.body
     */
router.use(bodyParser.urlencoded({
    extended: false
}));
router.use(bodyParser.json());
router.use(cookieParser());
router.use(cors());
/**
 * @return
 * 
 */

router.get('/login', (req, res) => {
    const login = req.query.userName
    const password = req.query.password
    console.log("DATA: " + login + " " + password)
    const queryString = "SELECT userName, user_password From user_info WHERE userName = ? "
    db.query(queryString, [login, password], (err, rows, fields) => {
        if (err) {
            console.log("Failed to query for users: " + err)
            res.sendStatus(500)
            return
        }

        let hashPassword = rows[0].user_password

        //compares hashed password with the one sent in
        bcrypt.compare(password, hashPassword, (err, res2) => {
            res.json(res2)
        })
    })
})


module.exports = router
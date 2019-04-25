/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

const express = require('express')
const db = require('../config/db.config.js');
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
router.post('/reg', (req, res) => {
    username = req.body.username
    email = req.body.email
    password = req.body.password
    first_name = req.body.first_name
    last_name = req.body.last_name
    city = req.body.city
    description = req.body.description
    var createProfile = "INSERT INTO User(username, email, password, first_name, last_name, city, description, profImage) VALUES (?, ?, ?, ?, ?, ?, ?)"
    db.query(createProfile, [username, email, password, first_name, last_name, city, description], (err, result) => {
        if (err) {
            console.log("failed to insert new image: " + err)
            res.sendStatus(500)
            return
        } else {
            console.log('inserted data');
            res.sendStatus(200)
        }
    });
});

router.get('/login', (req, res) => {
    //user id becomes the id number we want to look for 
    const username = req.body.username;
    const password = req.body.password;
    //this code selcts all the user information by user id 
    const queryString = "SELECT username password FROM user WHERE username = ?"
    db.query(queryString, [username, password], (err, rows, fields) => {
        if (err) {
            console.log("failed to query for users " + err)
            res.sendStatus(500)
            return
        }
        const users = rows.map((row) => {
                return { username: row.username, email: row.email, first_name: row.first_name, last_name: row.last_name, city: row.city, description: row.description }
            })
            //this line displays the user first name ans last name
        res.json(users)
    })
})


module.exports = router
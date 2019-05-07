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
router.post('/newListing', (req, res) => {
    const name = req.body.name
    const status = "true"
    const description = req.body.description
    const username = req.body.username
    var createProfile = "INSERT INTO Listing(name, status, description, owner) VALUES (?, ?, ?, ?)"
    db.query(createProfile, [name, status, description, username], (err, result) => {
        if (err) {
            console.log("failed to insert new listing " + err)
            res.sendStatus(500)
            return
        } else {
            console.log('inserted data');
            res.sendStatus(200)
        }
    });
});

router.get('/search', (req, res) => {
    const like = req.query.like
    var createquery = "SELECT * FROM Listing WHERE name LIKE ? AND status = true"
    db.query(createquery, ['%' + like + '%'], (err, rows, fields) => {
        if (err) {
            console.log(err)
            res.sendStatus(500)
            return
        } else {
            res.json(rows)
        }
    })
})

router.get('/getbyowner', (req, res) => {
    const username = req.query.username
    var createquery = "SELECT * FROM Listing WHERE owner = ?"
    db.query(createquery, [username], (err, rows, fields) => {
        if (err) {
            console.log(err)
            res.sendStatus(500)
            return
        } else {
            res.json(rows)
        }
    })
})


module.exports = router
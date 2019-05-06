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
     * and exposes the resulting object (containing the keys and values) on request.body
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
router.post('/newListing', (request, response) => {
    try {
      const listing = Listing.create(request.body)

      response.json(listing);
    } catch( error ) {
      response.sendStatus(500);
    }
    // const name = request.body.name
    // const status = "true"
    // const description = request.body.description
    // const username = request.body.username
    // var createProfile = "INSERT INTO Listing(listing_name, listing_status, listing_desc, listing_owner) VALUES (?, ?, ?, ?)"
    // db.query(createProfile, [name, status, description, username], (err, result) => {
    //     if (err) {
    //         console.log("failed to insert new listing " + err)
    //         res.sendStatus(500)
    //         return
    //     } else {
    //         console.log('inserted data');
    //         res.sendStatus(200)
    //     }
    // });
});

router.get('/search', ({ body: { like = ''}}, response) => {
    try {
      const results = Listing.find(like);
      response.json(results);
    } catch( error ) {
      response.sendStatus(500);
    }
    // const like = request.body.like
    // var createquery = "SELECT * FROM Listing WHERE listing_name LIKE ? AND listing_status = true"
    // db.query(createquery, ['%' + like + '%'], (err, rows, fields) => {
    //     if (err) {
    //         console.log(err)
    //         res.sendStatus(500)
    //         return
    //     } else {
    //         console.log(result)
    //         res.json(rows)
    //     }
    // })
})

router.get('/getbyowner', (request, res) => {
    const username = request.body.username
    var createquery = "SELECT * FROM Listing WHERE listing_owner = ?"
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
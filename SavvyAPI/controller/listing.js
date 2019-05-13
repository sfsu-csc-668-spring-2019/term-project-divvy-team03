/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import cookieParser from 'cookie-parser'
import Listing from "../models/listing_model";
import ListingG from "../models/listing_group_model";

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
    var time = new Date();
    var group_id = time.getTime();
    var username = request.body.username;
    var groupValues = [group_id, username];
    var values = Object.keys(request.body).map(function(key) { return request.body[key]; });
    values.push(group_id);
    Listing.create(values, function(err, result) {
        if (err) {
            response.sendStatus(500);
        } else {
            ListingG.create(groupValues, function(err, result) {
                if (err) {
                    response.send('Failed to create a listing group');
                } else {
                    response.sendStatus(result);
                }
            })
        }
    });
});

router.get('/search', (request, response) => {
    Listing.find(request.query.like, 1, function(err, result) {
        if (err) {
            response.send(err);
        } else {
            response.send(result);
        }
    });
})

router.get('/searchbyusername', (request, response) => {
    const username = request.query.username
    Listing.find(username, 2, function(err, result) {
        if (err) {
            response.sendStatus(500);
        } else {
            response.send(result);
        }
    })
})


module.exports = router
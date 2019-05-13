/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import cookieParser from 'cookie-parser'
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
router.post('/newMember', (request, response) => {
    var values = Object.keys(request.body).map(function(key) { return request.body[key]; });
    console.log(values)
    ListingG.create(values, function(err, result) {
        if (err) {
            response.sendStatus(500);
        } else {
            response.sendStatus(result);
        }
    });
});

router.get('/getGroupByListingID', (request, response) => {
    ListingG.find(request.query.groupID, 1, function(err, result) {
        if (err) {
            response.send(err);
        } else {
            response.send(result);
        }
    });
})

router.get('/searchGroupByUsername', (request, response) => {
    const username = request.query.username
    ListingG.find(username, 2, function(err, result) {
        if (err) {
            response.sendStatus(500);
        } else {
            response.send(result);
        }
    })
})


module.exports = router
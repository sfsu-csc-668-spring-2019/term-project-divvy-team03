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
router.post('/newListing', ({ body }, response) => {
    const time = (new Date())
    const group_id = time.getTime();
    const { username } = body;

    // const values = Object.keys(body).reduce( 
    //     (memo, key) => [ ...memo, body[key] ],
    //     [group_id]
    // );

    const values = [
        ...Object.keys(body).map(key => body[key]),
        group_id
    ];
    Listing.create(values)
        .then(_ => {
            ListingG.create([group_id, username])
        }, error => response.sendStatus(422))
        .then(row => {
            response.sendStatus(200)
        }, error => response.sendStatus(422));
});

router.get('/search', (request, response) => {
    Listing.find(request.query.like, 1)
        .then(rows => {
            response.send(rows)
        }, error => response.sendStatus(422));
});

router.get('/searchbyusername', (request, response) => {
    Listing.find(request.query.username, 2)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})

router.get('/searchbyid', (request, response) => {
    Listing.find(request.query.id, 3)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})


module.exports = router

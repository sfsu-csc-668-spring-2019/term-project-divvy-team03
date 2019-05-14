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
    const group_id = (new Date()).getTime();
    const { username } = body;

    // const values = Object.keys(body).reduce( 
    //     (memo, key) => [ ...memo, body[key] ],
    //     [group_id]
    // );

    const values = [
        ...Object.keys(body).map(key => body[key]),
        group_id
    ];

    const error = () => response.send('Failed to create a listing group').sendStatus(500);
    const createListingGroup = () => ListingG.create( [group_id, username] )
    const createRoom = () => Room.create([group_id, 'true']);
    const respond = responseCode => response.sendState(responseCode);

    Listing.create(values)
        .then( createListingGroup )
        .then( createRoom )
        .then( respond )
        .catch( error );
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
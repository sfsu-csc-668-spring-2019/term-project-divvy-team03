/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import cookieParser from 'cookie-parser'
import Rating from "../models/rating_model";

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
router.post('/rate', ({ body }, response) => {
    const values = [
        ...Object.keys(body).map(key => body[key]),
    ];
    Rating.create(values)
        .then(_ => {
            response.sendStatus(200)
        }, _ => response.sendStatus(422));

});

router.get('/searchRatingByUserRated', (request, response) => {
    Rating.find(request.query.username, 1)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})

router.get('/searchRatingByUserRating', (request, response) => {
    Rating.find(request.query.username, 2)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})



module.exports = router
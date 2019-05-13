/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import cookieParser from 'cookie-parser'
import User from "../models/user_model";

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
router.post('/reg', (request, response) => {
    var values = Object.keys(request.body).map(function(key) { return request.body[key]; });
    User.create(values, function(err, result) {
        if (err) {
            response.sendStatus(500);
        } else {
            response.sendStatus(result);
        }
    });

});

router.get('/login', (request, response) => {
    var values = Object.keys(request.query).map(function(key) { return request.query[key]; });
    User.find(values, 1, function(err, result) {
        if (err) {
            response.send(err);
        } else {
            response.send(result);
        }
    });
})


module.exports = router
/**
 * @module
 * this class will be used to handle get and post request for 
 * user related info. 
 */

import express from 'express'
import bodyParser from 'body-parser'
import cors from 'cors'
import cookieParser from 'cookie-parser'
import Chat from "../models/chat_model";
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

router.get('/chatbyID', (request, response) => {
    Chat.find(request.query.id, 1)
        .then(rows => {
            response.send(rows)
        }, error => response.sendStatus(422));
});

router.get('/chatbyuser', (request, response) => {
    Chat.find(request.query.username, 2)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})


module.exports = router

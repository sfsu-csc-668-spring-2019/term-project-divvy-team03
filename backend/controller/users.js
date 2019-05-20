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
import db from '../config/db.config.js';
import multer from 'multer';
import path from 'path';
import sharp from 'sharp';
import fs from 'fs';


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

// let upload = multer({ storage: storage });

// function to encode file data to base64 encoded string
function base64_encode(file) {
    // read binary data
    var bitmap = fs.readFileSync(file);
    // convert binary data to base64 encoded string
    return new Buffer(bitmap).toString('base64');
}

// function to create file from base64 encoded string
function base64_decode(base64str, file) {
    // create buffer object from base64 encoded string, it is important to tell the constructor that the string is base64 encoded
    var bitmap = new Buffer(base64str, 'base64');
    // write buffer to file
    fs.writeFileSync(file, bitmap);
    console.log('******** File created from base64 encoded string ********');
}

// convert image to base64 encoded string
//var base64str = base64_encode('kitten.jpg');
//console.log(base64str);
// convert base64 string back to image 
//base64_decode(base64str, '../uploads/');

/*router.post('/image', (request, response) => {
    const image = request.body.file;
    const name = request.body.name
    base64_decode(image, __basedir + '/uploads/' + name + '.png');
    resizeBy.send("done");
})*/

router.post('/reg', (request, response) => {
    const profImage = request.body.username + '.png';
    const image = request.body.file
    base64_decode(image, __basedir + '/uploads/' + profImage);
    const values = [request.body.username, request.body.email, request.body.password, request.body.first_name,
        request.body.last_name, request.body.city, request.body.descr, profImage
    ];
    User.create(values)
        .then(_ => {
            response.sendStatus(200)
        }, _ => response.sendStatus(422));

});

router.get('/login', ({ query }, response) => {
    const values = [
        ...Object.keys(query).map(key => query[key]),
    ];
    User.find(values, 1)
        .then(rows => {
            response.send(rows)
        }, _ => response.sendStatus(422));
})


module.exports = router
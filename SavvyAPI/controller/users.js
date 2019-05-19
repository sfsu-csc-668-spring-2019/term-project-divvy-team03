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
// /**
//  * @return
//  * 
//  */

// /**
//  * @method
//  * stores the image to uploads and creates metadata of the image
//  */
// let storage = multer.diskStorage({
//     destination: (req, file, cb) => {
//         cb(null, __basedir + '/uploads/')
//     },
//     filename: (req, file, cb) => {
//         cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
//     }
// });


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
var base64str = base64_encode('kitten.jpg');
console.log(base64str);
// convert base64 string back to image 
base64_decode(base64str, 'copy.jpg');

router.post('/reg', upload.single('profile'), (request, response) => {
    const file_name = request.file.filename
    const values = [
        ...Object.keys(request.body).map(key => request.body[key]),
        file_name
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
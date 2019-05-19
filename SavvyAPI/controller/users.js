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
/**
 * @return
 * 
 */

/**
 * @method
 * stores the image to uploads and creates metadata of the image
 */
let storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, __basedir + '/uploads/')
    },
    filename: (req, file, cb) => {
        cb(null, file.fieldname + '-' + Date.now() + path.extname(file.originalname));
    }
});


let upload = multer({ storage: storage });

/**
 * @method
 * @param
 * this method receives an image via post request and then saves it to 
 * uploads folder and also saves the image info to the image table in Mysql
 */
// router.post('/profileImage', upload.single('image'), (req, res) => {
//     /* message: "Error! in image upload."
//      if (!req.file) {
//          console.log("No file received");
//          message = "Error! in image upload."
//          res.render('index', { message: message, status: 'danger' });

//      } else {*/
//     const username = req.body.username
//     const email = req.body.email
//     const password = req.body.password
//     const first_name = req.body.first_name
//     const last_name = req.body.last_name
//     const city = req.body.city
//     const descr = req.body.descr
//     const profImage = req.file.image


//     var insertimage = "INSERT INTO user (username, email, password, first_name, last_name, city, descr, profImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
//     db.query(insertimage, [username, email, password, first_name, last_name, city, descr, profImage], (err, result) => {
//         if (err) {
//             console.log("failed to insert new image: " + err)
//             res.sendStatus(500)
//         } else {
//             console.log('inserted data');
//         }
//     });
// })

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
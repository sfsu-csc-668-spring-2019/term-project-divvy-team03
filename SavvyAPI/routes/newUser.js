/**
 * @class
 * this class will be use to handle image request 
 */
module.exports = function(app) {
    const multer = require('multer');
    const path = require('path')
    const db = require('../config/db.config.js');
    global.fileName = ''
    global.imageRename = ''

    /**
     * @method
     * stores the image to uploads and creates metadata of the image
     */
    let storage = multer.diskStorage({
        destination: (req, file, cb) => {
            cb(null, __basedir + '/images/')
        },
        filename: (req, file, cb) => {
            fileName = file.fieldname + '-' + Date.now() + path.extname(file.originalname)
            imageRename = file.fieldname + '-' + Date.now()
            cb(null, fileName)
        }
    });

    let upload = multer({ storage: storage });

    /**
     * @method
     * @param
     * this method receives an image via post request and then saves it to 
     * uploads folder and also saves the image info to the image table in Mysql
     */
    app.post('/register', upload.single('image'), (req, res) => {
        var username = ""
        var email = ""
        var password = ""
        var first_name = ""
        var last_name = ""
        var city = ""
        var description = ""
        var profileImage = ""

        if (!req.file) {
            username = req.body.username
            email = req.body.email
            password = req.body.password
            first_name = req.body.first_name
            last_name = req.body.last_name
            city = req.body.city
            description = req.body.description
            profileImage = "NULL"
        } else {
            username = req.body.username
            email = req.body.email
            password = req.body.password
            first_name = req.body.first_name
            last_name = req.body.last_name
            city = req.body.city
            description = req.body.description
            profileImage = req.file.fileName
        }
        var createProfile = "INSERT INTO User(username, email, password, first_name, last_name, city, description, profileImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        db.query(createProfile, [username, email, password, first_name, last_name, city, description, profileImage], (err, result) => {
            if (err) {
                console.log("failed to insert new image: " + err)
                res.sendStatus(500)
                return
            } else {
                console.log('inserted data');
                res.sendStatus(200)
            }
        });
    });
}
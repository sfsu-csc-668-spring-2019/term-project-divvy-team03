//load our app server using express
import express from 'express'
var fs = require('fs');
import cors from 'cors'
const app = express()
import morgan from 'morgan'
import http from 'http'
const server = http.createServer(app),
    io = require('socket.io').listen(server);
import bodyParser from 'body-parser'
app.use(cors())

app.use(bodyParser.urlencoded({ extended: false }))

//important.. this line creates a connection to use static files such as html saved in the
//folder public

app.use(express.static('./images'));

app.use(morgan('short'))

global.__basedir = __dirname;


//create router
//this is important because it creates a connection to the users folder and runs the users.js folder
const user = require('./controller/users.js')
app.use(user)

const listings = require('./controller/listing.js')
app.use(listings)

const listingGroup = require('./controller/listing_group')
app.use(listingGroup)

const rating = require('./controller/rating')
app.use(rating)

function image(data){
//const json = Object.keys(data).forEach((key) => (data[key] == '') && delete data[key]);
  //  var jsonContent = JSON.parse(json);
const path = `${__dirname}/images/test.png`;
var result = Buffer.from([data], 'base64')
fs.writeFile(path, result, (error) => { console.log(error) })
console.log(result)
}

//socket config
io.on('connection', (socket) => {
    console.log('user connected')
    var room = socket.handshake.query.room;
    socket.join(room);

    socket.on('join', function(userNickname) {
//        console.log(userNickname + " : has joined " + socket.room);
        io.to(room).emit('user joined the chat', userNickname + " : has joined the chat ");
    })

    socket.on('messagedetection', (senderNickname, messageContent) => {
        //log the message in console 
  //      console.log(senderNickname + " : " + messageContent)
            //create a message object 
//console.log(messageContent)
//console.log(senderNickname)
        image(senderNickname)
        let message = { "message": messageContent, "senderNickname": senderNickname }
//        const json = Object.keys(data).forEach((key) => (data[key] == '') && delete messageContent[key]);
        //image(json.image)
            // send the message to all users including the sender  using io.emit() 
        io.to(room).emit('message', message)
       // console.log("this is the message " + message);
    })


   // socket.on('imagedetection', (senderNickname, messageContent) => {
        //log the message in console 
     //   console.log(senderNickname + " : " + messageContent)
            //create a message object 
//console.log(messageContent)
//console.log(senderNickname)
       // image(messageContent)
       // let message = { "message": messageContent, "senderNickname": senderNickname }
//        const json = Object.keys(data).forEach((key) => (data[key] == '') && delete messageContent[key]);
        //image(json.image)
            // send the message to all users including the sender  using io.emit() 
       // io.to(room).emit('message', message)
       // console.log("this is the message " + message);
//})
    socket.on('disconnect', function() {
        console.log('user has left ')
        io.to(room).emit("userdisconnect", ' user has left')

    })
})

app.get('/', function(req, res) {
    res.send('SAVVY API');
});


//for local uncomment this 
server.listen(3000, () => {
    console.log("Server is up and listening on 3000...")
})

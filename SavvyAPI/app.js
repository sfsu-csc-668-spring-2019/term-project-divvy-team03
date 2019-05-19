//load our app server using express
import express from 'express'
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

//socket config
io.on('connection', (socket) => {
    var room = '';
    console.log (room + "here room")
    room =  socket.handshake['query']['room'];
    console.log("room=" + room)
    socket.join(room);

    socket.on('join', function(userNickname) {
        console.log(userNickname + " : has joined " + socket.room);
        io.to(room).emit('user joined the chat', userNickname + " : has joined the chat ");
    })

    socket.on('messagedetection', (senderNickname, messageContent) => {
        console.log(senderNickname + " : " + messageContent)
        let message = { "message": messageContent, "senderNickname": senderNickname }
        io.to(room).emit('message', message)
    })

    socket.on('disconnect', function() {
        console.log('user has left ')
        socket.leave(room)
        io.to(room).emit("userdisconnect", ' user has left')
        room = '';
        console.log(room + "after logout")
    })
})

app.get('/', function(req, res) {
    res.send('SAVVY API');
});


//for local uncomment this 
server.listen(3000, () => {
    console.log("Server is up and listening on 3000...")
})

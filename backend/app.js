//load our app server using express
import express from 'express'
import cors from 'cors'
const app = express()
import morgan from 'morgan'
import Chat from "../SavvyAPI/models/chat_model";
import http from 'http'
const server = http.createServer(app),
    io = require('socket.io').listen(server);
import bodyParser from 'body-parser'
app.use(cors())

app.use(bodyParser.urlencoded({ extended: false }))

//important.. this line creates a connection to use static files such as html saved in the
//folder public

app.use(express.static('./uploads'));

app.use(morgan('short'))

global.__basedir = __dirname;


//create router
//this is important because it creates a connection to the users folder and runs the users.js folder
const user = require('./controller/users.js')
app.use(user)

const listings = require('./controller/listing.js')
app.use(listings)

const chat = require('./controller/chat.js')
app.use(chat)

const listingGroup = require('./controller/listing_group')
app.use(listingGroup)

const rating = require('./controller/rating')
app.use(rating)


//socket config
io.on('connection', (socket) => {
    var room = '';
    room = socket.handshake['query']['room'];

    socket.join(room, () => {
        let rooms = Object.keys(socket.rooms);
        console.log(rooms);
        io.to('room').emit('a new user has joined the room');
    });

    /*    socket.on('join', function(userNickname) {
            console.log(userNickname + " : has joined " + socket.room);
            io.to(room).emit('user joined the chat', userNickname + " : has joined the chat ");
        })*/

    socket.on('messagedetection', (message) => {
        //console.log(JSON.stringify(message))
        Chat.create([room, message["senderNickname"], message["message"]])
            //let message = { "message": messageContent, "senderNickname": senderNickname }
        io.to(room).emit('message', message)
    })

    socket.on('disconnect', function() {
        console.log('user has left ')
        socket.removeAllListeners(room);
        socket.leave(room)
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
//load our app server using express
const express = require('express')
var cors = require('cors')
const app = express()
const morgan = require('morgan')
http = require('http'),
    server = http.createServer(app),
    io = require('socket.io').listen(server);
const bodyParser = require('body-parser')

app.use(cors())

app.use(bodyParser.urlencoded({ extended: false }))

//important.. this line creates a connection to use static files such as html saved in the
//folder public

app.use(express.static('./images'));

app.use(morgan('short'))

global.__basedir = __dirname;


//create router
//this is important because it creates a connection to the users folder and runs the users.js folder
const router = require('./routes/users.js')
app.use(router)

const listings = require('./routes/listing.js')
app.use(listings)

//socket config
io.on('connection', (socket) => {

    console.log('user connected')

    socket.on('join', function(userNickname) {

        console.log(userNickname + " : has joined the chat ");

        socket.broadcast.emit('userjoinedthechat', userNickname + " : has joined the chat ");
    })


    socket.on('messagedetection', (senderNickname, messageContent) => {

        //log the message in console 

        console.log(senderNickname + " : " + messageContent)

        //create a message object 

        let message = { "message": messageContent, "senderNickname": senderNickname }

        // send the message to all users including the sender  using io.emit() 

        io.emit('message', message)

    })

    socket.on('disconnect', function() {

        //console.log(userNickname + ' has left ')

        socket.broadcast.emit("userdisconnect", ' user has left')




    })

})

app.get('/', function(req, res) {
    res.send('SAVVY API');
});


//for local uncomment this 
server.listen(3000, () => {
    console.log("Server is up and listening on 3003...")
})
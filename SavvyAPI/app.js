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
var nsp = io.of('main');
nsp.on('connection', function(socekt) {
    SocketIO.on('message', function(msg) {
        console.log(msg);
        nsp.emit('message', msg)
    })
})

nsp.on('connection', function(socket) {
    socket.on('disconnect', function() {
        nsp.emit('new message', '*disconnected');
    });
});

//localhost:3003
app.get('/', function(req, res) {
    res.send('SAVVY API');
});

//for server uncomment this
//app.listen(8080, '172.31.90.190');

//for local uncomment this 
app.listen(3003, () => {
    console.log("Server is up and listening on 3003...")
})
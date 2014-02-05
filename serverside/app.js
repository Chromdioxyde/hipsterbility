/**
 * Module dependencies.
 */
// own classes
var converter = require('./classes/converter');

// express related libs
var express = require('express');

var routes = require('./routes'); // web routes

var videos = require('./routes/videos'); // videos API
var captures = require('./routes/captures'); // captures / screenshot API
var sessions = require('./routes/sessions'); // session ID API
var logs = require('./routes/logs'); // logfiles API

var http = require('http');
var path = require('path');
var app = express();

// environments config
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.use(express.bodyParser());
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));


// Web routes --------------------------------------------

// TODO implement

// -------------------------------------------------------

// simple test route
app.get('/ping/?', routes.pong);

// session API
// app.get('/sessions/?', sessions.getAll); // get list of sessions
// app.post('/sessions/?', session.post); // new session
// 
// app.get('/sessions/:id/?', sessions.get); // get specific session

// videos API
app.get('/:session_id/videos/?', videos.getAll); // get list of videos 
app.post('/:session_id/videos/?', videos.post); // post new video

app.get('/:session_id/videos/:which/?', videos.get); // get specific video
//app.put('/videos/:which/?', videos.update);

// captures API
// app.get('/captures/?', captures.getAll); // get list of captures
// app.post('/captures/?', captures.post); // post a new capture
// 
// app.get('/captures/:which/?', captures.get); // get specific capture

// logs API
// app.get('/:session_id/logs/?', logs.getAll); // get list of logs
// app.post('/:session_id/logs/?', logs.post); // post a new log
// 
// app.get('/:session_id/logs/:id/?', logs.get); // get specific log
// -------------------------------------------------------

// server take off
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
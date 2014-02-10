/**
 * Module dependencies.
 */
// express related libs
var express = require('express');

var backend = require('./routes/backend'); // admin web routes
var frontend = require('./routes/frontend'); // frontend web routes

var videos = require('./routes/api/videos'); // videos API
var audios = require('./routes/api/audios'); // microphone / audio API
var captures = require('./routes/api/captures'); // captures / screenshot API
var sessions = require('./routes/api/sessions'); // session ID API
var logs = require('./routes/api/logs'); // logfiles API
var todos = require('./routes/api/todos'); // todos API
var tasks = require('./routes/api/tasks'); // tasks API

var http = require('http');
var path = require('path');
var app = express();

// environments config
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.bodyParser());
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// Web routes --------------------------------------------

// front pages
app.get('/?', frontend.index);
app.get('/about/?', frontend.about);
app.get('/login/?', frontend.login);


// admin pages
app.get('/:user_id/admin', backend.index);
app.get('/:user_id/admin/sessions', backend.sessions);
app.get('/:user_id/admin/sessions/:id/?', backend.session);

// test page TODO delete route!
app.get('/test/db_test/?', function(req, res) {
	
	var Query = require('./classes/query');
	var q = new Query();
	
	q.test(function(rows) {
			res.send(rows);
	});
});

// error pages
app.use(function(req, res) {
	res.render(404, {title: '404: Page not found', type: 'frontend'});
});

// -------------------------------------------------------

// simple test route
app.get('/ping/?', frontend.pong);

// session API
app.get('/:user_id/sessions/?', sessions.all); // get list of sessions
app.post('/:user_id/sessions/?', sessions.post); // new session
app.get('/:user_id/sessions/:session_id/?', sessions.get); // get specific session
app.put('/:user_id/sessions/fin/?', sessions.finish); // finishes a session and video converting will start

// videos API
app.get('/:user_id/:session_id/videos/?', videos.all); // get list of videos 
app.post('/:user_id/:session_id/videos/?', videos.post); // post new video
 
app.get('/:user_id/:session_id/videos/:id/?', videos.get); // get specific video
// //app.put('/videos/:id/?', videos.update);

// captures API
app.get('/:user_id/:session_id/captures/?', captures.all); // get list of captures
app.post('/:user_id/:session_id/captures/?', captures.post); // post a new capture

app.get('/:user_id/:session_id/captures/:id/?', captures.get); // get specific capture

// logs API
app.get('/:user_id/:session_id/:session_id/logs/?', logs.all); // get list of logs
app.post('/:user_id/:session_id/:session_id/logs/?', logs.post); // post a new log
 
app.get('/:user_id/:session_id/:session_id/logs/:id/?', logs.get); // get specific log

// audio API
app.get('/:user_id/:session_id/audio/?', audios.all); // get list of audio files
app.post('/:user_id/:session_id/audio/?', audios.post); // post new audio

app.get('/:user_id/:session_id/audio/?', audios.get); // get list of audio files

// todo API
app.get('/:user_id/:session_id/todos/?', todos.all);
app.post('/:user_id/:session_id/todos/?', todos.post);

app.get('/:user_id/:session_id/todos/:id/?', todos.get);

// task API
app.get('/:user_id/:session_id/todos/:id/tasks/?', tasks.all);
app.post('/:user_id/:session_id/todos/:id/tasks/?', tasks.post);

app.get('/:user_id/:session_id/todos/:id/tasks/:id/?', tasks.get);

// -------------------------------------------------------

// server take off
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
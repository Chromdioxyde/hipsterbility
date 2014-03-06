/**
 * Module dependencies.
 */
// express related libs
var express = require('express');
var Query = require('./classes/query');

var backend = require('./routes/backend'); // admin web routes
var frontend = require('./routes/frontend'); // frontend web routes

var videos = require('./routes/api/videos'); // videos API
//var audio = require('./routes/api/audio'); // microphone / audio API
var captures = require('./routes/api/captures'); // captures / screenshot API
var sessions = require('./routes/api/sessions'); // session ID API
var logs = require('./routes/api/logs'); // logfiles API
var todos = require('./routes/api/todos'); // todos API
var tasks = require('./routes/api/tasks'); // tasks API

var http = require('http');
var path = require('path');
var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var app = express();

// environments config
app.set(process.env.TMPDIR = './uploads/tmp');
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');
app.use(express.bodyParser());
app.use(express.cookieParser()); 
app.use(express.session({secret: 'hipsterbility'}));
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.json());
app.use(express.urlencoded());
app.use(express.methodOverride());

// passport authentication
app.use(passport.initialize());
app.use(passport.session());

app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));

// passport strategy setup
passport.use(new LocalStrategy(
	function(username, password, done) {
        console.log(username);

		// implement authentication mechanism
		// TODO: fail checks before building query
		var qstr = "SELECT * FROM users WHERE name = '" + username + "'";

		var query = new Query;

		query.execute(qstr, '', function(rows) {

			if (rows.length == 1) {
				done(null, rows[0]);
			} else {
				done(null, false);
			}
		});
	}
));

// automagic function
passport.serializeUser(function(user, done) {
  done(null, user.idusers);
});

// automagic function
passport.deserializeUser(function(id, done) {
  var qstr = "SELECT idusers FROM users WHERE idusers = " + id;

		var query = new Query;

		query.execute(qstr, '', function(rows) {
			done(null, rows[0]);
		});
});

// Web routes --------------------------------------------

// front pages
app.get('/?', frontend.index);
app.get('/about/?', frontend.about);
app.get('/login/?', frontend.login);

/**
 *
 */
app.get('/web-auth/?', function(req, res) {

    if (req.user != undefined) {
        res.redirect('/'+ req.user.idusers + '/admin');
    } else {
        res.redirect('/login');
    }

});

/**
 * validates a login using passportjs
 * TODO create wrappe class for passport (auth handler e.g)
 */
app.post('/web-auth/?', passport.authenticate('local', {successRedirect: '/web-auth' ,failureRedirect: '/login'}));

/**
 * development authentication mode for mobile app
 */
app.post('/auth/?', function (req, res) {

   console.log(req.body);

   if( req.body.name != undefined && req.body.name != '') {

       var qstr = 'SELECT idusers AS "id" FROM users WHERE name = "' + req.body.name + '" AND password = "' + req.body.password + '" AND active = 1';

       console.log(qstr);

       var query = new Query;

       query.execute(qstr, '', function(rows) {
           console.log(rows);

           if (rows.length == 1) {
               res.status(200);
               res.send(rows[0]);
           } else {
               res.status(400);
               res.send('Du weißt nicht wer du bist, oder du kennst dein Passwort nicht!'); // TODO sth more useful
           }
       });
   } else {
       res.status(400);
       res.send('Du weißt nicht wer du bist, oder du kennst dein Passwort nicht!');
   }
});

// admin pages
app.get('/:user_id/admin', backend.index);
app.get('/:user_id/admin/sessions/?', backend.sessions);
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
app.get('/ping/?', function(req, res) {
    res.status(200);
    res.send('OK');
});



// session API
app.get('/:user_id/sessions/?', sessions.all); // get list of sessions
app.get('/:user_id/sessions/:session_id/?', sessions.get); // get specific session
app.put('/:user_id/sessions/:session_id/?', sessions.put); // finishes a session and video converting will start

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
app.get('/:user_id/:session_id/logs/?', logs.all); // get list of logs
app.post('/:user_id/:session_id/logs/?', logs.post); // post a new log
 
app.get('/:user_id/:session_id/logs/:id_logs/?', logs.get); // get specific log

// audio API
// app.get('/:user_id/:session_id/audio/?', audio.all); // get list of audio files
// app.post('/:user_id/:session_id/audio/?', audio.post); // post new audio0

//app.get('/:user_id/:session_id/audio/:audio_id?', audio.get); // get list of audio files

// todos API
app.get('/:user_id/:session_id/todos/?', todos.all);
app.post('/:user_id/:session_id/todos/?', todos.post);

app.get('/:user_id/:session_id/todos/:todo_id/?', todos.get);
app.put('/:user_id/:session_id/todos/:todo_id/?', todos.put);

// task API
app.get('/:user_id/:session_id/todos/:todo_id/tasks/?', tasks.all);
app.post('/:user_id/:session_id/todos/:todo_id/tasks/?', tasks.post);

app.put('/:user_id/:session_id/todos/:todo_id/tasks/:task_id/?', tasks.put);

app.get('/:user_id/:session_id/todos/:todo_id/tasks/:task_id/?', tasks.get);

// users API
app.post('/users/')

// -------------------------------------------------------

// server take off
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
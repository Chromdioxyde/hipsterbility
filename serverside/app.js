/**
 * Module dependencies.
 */
// own classes
var converter = require('./classes/converter');

// express related libs
var express = require('express');

var routes = require('./routes');
var videos = require('./routes/videos');
var captures = require('./routes/captures');

var http = require('http');
var path = require('path');
var app = express();

// environments config
app.set('port', process.env.PORT || 3000);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
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

// videos API
app.get('/videos/?', videos.getAll);
app.post('/videos/?', videos.post);

app.get('/videos/:which/?', videos.get);
//app.put('/videos/:which/?', videos.update);

// captures API
app.get('/captures/?', captures.getAll);
app.post('/captures/?', captures.post); // post a new one

app.get('/captures/:which/?', captures.get);

// logs API

// -------------------------------------------------------

// server take off
http.createServer(app).listen(app.get('port'), function(){
  console.log('Express server listening on port ' + app.get('port'));
});
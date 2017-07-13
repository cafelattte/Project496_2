var express = require('express');
var app = require('app');
var bodyParser = require('bodyParser');
var mongoose = require('mongoose');

var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function(){
	console.log("Connected to mongod server");
});

mongoose.connect('mongodb://localhost/mongodb_tutorial');

var Contact = require('./models/contact');

app.use(bodyParser.urlencoded({ extended: true}));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

var router = require('./routes')(app, Contact);

var server = app.listen(port, function () {
	console.log("Express server has started on port " + port)
});


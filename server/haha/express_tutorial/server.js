var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var fs = require('fs');

app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);


var server = app.listen(8080, function(){
	console.log('Express server has started on port 8080');
});

app.use(express.static('public'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
	
var router = require('./router/main')(app, fs);


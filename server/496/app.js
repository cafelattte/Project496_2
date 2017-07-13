var express = require("express");
var app = express();
var bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({ extended: true}));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;

// for connecting mongodb
var mongoose = require("mongoose");
var db = mongoose.connection;
db.on("error", console.error.bind(console, "connection error:"));
db.once("open", function () {
	console.log("mongo db connection OK.");
});
mongoose.connect("mongodb://localhost/test");

// DEFINE MODEL
var Lecture = require("./models/lecture.js");

// CONFIGURE ROUTER
var router = require("./routes/mongo.js")(app, Lecture);

var server = app.listen(port, function () {
	console.log("Example app listening on port" + port + "!");
})


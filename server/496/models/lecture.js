var mongoose = require('mongoose');

// Define Schemas
var lectureSchema = new mongoose.Schema({
	Depart: String,
	Code: String,
	Course_no: String,
	Course_type: String,
	Course_title: String,
	Credits: Number,
	AU: Number,
	Grades: String,
	Repeat: String
});

module.exports = mongoose.model("lecture", lectureSchema);


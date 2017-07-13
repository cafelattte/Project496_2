module.exports = function(app, Lecture) {
	var parse = require("../parse.js");

	var express = require("express");
	var router = express.Router();

	var Lecture = require("../models/lecture");

	// Get All Lectures
	app.get("/lectures/list", function(req, res) {
		Lecture.find(function(err, lec) {
			if (err) return res.status(500).send({error: "database failure"});
			if (lec.length == 0) return res.status(404).json({error: "lecture not found"});
			res.json(lec);
		})
	});
	
	// Get Current Lectures
	app.get("/lectures/all", function(req, res) {
		Lecture.find({"Grades": "null"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			if (lec.length == 0) return res.status(404).json({error: "lecture not found"});
			res.json(lec);
		})
	});

	// Previous Repeat Count
	app.get("/previous/count", function(req, res) {
		Lecture.find({"Repeat": "Y"}, {_id: 0, Grades: 1, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	// Get Previous Lectures
	app.get("/previous/all", function(req, res) {
		Lecture.find({"Grades": {$ne: "null"}}, {_id: 0, Grades: 1, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});
	
	// START
	app.post("/start", function(req, res) {
		var len = req.body.length;
		
		for (var i = 0; i < len; i++) {
			var lecture = new Lecture();
			var cur_json = req.body.pop();
			console.log(cur_json);
		    
			lecture.Depart = cur_json.Depart;
			lecture.Code = cur_json.Code;
			lecture.Course_no = cur_json.Course_no;
			lecture.Course_type =  cur_json.Course_type;
			lecture.Course_title =  cur_json.Course_title;
			lecture.Credits =  cur_json.Credits;
			lecture.AU =  cur_json.AU;
			lecture.Grades =  cur_json.Grades;
			lecture.Repeat =  cur_json.Repeat;
			lecture.save(function(err){
				if (err) {
					console.error(err);
					res.json({result: 0});
					return;
				} else {
				}
			});
		}
		res.json({result: 1});
	});

	// Get Specific Lectures
	app.get("/lectures/find/a", function(req, res) {
		Lecture.find({"Course_type": "기필"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/b", function(req, res) {
		Lecture.find({"Course_type": "기선"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/c", function(req, res) {
		Lecture.find({"Course_type": "전필"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/d", function(req, res) {
		Lecture.find({"Course_type": "전선"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/e", function(req, res) {
		Lecture.find({"Course_type": "교필"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/f", function(req, res) {
		Lecture.find({"Course_type": "자선"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	app.get("/lectures/find/g", function(req, res) {
		Lecture.find({"Course_type": "인선"}, {_id: 0, AU: 1, Credits: 1, Course_title: 1, Course_type: 1, Course_no: 1, Depart: 1}, function(err, lec) {
			if (err) return res.status(500).json({error: err});
			res.json(lec);
		})
	});

	// UPDATE Lecture
	app.put("/api/lectures/:lecture_id", function(req, res) {
		Lecture.update({ _id: req.params.lecture_id }, { $set: req.body }, function(err, output) {
			if (err) res.status(500).json({ error: "database failure"});
			console.log(output);
			if(!output.n) return res.status(404).json({ error: "lecture not found"});
			res.json({ message: "lecture updated"});
		})
	});

	// DELETE Lecture
	app.get("/lectures/delete", function(req, res) {
		Lecture.remove({}, function(err, lectures) {
			if (err) return res.status(500).send({error: "database failure"});
			res.json(lectures);
		})
	});
}


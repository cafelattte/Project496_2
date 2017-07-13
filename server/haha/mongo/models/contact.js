var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var contactSchema = new Schema({
	name: String
	number: Number
	published_date: { type: Date, default: Date.now}
});

module.exports = mongoose.model('contact', contactSchema);


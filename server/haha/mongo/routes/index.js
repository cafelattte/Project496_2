module.exports = function(app, contact)
{
    // GET ALL CONTACTS
    app.get('/api/contacts', function(req,res){
		Contact.find(function(err, contacts) {
			if (err) return res.status(500).send({error: 'database failure'});
			res.json(contacts);
		})
    });

    // GET SINGLE CONTACT
    app.get('/api/contacts/:contact_number', function(req, res){
		Contact.findOne({_id: req.params.contact_number}, function(err, contact){
			if (err) return res.status(500).json({error: err});
			if (!contact) return res.status(404).json({error: 'contact not found'});
			res.json(contact)
		});
    });

    // GET CONTACT BY NAME
    app.get('/api/contacts/name/:name', function(req, res){
		Contact.find({name: req.params.name}, {_id: 0, number: 1, published_date : 1}, function(err, books){
			if (err) return res.status(500).json({error: err});
			if (contacts.length === 0) return res.status(404).json({error: 'contact not found'})
			res.json(contacts);
		});
    });

    // CREATE CONTACT
    app.post('/api/contacts', function(req, res){
		var contact = new Contact();
		contact.name = req.body.name;
		contact.number = req.body.number;
		contact.published_date = new Date(req.body.published_date);

		contact.save(function(err) {
			if (err) {
				console.err(err);
				res.json({result: 0});
				return;
			}
			res.json({result: 1});
		});
    });

    // UPDATE THE CONTACT
    app.put('/api/contacts/:contact_number', function(req, res){
		Contact.findById(req.params.contact_name, function(err, contact) {
			if (err) return res.status(500).json({error: err});
			if (!contact) return res.status(404).json({error: 'contact not found'});

			if (req.body.name) contact.name = req.body.name;
			if (req.body.number) contact.number = req.body.number;
			if (req.body.published_date) contact.published_date = req.body.published_date;
			contact.save(function(err) {
				if (err) return res.status(500).json({error: err});
				res.json({message: 'contact updated'});
			});
		});
    });

    // DELETE CONTACT
    app.delete('/api/contacts/:contact_number', function(req, res){
		Contact.remove({_id: req.params.contact_name}, function(err, output) {
			if (err) return res.status(500).json({error: 'database failure'});
			res.status(204).end();
		});
    });

}

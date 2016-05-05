var mubsub = require('./lib/index');


var socket = require('socket.io-client')('http://socketio-matiang01.rhcloud.com:8000');
socket.on('connect', function(){
	console.log('conctado');	
});

socket.on('event', function(data){	
	console.log('data'+ data);
});

socket.on('disconnect', function(){
	console.log('desconctado');
});

var client = mubsub('mongodb://test:test@ds015478.mlab.com:15478/pets');
var channel = client.channel('example');

channel.on('error', console.error);
client.on('error', console.error);

channel.subscribe('foo', function (message) {

		var message = {
				'room': 'Coding',
				'user': 'aa',
				'text': 'lll',
				'time': '2016-05-04T17:12:11.439Z'
			};

		socket.compress(false).emit('send:message',  message );
	
});

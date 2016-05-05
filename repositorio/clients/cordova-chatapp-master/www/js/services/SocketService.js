(function(){

	angular.module('starter')
	.service('SocketService', ['socketFactory', SocketService]);

	function SocketService(socketFactory){
		return socketFactory({
			
			ioSocket: io.connect('http://socketio-matiang01.rhcloud.com:8000')

			//ioSocket: io.connect('http://localhost:3000')

		});
	}
})();
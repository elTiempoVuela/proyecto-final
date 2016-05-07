(function(){
	angular.module('starter')
	.controller('HomeController', ['$scope', '$state', 'localStorageService', 'SocketService', HomeController]);
	
	function HomeController($scope, $state, localStorageService, SocketService){

		var me = this;

		me.current_room = localStorageService.get('room');
		me.rooms = ['mascotas', 'Art', 'Writing', 'Travel', 'Business', 'Photography'];
		

		
		try {
			
			me.status = SocketService;
			SocketService.on('connect', function () {
			
				me.status = me.status + 'connected';
				console.log(me.status);	
			});
			
			SocketService.on('disconnect', function () {
				me.status = me.status + 'disconnect';
				console.log(me.status);	
			});
				
		}
		catch(err) {
			console.log(err);
			me.status = err.message;
		}


		
		
		
		$scope.login = function(username){
			me.username = username;
			localStorageService.set('username', 'advertising');
			localStorageService.set('title', username);
			//$state.go('rooms');
			
			room_name = 'mascotas';
			me.current_room = room_name;
			localStorageService.set('room', room_name);
			
			var room = {
				'room_name': room_name
			};

			SocketService.emit('join:room', room);

			$state.go('room');
			
		};


		$scope.enterRoom = function(room_name){

			me.current_room = room_name;
			localStorageService.set('room', room_name);
			
			var room = {
				'room_name': room_name
			};

			SocketService.emit('join:room', room);

			$state.go('room');
		};

	}

})();
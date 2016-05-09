angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope) {})

.controller('ChatsCtrl', function($scope,$state, Chats,$ionicScrollDelegate, localStorageService) {
	
	
	
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});
 
  var socketService = Chats.socketService();
    
	$scope.chats = localStorageService.get('chats');
   
   	var room = { 'room_name': 'events' };   
	socketService.emit('join:room', room);
  	socketService.on('message', function(msg){
		
		console.log($scope.chats);
		if (typeof $scope.chats === "undefined" || $scope.chats === null) {
			var chats = [];
			$scope.chats = chats;	
			
		}
		
		$scope.chats.push(msg);
		$ionicScrollDelegate.scrollBottom();
		$scope.$broadcast('scroll.refreshComplete');
		localStorageService.set('chats', $scope.chats);
		
		});
  
  
 
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});

angular.module ('starter.controllers', [])

.controller('DashCtrl', function($scope, $ionicPopup, $cordovaVibration, Chats,$ionicScrollDelegate, localStorageService) {

 var socketService = Chats.socketService(); 
$scope.socketService = socketService; 
$scope.posts = localStorageService.get('posts');
	
   
// Triggered on a button click, or some other target
$scope.showPopup = function() {
  $scope.data = {text:''};
  
   // An elaborate, custom popup
  var myPopup = $ionicPopup.show({
    template: '<input type="text" ng-model="data.text">',
    title: 'Qué estas ladrando?',
    subTitle: 'Escribe los que ladras',
    scope: $scope,
    buttons: [
      { text: 'Cancel' },
      {
        text: '<b>Publish</b>',
        type: 'button-positive',
        onTap: function(e) {
          if (!$scope.data.text) {
            //don't allow the user to close unless he enters wifi password
            e.preventDefault();
          } else {
            return $scope.data.text;
          }
        }
      }
    ]
  });

  myPopup.then(function(res) {

	  if (typeof res != "undefined" && res != null) {
		  
		  var user = localStorageService.get('user');
	
			 if (typeof user === "undefined" || user === null) {
				user ='Anonimus';
			 }
		  
		  if (typeof $scope.posts === "undefined" || $scope.posts === null) {
				var posts = [];
				$scope.posts = posts;				
			}
			var post = {img:'img/post.jpg',message:res,user:user};	
			$scope.posts.push(post);
			
			var msg = {
			'room': 'posts',
			'img' : 'img/post.jpg',
			'user' : user,
			'message' : res
			};

			$scope.socketService.emit('send:message',  msg );	
	
		
	  }
  });
 };
 
 
   
   
   	var room = { 'room_name': 'posts' };   
	socketService.emit('join:room', room);
  	socketService.on('message', function(msg){
		console.log(msg);

		if (typeof $scope.posts === "undefined" || $scope.posts === null) {
			var posts = [];
			$scope.posts = posts;				
		}
		
		try{

		$cordovaVibration.vibrate(1000);
		 }catch(e){
			 
     	 }
		 
		 if(typeof msg.img === "undefined" || msg.img === null){
			 msg.img = 'img/post.jpg';
		 }
		 
		$scope.posts.push(msg);
		
		
		$ionicScrollDelegate.scrollBottom();
		$scope.$broadcast('scroll.refreshComplete');
		localStorageService.set('posts', $scope.posts);
		
		});
 
})


.controller('ChatsCtrl', function($scope,$state, $cordovaVibration, Chats,$ionicScrollDelegate, localStorageService	) {
	
	
	
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
		
		if (typeof $scope.chats === "undefined" || $scope.chats === null) {
			var chats = [];
			$scope.chats = chats;	
			
		}
		
		try{

		$cordovaVibration.vibrate(1000);
		 }catch(e){
			 
     	 }
		 
		$scope.chats.push(msg);
		$ionicScrollDelegate.scrollBottom();
		$scope.$broadcast('scroll.refreshComplete');
		localStorageService.set('chats', $scope.chats);
		
		});
  
  

})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope,localStorageService) {
  
    $scope._name = 'Anonimus';
	 $scope.user = {
    name: function(newName) {
     // Note that newName can be undefined for two reasons:
     // 1. Because it is called as a getter and thus called with no arguments
     // 2. Because the property should actually be set to undefined. This happens e.g. if the
     //    input is invalid
     return arguments.length ? ($scope._name = newName) : $scope._name;
    }
  };
  
  
   $scope.saveUser = function() {
		localStorageService.set('user', $scope._name);
	};
	
      $scope.clearNotifications = function() {
		var chats = [];
		localStorageService.set('chats', chats);
		$scope.$broadcast('scroll.refreshComplete');
	};
	
	 $scope.clearPosts = function() {
		 
		var posts = [];
		localStorageService.set('posts', posts);
		$scope.$broadcast('scroll.refreshComplete');
		
	};
  
});
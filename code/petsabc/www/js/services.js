angular.module('starter.services', [])
.service('SocketService', [])

.factory('Chats', function() {
  // Might use a resource here that returns a JSON array

  // Some fake testing data
  var chats = new Array();

  return {
	 
	socketService : function() {	
      return io.connect('http://socketio-matiang01.rhcloud.com:8000');
    } 
	,
    all: function() {	
      return chats;
    },
	add : function(msg) {	
      chats.push(msg);
	  console.log(chats);
    },
    remove: function(chat) {
      chats.splice(chats.indexOf(chat), 1);
    },
    get: function(chatId) {
      for (var i = 0; i < chats.length; i++) {
        if (chats[i].id === parseInt(chatId)) {
          return chats[i];
        }
      }
      return null;
    }
  };
});

/**
 * 
 */
var ToDo = angular.module('ToDo');

ToDo.factory('getAllNotesService',function($http,$location){
	// in the form of json object from login controller  
	var notes ={};
	
	notes.Notes = function() {
	console.log("inside getAllNotes")
		return $http({
			method :'get',
			url :'/ToDoNotesApp/getAllNotes',
			headers :{
				'token' : localStorage.getItem('token')
			}		
		})
	}
	
	notes.addNote=function(token,note){
		console.log("Inside add note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/createNote',

		    data:note,

		    headers: {

		        'token': token

		    }
		});
	}
	notes.deleteNote=function(token,note){
		console.log("Inside delete note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		        'token': token
		    }
		});
	}
	notes.archiveNote=function(token,note){
		console.log("Inside archive note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		        'token': token
		    }
		});
	}
	notes.reminderNote=function(token,note){
		console.log("Inside reminder note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		        'token': token
		    }
		});
	}
	notes.deleteForeverNote=function(token,note){
		console.log("Inside delete note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/deleteNote',

		    data:note,

		    headers: {

		        'token': token
		    }
		});
	}
	notes.restoreNote=function(token,note){
		console.log("Inside restore note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		        'token': token
		    }
		});
	}
	notes.updateNote=function(token,note){
		//console.log("Inside restore note");
		//console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		         'token': token
		    }
		});
	}
	notes.pinNote=function(token,note){
		console.log("Inside pin note");
		console.log(note);

		return $http({

		    method: 'POST',

		    url: '/ToDoNotesApp/updateNote',

		    data:note,

		    headers: {

		         'token': token
		    }
		});
	}
	return notes;

});
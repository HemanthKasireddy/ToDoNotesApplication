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
	notes.users=function() {
		console.log("inside get user ")
		return $http({
			method :'get',
			url :'/ToDoNotesApp/getUser',
			headers :{
				'token' : localStorage.getItem('token')
			}
		})
	}
	notes.getOwner=function(note) {
		console.log("inside get owner ")
		return $http({
			method :'get',
			url :'/ToDoNotesApp/getOwner',
			headers :{
						'token' : localStorage.getItem('token')
					},
		   data:note
		})
	}
	notes.collaborator=function(note,token,email) {
		console.log("in side real collaborator")
		return $http({
			method:'POST',
			url:'/ToDoNotesApp/collaborator',
			headers: {
		        		'token': token,
		        		'emailId':email

					 },
			data:note
		
		})
	}
	/*notes.getUserByEmail=function(email,token) {
		console.log("in side collaborator")
		console.log("getUserby emailid service token is: "+token)
		console.log("Service "+email);
		var urlI="/ToDoNotesApp/getuserByEmail";
		return $http({
			method:'GET',
			url:urlI,
			headers: {
		        		'token': token,
		        		'emailId':email
					 }
		
		})
	}*/
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
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
	
	notes.sharedNotes=function() {
		
		console.log("inside getAllNotes")
		return $http({
			method :'get',
			url :'/ToDoNotesApp/getAllSharedNotes',
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
			method :'POST',
			url :'/ToDoNotesApp/getOwner',
			headers :{
						'token' : localStorage.getItem('token')
					},
		   data:note
		})
	}
	notes.getAllUsers=function(note) {
		console.log("inside all owner ")
		return $http({
			method :'POST',
			url :'/ToDoNotesApp/sharedNotesUser',
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
		
		})WARN  PageNotFound - No mapping found for HTTP request with URI [/ToDoNotesApp/addLabel] in DispatcherServlet with name 'spring-dispatcher'

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
	notes.addLabel=function(label) {
		console.log("insidde add label")
		return $http({
			
			 method: 'POST',

			    url: '/ToDoNotesApp/addLabel',

			    data:label,

			    headers: {

			         'token': localStorage.getItem('token')
			    }
		});
	}
	notes.removeCollaborator=function(user) {
		console.log("inside remove colloborator")
		return $http({
			method:'POST',
			url:'/ToDoNotesApp/removeSharedUser',
			data:user,
			headers:{
				'token':localStorage.getItem('token')
			}
		})
	}
	notes.deleteLabels=function(label) {
		console.log("inside delete label")
		return $http({
			
			method:'POST',
			url:'/ToDoNotesApp/deleteLabel',
			data:label,
			headers:{
				'token':localStorage.getItem('token')
			}
		});
	}
	notes.updateNote=function(token,note){
		console.log("Inside update  note");
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
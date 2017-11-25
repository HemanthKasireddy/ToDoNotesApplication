/**
 * 
 */
var ToDo=angular.module('ToDo');

  ToDo.controller('homeController', function($scope, $timeout, $mdSidenav,getAllNotesService,$location,$mdDialog,$state) {
	  $scope.displayDiv=false;
		$scope.show=function(){
			console.log("inside create")
			$scope.displayDiv=true;
		}
	  $scope.toggleLeft = function(){
		  console.log("inside toggle");
		 // $scope.notes = getAllNotesService.notes;
		 		  $mdSidenav('left').toggle();
	  }
	  
	  var getNotes=function(){
	    	
		  var Notes = getAllNotesService.Notes();

			Notes.then(function(response) {
				$scope.notes = response.data;
				getAllNotesService.notes = response.data;
				console.log(Notes)
			}, function(response) {
				console.log(response.status);
				if (response.status = '511') {
					$location.path('/loginpage')
				}
			})

	    }
	
		
	  $scope.addNote = function() {
	    	$scope.note = {};
	    	var token = localStorage.getItem('token');
	    	$scope.note.title = document.getElementById("noteTitle").innerHTML;
	    	
	    	$scope.note.content = document.getElementById("noteBody").innerHTML;
			
			var notes = getAllNotesService.addNote(token, $scope.note);
			
			notes.then(function(response) {

				document.getElementById("noteTitle").innerHTML="";
				document.getElementById("noteBody").innerHTML="";
				getNotes();

			}, function(response) {

				getNotes();	

				$scope.error = response.data.message;

			});
		}
	  $scope.archiveNote=function(note,status) {
		  note.archive=status;
		  console.log(note)
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.archiveNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.pinnedNote=function(note,status) {
		  note.pinned=status;
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.pinNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.deleteNote= function(note) {
		  note.trash=1;
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.deleteNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.reminderNote= function(note) {
		  note.reminder=1;
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.reminderNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.deleteForeverNote= function(note) {
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.deleteForeverNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.restoreNote=function(note) {
		  note.trash=0;
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.restoreNote(token,note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }
	  $scope.displayDiv=false;
		$scope.show=function(){
			$scope.displayDiv=true;
		}
		

		
		$scope.updateNote = function(note, event) {
			console.log('calling');
		    // Show dialog box for edit a note
			//console.log("inside updatenote");
			//console.log(note);
		    $mdDialog.show({
		      locals: {
		        dataToPass: note  // Pass the note data into dialog box
		      },
		      templateUrl: 'template/updateNote.html',
		      parent: angular.element(document.body),
		      targetEvent: event,
		      clickOutsideToClose: true,
		      controllerAs: 'controller',
		      controller: mdDialogController
		    });
		}
		
		function mdDialogController($scope, $state, dataToPass) {
		      $scope.mdDialogData = dataToPass;

		      // Saving the edited note
		      	$scope.saveUpdatedNote = function() {
		    	var url = 'update';
		    	
		    	console.log(dataToPass);
		    	
		    	dataToPass.title = document.getElementById("updatedNoteTitle").innerHTML;
		    	
		    	dataToPass.content = document.getElementById("updatedNoteBody").innerHTML;
		    	/*var updatedNoteTitle = document.getElementById("updatedNoteTitle").innerHTML;
		    	
		    	var updatedNoteBody = document.getElementById("updatedNoteBody").innerHTML;*/
		    	
		    	console.log(dataToPass);
		    	var token= localStorage.getItem('token');
		  		var notes = getAllNotesService.updateNote(token,dataToPass)
		  		
		  		notes.then(function(response){
					console.log(" updated success")
					  getNotes();
					$mdDialog.cancel();
					$state.reload();

				},function(response){
					$scope.error=response.data.responseMessage;
				});
		      }
		
		}
		getNotes();	
  });
	  
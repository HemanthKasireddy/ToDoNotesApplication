/**
 * 
 */
var ToDo=angular.module('ToDo');

  ToDo.controller('homeController', function($scope, $timeout, $mdSidenav,getAllNotesService,$location,$mdDialog,$state,mdcDateTimeDialog,toastr,$filter,$interval,$http) {
	  
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
	  var getUser=function() {
		  var user= getAllNotesService.users();
		  user.then(function(response) {
			  console.log(user);
				$scope.user = response.data;

		  }, function(response) {
			  console.log(response.status)
		  
	  })
	  }
	  
	
	
	  var getNotes=function(){
	    	
		  var Notes = getAllNotesService.Notes();

			Notes.then(function(response) {
				$scope.notes = response.data;
				getAllNotesService.notes = response.data;
				console.log(Notes)
				 $interval(function () {
				       
			          for (var i = 0; i < response.data.length; i++) {
			            if(response.data[i].reminder) {
			            	var date=new Date(response.data[i].reminder);
			            	if ($filter('date')(date)== $filter('date')(new Date())) {
			                toastr.success(response.data[i].body, response.data[i].title);
			              }
			            }
			          }
			      }, 60000);
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
			$scope.note.color=$scope.color;

			notes.then(function(response) {

				document.getElementById("noteTitle").innerHTML="";
				document.getElementById("noteBody").innerHTML="";
				$scope.color='#fff';
				$scope.displayDiv=false;

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
	  
	  $scope.logout = function() {
			localStorage.removeItem('token');
			$location.path('login');
		}
	  $scope.displayDialog = function (note) {
	      mdcDateTimeDialog.show({
	       /* maxDate: $scope.maxDate,*/
	        time: true
	      })
	        .then(function (date) {
	        	console.log(date);
	          $scope.selectedDateTime = date;
	          note.reminder=date;
	          
	          console.log('New Date / Time selected:', date);
	          
	          var token= localStorage.getItem('token');
			  var notes = getAllNotesService.reminderNote(token,note);
			  notes.then(function(response) {
				  console.log("note is reminder success");
				  getNotes();
			  }, function(response) {
				  getNotes();
					$scope.error = response.data.message;

			  })
	        });
	    };
	   
	    $scope.share= function(note) {
			  note.reminder=null;
			  console.log(note);
			  var token= localStorage.getItem('token');
			  var notes = getAllNotesService.reminderNote(token,note);
			  notes.then(function(response) {
				  console.log("note reminder cancelled");
				  getNotes();
			  }, function(response) {
				  getNotes();
					$scope.error = response.data.message;

			  })
			
		  }
	    
	  $scope.cancelReminder= function(note) {
		  note.reminder=null;
		  console.log(note);
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.reminderNote(token,note);
		  notes.then(function(response) {
			  console.log("note reminder cancelled");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
		
	  }
	  $scope.colors = [ '#fff', '#ff8a80', '#ffd180', '#ffff8d',
			'#ccff90', '#a7ffeb', '#80d8ff', '#82b1ff',
			'#b388ff', '#f8bbd0', '#d7ccc8', '#cfd8dc' ];
		
		 
		 
		 $scope.noteColor=function(newColor, oldColor)
		 {
			 console.log(newColor);
			 $scope.color = newColor;
		 }
		 
		$scope.colorChanged = function(newColor, oldColor, note) {
	        note.color=newColor;
	        
			 console.log("present note color is"+note.color);

	        var token= localStorage.getItem('token');
	  		var notes = getAllNotesService.updateNote(token,note)
	  		
	  		notes.then(function(response){
				console.log(" updated color  success")
				  getNotes();
				$mdDialog.cancel();
				$state.reload();

			},function(response){
				$scope.error=response.data.responseMessage;
			});

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
			note.updatedTime=new Date();
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
		
		$scope.collaborator = function(note, event) {
		    $mdDialog.show({
		      locals: {
		        dataToPass: note  // Pass the note data into dialog box
		      },
		      templateUrl: 'template/collaborator.html',
		      parent: angular.element(document.body),
		      targetEvent: event,
		      clickOutsideToClose: true,
		      controllerAs: 'controller',
		      controller: mdDialogshareController
		    });
		}
		function mdDialogshareController($scope, $state, dataToPass) {
		      $scope.mdDialogData = dataToPass;
		    	var token= localStorage.getItem('token');

		      console.log("Token of colaborator note is : "+token);
		      
		        var getOwner = function() {
		        	console.log("in side get owner controller");
					var noteOwner = getAllNotesService.getOwner(dataToPass);
					noteOwner.then(function(response) {

						$scope.owner = response.data;
						console.log("inside get owner"+$scope.owner);

					}, function(response) {
						console.log("fail");
					});
				}
		        getOwner();
		        var getAllUsers=function() {
		  		  var token =localStorage.getItem('token');
		  		  var allSharedUsers=getAllNotesService.getAllUsers(dataToPass);
		  		  allSharedUsers.then(function(response) {

		  				$scope.allUsers = response.data;
		  				console.log("inside get all users"+$scope.allUsers);
		  			}, function(response) {
		  				console.log("fail");
		  			});
		  	  }
		       getAllUsers();
		      // Saving the edited note
		      	$scope.sharColleborator = function() {
		    	
		    	console.log($scope.collaboratorEmail);
/*		    	var user= getAllNotesService.getUserByEmail($scope.collaboratorEmail,token);
		    	user.then(function(response) {
					  console.log(user);
					  	console.log("gggggggggggggggggggggg")
						$scope.user = response.data;
				    	dataToPass.sharedUser=$scope.user; 
				    	console.log(dataToPass);
*/				  	
		    	var notes = getAllNotesService.collaborator(dataToPass,token,$scope.collaboratorEmail)		
				  		notes.then(function(response){
							console.log(" shared success")
							  getNotes();
							$mdDialog.cancel();
							$state.reload();

						},function(response){
							$scope.error=response.data.responseMessage;
						});
				 /* }, function(response) {
					  console.log(response.status)
				  
			  })*/
		   		 			    	
		    	
		      }
		
		}
		
		
		getUser();
		getNotes();	
  });
	  
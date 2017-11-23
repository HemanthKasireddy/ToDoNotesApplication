/**
 * 
 */
var ToDo=angular.module('ToDo');

  ToDo.controller('homeController', function($scope, $timeout, $mdSidenav,getAllNotesService,$location) {
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
	  $scope.deleteNote= function(note) {
		  var token= localStorage.getItem('token');
		  var notes = getAllNotesService.deleteNote(token,$scope.note);
		  notes.then(function(response) {
			  console.log("note deleted");
			  getNotes();
		  }, function(response) {
			  getNotes();
				$scope.error = response.data.message;

		  })
	  }

		getNotes();	
  });
	  
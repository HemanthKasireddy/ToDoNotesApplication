/**
 * 
 */
var ToDo=angular.module('ToDo');

  ToDo.controller('', function($scope, $timeout, $mdSidenav) {
	  console.log("inside toggle");
	  $scope.toggleLeft = function(){
		  console.log("inside toggle");
		  $mdSidenav('left').toggle();
	  }
	
  });
  ToDo.controller("homeController",function($scope,getAllNotesService,$state) {
	  $scope.notes = homeService.notes;
		if ($scope.notes.length == 0) {
			var httpNotes = homeService.getAllNotes();

			httpNotes.then(function(response) {
				$scope.notes = response.data;
				homeService.notes = response.data;
			}, function(response) {
				console.log(response.status);
				if (response.status = '511') {
					$location.path('/loginpage')
				}
			});
		}
  }) 
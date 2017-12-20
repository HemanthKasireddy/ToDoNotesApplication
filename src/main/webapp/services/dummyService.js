/**
 * 
 */
var toDo = angular.module('ToDo');

toDo.factory('dummyService', function($http, $location) {
	
var login={};
login.service=function(){
	return $http({
		method:'GET',
		url :'/ToDoNotesApp/getToken'
	})
	}
return login;
})

/**
 * 
 */
var ToDo = angular.module('ToDo');
ToDo.controller('dummyController',function($location,dummyService,$state,$scope){
	var dummy=dummyService.service();
	
	dummy.then(function(response){
		/*localStorage.setItem('token',response.data.responseMessage);
		console.log("Token is "+response.data.responseMessage)*/
		
		console.log(response.headers("Authorazation"));
		localStorage.setItem('token',response.headers("Authorazation"));
		$location.path('/home');
	},function(response){
		$scope.error = response.data.responseMessage;

	});

});
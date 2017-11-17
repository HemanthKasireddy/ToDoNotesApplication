/**
 * 
 */
var ToDo = angular.module("ToDo")
.controller("logIn", function($scope,loginService,$state) {

	$scope.logInUser = function() {
		var user = loginService.logInUser($scope.user ,$scope.error) ;
			user.then(function(response) {
				console.log(response.data.responseMessage);
				localStorage.setItem('token',response.data.responseMessage);
				console.log("your log in succesfully ");
				$state.go('home');
			} ,function(response){
/*
				if(response.status==409) {

					$scope.error=response.data.responseMessage;

				}else {

					console.log("log in fail");
					$scope.error="Enter valid data";

				}*/
			}
	)
		
	}
});
/**
 * 
 */
var ToDo = angular.module("ToDo")
.controller("signUpController", function($scope,signUpService,$state) {

	$scope.registeringUser = function() {
		var user = signUpService.signupuser($scope.user  ,$scope.error) ;
			user.then(function(response) {
				console.log(response.data.responseMessage);
				//localStorage.setItem('token',response.data.responseMessage);
				console.log(" succesfully registration complited ");
				$state.go('login');
			} ,function(response){

				if(response.status==409) {

					$scope.error=response.data.responseMessage;

				} else {

					console.log("registration  fail");
					$scope.error="Enter valid data";

				}
			}
	)
		
	}
});
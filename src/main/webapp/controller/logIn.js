/**
 * 
 */
var ToDo = angular.module("ToDo")
.controller("loginController", function($scope,loginService,$state) {

	$scope.logInUser = function() {
		var user = loginService.loginUser($scope.user ,$scope.error) ;
			user.then(function(response) {
				console.log(response.data.token);
				localStorage.setItem('token',response.data.token);
				console.log("your log in succesfully ");
				$state.go('home');
				//$location.path('home');
			} ,function(response){

				if(response.status==409) {

					$scope.error=response.data.responseMessage;

				}else {

					console.log("log in fail");
					$scope.error="Enter valid data";

				}
			}
	)
		
	}
});
/**
 * 
 */
var ToDo = angular.module("ToDo")
.controller("forgotPasswordController", function($scope,forgotPasswordService,$state) {

	$scope.forgotPassword = function() {
		var user = forgotPasswordService.forgotPassword($scope.user ,$scope.error) ;
			user.then(function(response) {
				console.log(response.data.responseMessage);
				console.log("your link is sent sucessfully ");
				$state.go('resetPassword');
				//$location.path('home');
			} ,function(response){

				if(response.status==409) {

					$scope.error=response.data.responseMessage;

				} else {
					
					console.log("forgot password link is not able to send");
					$scope.error="Enter valid data";

				}
			} 
		)}
});
ToDo.controller("resetPasswordController", function($scope,resetPasswordService,$state) {

	$scope.resetPassword = function() {
		var user = resetPasswordService.resetPassword($scope.user ,$scope.error) ;
			user.then(function(response) {
				console.log(response.data.responseMessage);
				console.log("your registered in succesfully ");
				$state.go('');
				//$location.path('home');
			} ,function(response){

				if(response.status==409) {

					$scope.error=response.data.responseMessage;

				} else {
					
					console.log("log in fail");
					$scope.error="Enter valid data";

				}
			} 
		)}
});
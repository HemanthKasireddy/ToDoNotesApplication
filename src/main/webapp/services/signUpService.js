/**
 * 
 */
var ToDo = angular.module('ToDo')
				  .factory('signUpService',function($http,$location){
	// in the form of json object from login controller  
						var signup ;
						
						signup.signupUser = function(success) {
						
							return $http({
								method :"POST",
								url :'login',
								data : success
							});
						}
						
						return signup;
					
					});
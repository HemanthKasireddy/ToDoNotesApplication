/**
 * 
 */
var ToDo = angular.module('ToDo')
				  .factory('signUpService',function($http,$location){
	// in the form of json object from login controller  
						var signup={} ;
						
						signup.signupuser = function(user) {
						
							return $http({
								method :"POST",
								url :'register',
								data : user
							});
						}
						
						return signup;
					
					});
/**
 * 
 */
var ToDo = angular.module('ToDo');

ToDo.factory('forgotPasswordService',function($http,$location){
	// in the form of json object from login controller  
	var password ={};
	
	password.forgotPassword = function(user) {
	
		return $http({
			method :"POST",
			url :'forgotPassword',
			data : user
		});
	}
	
	return forgotpassword;

});
ToDo.factory('resetPasswordService',function($http,$location){
	// in the form of json object from login controller  
	var password ={};
	
	 password.resetPassword = function(user) {
	
		return $http({
			method :"POST",
			url :'resetPassword',
			data : user
		});
	}
	
	return forgotpassword;

});


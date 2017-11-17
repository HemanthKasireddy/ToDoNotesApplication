/**
 * 
 */

var ToDo=angular.module('ToDo', ['ui.router'])
				.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider) {
					
						$stateProvider.state('login',{
							
							url : '/login',
							templateUrl : 'template/logIn.html',
							controller : 'logIn'
						
						});
				
						$urlRouterProvider.otherwise('login');
					
				}]);
/**
 * 
 */

var ToDo=angular.module('ToDo', ['ui.router','ngMaterial', 'ngAnimate', 'ngAria'])
				.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider) {
					
						$stateProvider.state('login',{
							
							url : '/login',
							templateUrl : 'template/logIn.html',
							controller : 'loginController'
						
						});
						$stateProvider.state('signup',{
							
							url : '/signup',
							templateUrl : 'template/signup.html',
							controller : 'register'
						
						});
						$stateProvider.state('home', {
							url : '/home',
							templateUrl : 'template/home.html'
						});
				
						$urlRouterProvider.otherwise('login');
					
				}]);
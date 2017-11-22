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
							controller : 'signUpController'
						
						});
						$stateProvider.state('home', {
							url : '/home',
							templateUrl : 'template/home.html',
							controller:'homeController'
							
						});
						$stateProvider.state('forgotPassword', {
							url : '/forgotPassword',
							templateUrl : 'template/forgotPassword.html',
							controller : 'forgotPasswordController'
						});
						
						$stateProvider.state('resetPassword', {
							url : '/resetPassword',
							templateUrl : 'template/resetPassword.html',
							controller : 'resetPasswordController'
						});
						$urlRouterProvider.otherwise('login');
					
				}]);
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
							templateUrl : 'template/home.html'
						});
						$stateProvider.state('forgotPassword', {
							url : '/forgotPassword',
							templateUrl : 'template/ForgotPassword.html',
							controller : 'forgotPasswordController'
						});
						
						$stateProvider.state('resetPassword', {
							url : '/resetPassword',
							templateUrl : 'template/ResetPassword.html',
							controller : 'resetPasswordController'
						});
						$urlRouterProvider.otherwise('login');
					
				}]);
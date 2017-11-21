/**
 * 
 */
var ToDo = angular.module("ToDo")
	ToDo.directive('topNavBar',function(){
			return {
					restrict: 'E',
					templateUrl: './template/topNavBar.html'
			}
	})
ToDo.directive('sideNavBar',function(){
	return {
		restrict: 'E',
		templateUrl: './template/sideNavBar.html'
	}
});
/*var ToDo = angular.module("ToDo");
ToDo.directive('topNavBar', function() {

	return {
		 restrict:'E',
		templateUrl : './templates/TopNavBar.html'
	}
})
ToDo.directive('sideNavBar', function() {

	return {
		 restrict:'E',
		templateUrl : './templates/ideNavBar.html'
	}

});*/
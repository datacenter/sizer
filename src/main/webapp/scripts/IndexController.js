angular.module('webappApp')
.controller('IndexController', function ($scope,$rootScope,$location) {
        $rootScope.isProjectVisible = false ;
       // $rootScope.projectSelect = "";
       $rootScope.projectDropDown = false;
       $rootScope.project=[];

        $scope.changeProject = function(value){
        	if(value == 'Logical') {
        		$location.path('/projects');
        	} else {
        		$location.path('/physical');
        	}
        }

        $scope.showDashBoard = function(){
        	$rootScope.projectDropDown = false;

        }
		
    })

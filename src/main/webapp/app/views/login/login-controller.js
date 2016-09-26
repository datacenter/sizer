(function() {
  'use strict';

  angular
    .module('profiler')
    .controller('loginController', ["$scope", "authenticationService", "appService","$rootScope", function($scope, authenticationService, appService, $rootScope) {
      var vm = this;

      function init() {
        vm.username = '';
        vm.password = '';
        vm.isCredentialsIncorrect = false;
        $rootScope.isUserLoggedIn = false;
      }

      vm.onFocuFormInput = function(){
        vm.isCredentialsIncorrect = false;
      }

      vm.login = function() {
        if ($scope.loginForm.$valid) {
          authenticationService.login(vm.username, vm.password).then(function(response) {
            appService.setUserDetails(response);
            appService.gotoView("dashboard");
            $rootScope.username = response.username;
             $rootScope.isUserLoggedIn = true;             
          }, function() {
            //error in ajax call
            vm.isCredentialsIncorrect = true;
          });
        }
      };


      $scope.$on('$viewContentLoaded', function() {
        init();
      })


    }]); //controller

})();

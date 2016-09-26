(function() {
  'use strict';

  angular
    .module('profiler')
    .controller('usersController', ['$scope','$rootScope', '$timeout', '$uibModal', 'userService', function($scope,$rootScope, $timeout, $uibModal, userService) {
      var vm = this;
      vm.usersList = userService.query();
       $rootScope.projectDropDown = false;
      // POPUPs
      vm.addUserPopup = function() {
        $uibModal.open({
          templateUrl: 'app/views/home/users/modals/adduser.html',
          size: 'sm',
          scope: $scope,
          controller: 'userPopupController',
          controllerAs: 'popupCtrl',
          resolve: {
            userObject: function() {
              return null;
            }
          }

        }).result.then(function(userObject) {
          vm.usersList.push(userObject);
        }, function() {
          // console.log('CANCEL');
        })
      };

      vm.editUserPopup = function(userToEdit) {
        $uibModal.open({
          templateUrl: 'app/views/home/users/modals/edituser.html',
          size: 'sm',
          scope: $scope,
          controller: 'userPopupController',
          controllerAs: 'popupCtrl',
          resolve: {
            userObject: function() {
              return userToEdit;
            }
          }
        }).result.then(function(userObjectUpdated) {
          for (var i = 0; i < vm.usersList.length; i++) {
            if (vm.usersList[i].id == userObjectUpdated.id) {
              vm.usersList[i] = userObjectUpdated;
              break;
            }
          }
        }, function() {
          console.log('Dismiss');
        });
      };

      vm.deleteUserPopup = function(userToDelete) {
          $uibModal.open({
            templateUrl: 'app/views/home/users/modals/deleteuser.html',
            size: 'sm',
            scope: $scope,
            controller: 'userPopupController',
            controllerAs: 'popupCtrl',
            resolve: {
              userObject: function() {
                return userToDelete;
              }
            }
          }).result.then(function(deletedUserId) {
            for (var i = 0; i < vm.usersList.length; i++) {
              if (vm.usersList[i].id == deletedUserId) {
                vm.usersList.splice(i, 1);
                break;
              }
            }
          }, function() {
            console.log('Dismiss');
          });
        } //deleteUserPopup

    }]);

})();

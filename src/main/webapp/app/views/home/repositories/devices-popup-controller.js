(function() {
  'use strict';

  angular
    .module('profiler')
    .controller('devicePopupController', ['$scope', '$uibModalInstance', 'modelsList', 'deviceService', 'deviceObject','$rootScope', function($scope, $uibModalInstance, modelsList, deviceService, deviceObject, $rootScope) {
      var vm = this;
      vm.deviceObject = angular.extend({}, deviceObject);
      vm.deviceModels = modelsList;
      
      vm.addDevice = function(deviceToAdd) {
        var obj = angular.extend({}, deviceToAdd);
        obj.owner = $rootScope.userInfo.name;
        if ($scope.addDeviceForm.$valid) {
          deviceService.save(obj).$promise.then(function(deviceObjectAdded) {
            $uibModalInstance.close(deviceObjectAdded);
          });
        }
      }; //addDevice

      vm.editDevice = function(deviceToUpdate) {
        console.log(deviceToUpdate);
        if ($scope.editDeviceForm.$valid) {
          deviceService.update({id:deviceToUpdate.id, userId: $rootScope.userInfo.name}, deviceToUpdate).$promise.then(function(deviceObjectUpdated) {
            $uibModalInstance.close(deviceObjectUpdated);
            $scope.repoCtrl.selectedDevice.name = deviceObjectUpdated.name;

          });
        }
      }; //editDevice

      vm.deleteDevice = function(deviceIdToDelete) {
        deviceService.delete({ id: deviceIdToDelete, userId: $rootScope.userInfo.name }).$promise.then(function() {
          $uibModalInstance.close(deviceIdToDelete);
        });
      }; //deleteDevice

    }]);

})();

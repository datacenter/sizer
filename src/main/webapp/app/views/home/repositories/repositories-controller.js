(function() {
  'use strict';

  angular
    .module('profiler')
    .controller('repositoriesController', ['$scope', '$rootScope', 'deviceService', function($scope, $rootScope, deviceService) {
      var vm = this;
      vm.devicesList = [];
      vm.selectedDevice = {};
      vm.selectedDeviceIndex = 0;
       $rootScope.projectDropDown = false;
      vm.getDevicesList = function(shouldNotifyDeviceSelection){
        deviceService.query({ userId: $rootScope.userInfo.name }).$promise.then(function(data) {
          vm.devicesList = data;
          $rootScope.$broadcast('PROFILER_DEVICE_LIST_UPDATED', data);
          if(shouldNotifyDeviceSelection){
            notifyDeviceSelection();
          }
        });
      };

      vm.setSelectedDeviceIndex = function(index){
          vm.selectedDeviceIndex = index;
          notifyDeviceSelection();  
      };

      function notifyDeviceSelection(){
        vm.selectedDevice = vm.devicesList[vm.selectedDeviceIndex] || {}; 
        $rootScope.$broadcast('PROFILER_DEVICE_SELECTED', {device:vm.selectedDevice});
      };

    }]);

})();

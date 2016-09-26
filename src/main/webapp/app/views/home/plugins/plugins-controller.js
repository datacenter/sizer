(function() {
  'use strict';

  angular
    .module('profiler')
    .controller('pluginsController', ['$scope','$rootScope', '$timeout', 'pluginService', 'util', function($scope,$rootScope, $timeout, pluginService, util) {
      var vm = this;
      vm.pluginsList = [];//list of app plugins
      vm.vendorsList = [];
      vm.vendorPluginsList = null; // list of plugins of specific vendor / description
       $rootScope.projectDropDown = false;
      vm.getPlugins = function() {
        pluginService.query().$promise.then(function(response) {
          vm.pluginsList = response;
          vm.vendorsList = util.getUniqueValuesOfProp(vm.pluginsList, 'description');
          
        });
      };

    }]);



})();

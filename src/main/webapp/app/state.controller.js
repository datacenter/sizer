(function(){
  "use strict";

  angular
    .module('profiler')
    .run(['$rootScope', 'appService', function($rootScope, appService){
      $rootScope.root = {};

      $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams) {
        if(toState.name !== 'login' && !appService.isUserLoggedIn()){
          event.preventDefault();
          appService.gotoView("login");
          return;
        }else if( (fromState.name ==="") && (toState.name==='physical') ){
          event.preventDefault();
          appService.gotoView("dashboard");
          return;
        }else if( (fromState.name ==="") && (toState.name==='projects') ){
          event.preventDefault();
          appService.gotoView("dashboard");
          return;
        }
          
        $rootScope.root.currentState = toState.name;
        
      });

    }]);




})();
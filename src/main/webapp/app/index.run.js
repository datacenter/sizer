(function() {
  'use strict';

  angular
    .module('profiler')
    .run(runBlock);

  /** @ngInject */
  function runBlock($log, $rootScope, appService, authenticationService) {

  	$rootScope.logicalURL = '/profiler/v1/projects/';

    $rootScope.isUserLoggedIn = appService.isUserLoggedIn();

    $rootScope.userInfo = appService.getUserInfo();

    $rootScope.logOut = function(){
      appService.logout();
    };

  	// var user = {
  	// 	name : 'admin',
  	// 	pwd : 'admin'
  	// };

   //  // $log.debug('runBlock end');

   //  authenticationService.login(user.name, user.pwd).then(function(response) {
	  //   appService.setUserDetails(response);
	  //   appService.gotoView('dashboard');
   //      if(response.role == 'ROLE_USER'){
   //        $rootScope.displayrepositoryTab = false;
   //      }else{
   //        $rootScope.displayrepositoryTab = true;
   //      }
	  // }, function() {
	  //   //error in ajax call	
	  //   appService.gotoView("dashboard");    
	  // });

   //  authenticationService.getUserInfo().then(function(response){ 
   //    $rootScope.username = response.username;
   //    appService.setUserDetails(response);
   //  }, function() {
   //    //error in ajax call  
   //  });

  }

})();
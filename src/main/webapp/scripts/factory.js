angular.module('webappApp').factory("CONSTANT_FACTORY",function(){
	 var factory = {};

     //$rootScope.projectId = 387;
     var UTILITY="utility";
     
     factory.getUtility=function(){
     	return UTILITY;
     }
     
     return factory;
})
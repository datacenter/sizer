'use strict';

/**
 * @ngdoc overview
 * @name webappApp
 * @description
 * # webappApp
 *
 * Main module of the application.
 */

angular.module('physical', []); 
angular.module('dashboard', []);
 
angular
    .module('webappApp', ['physical', 'profiler','dashboard', 'tmh.dynamicLocale',
        'ngResource', 'ui.router', 'ngCookies', 'pascalprecht.translate', 'aci.directives', 'xeditable'
    ,'widget.scrollbar', 'chart.js','ui.bootstrap','angularUtils.directives.dirPagination'])
   //  .config(function ($routeProvider, $httpProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {
   //      $routeProvider
   //          .when('/home', {
   //              templateUrl: 'views/home.html',
   //              controller: 'MainCtrl',
   //              controllerAs: 'main'
   //          })
   //          .when('/about', {
   //              templateUrl: 'views/about.html',
   //              controller: 'AboutCtrl',
   //              controllerAs: 'about'
   //          })
   //          .when('/projects', {
   //              templateUrl: 'views/main.html',
   //              controller: 'MainController'
   //          })
   //          .when('/physical', {
   //              templateUrl: 'views/main.html',
   //              controller: 'physicalController'
   //          })
   //          .when('/results', {
   //              templateUrl: 'views/main.html',
   //              controller: 'MainController'
   //          }) 
   //          .when('/template', {
   //              templateUrl: 'views/main.html',
   //              controller: 'MainController'
   //          })			
   //          .otherwise({
   //              templateUrl: 'modules/dashboard/views/dashboard.html',
   //              controller: 'HomeController',
   //          })
			// /*
   //          .otherwise({
   //              templateUrl: 'views/projectlist.html',
   //              controller: 'HomeController',
   //          })*/;
   //      // Initialize angular-translate
   //      $translateProvider.useStaticFilesLoader({
   //          prefix: 'views/i18n/',
   //          suffix: '.json'
   //      });

   //      $translateProvider.preferredLanguage('en');
   //      $translateProvider.useCookieStorage();
   //      tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js')
   //      tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');
   //  })
   //   .run(function($rootScope, $location, $http) {
   //      $rootScope.authenticated = true;
   //     // $rootScope.showTabs();
   //  });

    .config(routeConfig);
  
  //routes configuration
    function routeConfig($stateProvider, $locationProvider, $urlRouterProvider) {

        $stateProvider
          .state('dashboard', {
            url: '/dashboard',
            templateUrl: 'modules/dashboard/views/dashboard.html',
            controller: 'HomeController'
          })
         .state('login', {
            url: '/',
            templateUrl: 'app/views/login/login.html',
            controller: 'loginController as loginCtrl'
          })
          .state('projects', {
            url: '/projects',
            templateUrl: 'views/main.html',
            controller: 'MainController as mainCtrl'
          })
          .state('about', {
            url: '/about',
            templateUrl: 'views/about.html',
            controller: 'AboutCtrl as about'
          })
          .state('physical', {
            url: '/physical',
            templateUrl: 'views/main.html',
            controller: 'physicalController'
          })
          .state('template', {
            url: '/template',
            templateUrl: 'views/home.html',
            controller: 'MainCtrl'
          })
          .state('users', {
            url: '/users',
            templateUrl: 'app/views/home/users/users.html',
            controller: 'usersController as usersCtrl'
          })
            .state('plugins', {
                url: '/plugins',
                views : {
                  '' : {
                    templateUrl: 'app/views/home/plugins/plugins.html',
                    controller: 'pluginsController as pluginsCtrl'
                  },
                  'leftpanel@plugins' : {
                    templateUrl: 'app/views/home/plugins/plugins-left-panel.html',
                    controller: 'pluginsLeftPanelController as pluginsLPCtrl'
                  },
                  'rightpanel@plugins' : {
                    templateUrl: 'app/views/home/plugins/plugins-right-panel.html',
                    controller: 'pluginsRightPanelController as pluginsRPCtrl'
                  }
                }        
              })
            .state('repositories', {
                url: '/repositories',
                views : {
                  '' : {
                    templateUrl: 'app/views/home/repositories/repositories.html',
                    controller: 'repositoriesController as repoCtrl'
                  },
                  'leftpanel@repositories' : {
                    templateUrl: 'app/views/home/repositories/repository-left-panel.html',
                    controller: 'repositoryLeftPanelController as repoLPCtrl'
                  },
                  'rightpanel@repositories' : {
                    templateUrl: 'app/views/home/repositories/repository-right-panel.html',
                    controller: 'repositoryRightPanelController as repoRPCtrl'
                  }
                }        
              });     

        $urlRouterProvider.otherwise('/');

    }
   

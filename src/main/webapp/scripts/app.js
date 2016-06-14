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
    .module('webappApp', ['physical', 'dashboard', 'tmh.dynamicLocale',
        'ngResource', 'ngRoute', 'ngCookies', 'pascalprecht.translate', 'ngCacheBuster', 'aci.directives', 'xeditable'
    ,'widget.scrollbar', 'chart.js'])
    .config(function ($routeProvider, $httpProvider, $translateProvider, tmhDynamicLocaleProvider, httpRequestInterceptorCacheBusterProvider) {
        $routeProvider
            .when('/home', {
                templateUrl: 'views/home.html',
                controller: 'MainCtrl',
                controllerAs: 'main'
            })
            .when('/about', {
                templateUrl: 'views/about.html',
                controller: 'AboutCtrl',
                controllerAs: 'about'
            })
            .when('/projects', {
                templateUrl: 'views/main.html',
                controller: 'MainController'
            })
            .when('/physical', {
                templateUrl: 'views/main.html',
                controller: 'physicalController'
            })
            .when('/results', {
                templateUrl: 'views/main.html',
                controller: 'MainController'
            }) 
            .when('/template', {
                templateUrl: 'views/main.html',
                controller: 'MainController'
            })			
            .otherwise({
                templateUrl: 'modules/dashboard/views/dashboard.html',
                controller: 'HomeController',
            })
			/*
            .otherwise({
                templateUrl: 'views/projectlist.html',
                controller: 'HomeController',
            })*/;
        // Initialize angular-translate
        $translateProvider.useStaticFilesLoader({
            prefix: 'views/i18n/',
            suffix: '.json'
        });

        $translateProvider.preferredLanguage('en');
        $translateProvider.useCookieStorage();
        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js')
        tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');
    })
    .run(function($rootScope, $location, $http) {
        $rootScope.authenticated = true;
       // $rootScope.showTabs();
    });

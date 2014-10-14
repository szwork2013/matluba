'use strict';

/* App Module */
var httpHeaders;

var shopstuffsApp = angular.module('shopstuffsApp', ['http-auth-interceptor', 'tmh.dynamicLocale',
    'ngResource', 'ngRoute', 'ngCookies', 'shopstuffsAppUtils', 'pascalprecht.translate', 'truncate', 'ui.bootstrap']);

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, tmhDynamicLocaleProvider, USER_ROLES) {
        $routeProvider
            .when('/register', {
                templateUrl: 'views/register.html',
                controller: 'RegisterController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/activate', {
                templateUrl: 'views/activate.html',
                controller: 'ActivationController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/error', {
                templateUrl: 'views/error.html',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/settings', {
                templateUrl: 'views/settings.html',
                controller: 'SettingsController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/password', {
                templateUrl: 'views/password.html',
                controller: 'PasswordController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/sessions', {
                templateUrl: 'views/sessions.html',
                controller: 'SessionsController',
                resolve: {
                    resolvedSessions: ['Sessions', function (Sessions) {
                            return Sessions.get();
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/metrics', {
                templateUrl: 'views/metrics.html',
                controller: 'MetricsController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/logs', {
                templateUrl: 'views/logs.html',
                controller: 'LogsController',
                resolve: {
                    resolvedLogs: ['LogsService', function (LogsService) {
                        return LogsService.findAll();
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/audits', {
                templateUrl: 'views/audits.html',
                controller: 'AuditsController',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .when('/logout', {
                templateUrl: 'views/main.html',
                controller: 'LogoutController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            })
            .when('/docs', {
                templateUrl: 'views/docs.html',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
            .otherwise({
                templateUrl: 'views/main.html',
                controller: 'MainController',
                access: {
                    authorizedRoles: [USER_ROLES.all]
                }
            });

        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/',
            suffix: '.json'
        });

        $translateProvider.useCookieStorage();

        $translateProvider.preferredLanguage('en');

        tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
        tmhDynamicLocaleProvider.useCookieStorage('NG_TRANSLATE_LANG_KEY');

        httpHeaders = $httpProvider.defaults.headers;
    })
    .run(function ($rootScope, $location, $http, AuthenticationSharedService, Session, USER_ROLES) {
        // Initialize angular-translate

        $rootScope.authenticated = false;
        $rootScope.ui = {};
        $rootScope.ui.loading = false;
        $rootScope.isAuthorized = AuthenticationSharedService.isAuthorized;
        $rootScope.$on('$routeChangeStart', function (event, next) {
//            $rootScope.ui.loading = false;
            $rootScope.userRoles = USER_ROLES;
            var authorized = AuthenticationSharedService.isAuthorized(next.access.authorizedRoles);
            AuthenticationSharedService.valid(next.access.authorizedRoles);
        });

        // Call when the the client is confirmed
        $rootScope.$on('event:auth-loginConfirmed', function (data) {
            $rootScope.authenticated = true;
            if ($location.path() === "/login") {
                $location.path('/').replace();
            }
        });

        // Call when the 401 response is returned by the server
        $rootScope.$on('event:auth-loginRequired', function (rejection) {
            Session.invalidate();
            $rootScope.authenticated = false;
            if ($location.path() !== "/" && $location.path() !== "" && $location.path() !== "/register" &&
                $location.path() !== "/activate") {
                $location.path('/login').replace();
            }
        });

        // Call when the 403 response is returned by the server
        $rootScope.$on('event:auth-notAuthorized', function (rejection) {
            $rootScope.errorMessage = 'errors.403';
            $location.path('/error').replace();
        });


        // Call when the user logs out
        $rootScope.$on('event:auth-loginCancelled', function () {
            $location.path('');
        });
    });

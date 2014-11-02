'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/service', {
                    templateUrl: 'views/services.html',
                    controller: 'ServiceController',
                    resolve:{
                        resolvedService: ['Service', function (Service) {
                            return Service.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });

'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/attribute', {
                    templateUrl: 'views/attributes.html',
                    controller: 'AttributeController',
                    resolve:{
                        resolvedAttribute: ['Attribute', function (Attribute) {
                            return Attribute.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });

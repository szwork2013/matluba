'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/company', {
                    templateUrl: 'views/company.html',
                    controller: 'CompanyController',
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });

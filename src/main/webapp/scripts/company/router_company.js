'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/company', {
                    templateUrl: 'views/companys.html',
                    controller: 'CompanyController',
                    resolve:{
                        resolvedCompany: ['Company', function (Company) {
                            return Company.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });

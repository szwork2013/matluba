'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/product', {
                    templateUrl: 'views/products.html',
                    controller: 'ProductController',
                    resolve:{
                        resolvedProduct: ['Product', function (Product) {
                            return Product.query();
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });

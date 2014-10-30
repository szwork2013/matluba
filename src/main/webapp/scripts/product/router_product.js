/**
 * Created by jasurbek.umarov on 10/4/2014.
 */
'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
        $routeProvider
            .when('/product/edit/:productId?', {
                templateUrl: 'views/product.html',
                controller: 'AddProductCtrl',
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
    });

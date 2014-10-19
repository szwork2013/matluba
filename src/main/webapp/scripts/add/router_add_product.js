/**
 * Created by jasurbek.umarov on 10/4/2014.
 */
'use strict';

shopstuffsApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
        $routeProvider
            .when('/product/edit/:productId?', {
                templateUrl: 'views/add.html',
                controller: 'AddProductCtrl',
                resolve:{
                    resolvedProduct: ['Product', '$routeParams', '$log', function (Product, $routeParams, $log) {
                        return $routeParams.productId ? Product.get({ "id": $routeParams.productId }) : {};
                    }]
                },
                access: {
                    authorizedRoles: [USER_ROLES.admin]
                }
            })
    });

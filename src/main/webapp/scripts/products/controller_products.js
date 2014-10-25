'use strict';

shopstuffsApp.controller('ProductsCtrl', function ($scope,  Product) {
    $scope.products = Product.query();
});

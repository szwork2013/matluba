'use strict';

shopstuffsApp.controller('ProductsCtrl', function ($scope,  Product) {
    $scope.products = Product.search({page:0}, {title:'Adidas'});

});

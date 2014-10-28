'use strict';

shopstuffsApp.controller('ProductsCtrl', function ($scope,  Product) {
    $scope.page = {};
    $scope.products = [];
    Product.search({page:0}, {title:'Adidas'}).$promise.then(function(response){
        $scope.products = response.products || [];
        $scope.page = {index: response.index, total: response.pages};
    });


});

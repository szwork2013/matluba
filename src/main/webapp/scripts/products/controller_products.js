'use strict';

shopstuffsApp.controller('ProductController', function ($scope, resolvedProduct, Product) {

        $scope.products = resolvedProduct;

        $scope.saveProduct = function () {
            Product.save($scope.product,
                function (savedProduct) {
                    $scope.product = savedProduct;

                });
        };

        $scope.update = function (id) {
            $scope.product = Product.get({id: id});
            $('#saveProductModal').modal('show');
        };

        $scope.delete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.products = Product.query();
                });
        };

        $scope.clear = function () {
            $scope.product = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    });

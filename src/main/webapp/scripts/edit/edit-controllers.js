/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp.controller('productEditController',
    ['$scope', 'resolvedProduct', 'Product', 'Category', function($scope, resolvedProduct, Product, Category) {

    $scope.product = resolvedProduct || Product.create();

    $scope.types  = Product.getProductTypes();

    $scope.categories = Category.query();

    $scope.save = function () {

    };

    $scope.cancel = function () {

    };
}]);

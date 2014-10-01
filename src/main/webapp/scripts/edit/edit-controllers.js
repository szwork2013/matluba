/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp.controller('productEditController', [function($scope, resolvedProduct, Product, Categories) {

    $scope.product = resolvedProduct || Product.create();

    $scope.types  = Product.getProductTypes();

    $scope.categories = Catergories.query();

    $scope.save = function () {

    };

    $scope.cancel = function () {

    };
}]);

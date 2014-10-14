/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddProductController', ['$scope', 'resolvedProduct', 'Product', 'Category', 'ProductTypes',
        function($scope, resolvedProduct, Product, Category, ProductTypes) {

    $scope.product = !angular.equals(resolvedProduct, {}) ? resolvedProduct : new Product({ images:[], attributes:[]});

    $scope.types = ProductTypes.query();

    $scope.categories = Category.query();

    $scope.format = 'dd/mm/yyyy';

    $scope.save = function () {
        $scope.product.$save();
    };

    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };
}]);

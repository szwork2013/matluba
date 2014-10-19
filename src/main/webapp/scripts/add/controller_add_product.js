/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddProductCtrl', ['$scope', '$log', 'resolvedProduct', 'Product', 'Category', 'ProductTypes',
        function($scope, $log, resolvedProduct, Product, Category, ProductTypes) {
    $scope.product = !angular.equals(resolvedProduct, {}) ? resolvedProduct : new Product({images: [], attributes: [], id: null });

    $scope.types = ProductTypes.query();

    $scope.categories = Category.query();

    $scope.format = 'yyyy-mm-dd';

    $scope.save = function () {
        $scope.product.$save(function(response){
            $scope.saveStatus = 'Product created successfully';
            $log.info(response);
        }, function(error){

        });
    };

    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };
}]);

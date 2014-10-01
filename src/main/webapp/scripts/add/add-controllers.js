/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddController', ['$scope', 'resolvedProduct', 'Product', 'Category', function($scope, resolvedProduct, Product, Category) {

    $scope.product = resolvedProduct || new Product({ images:[], attributes:[], title: null,
        category: null, oldPrice: 0, rentalPrice: 0, price: 0, type: null });

    $scope.types  = Product.getProductTypes();

    $scope.categories = Category.query();

    $scope.save = function () {
        $scope.product.$save();
    };

    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };
}])
    .controller('ImageEditController', ['$scope', function($scope){
        $scope.addImage = function (image) {
            $scope.product.images.push(image);
        }
    }]);

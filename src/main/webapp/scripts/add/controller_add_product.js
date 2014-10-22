/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddProductCtrl', ['$scope', '$log', 'resolvedProduct', 'Product', 'Category', 'ProductTypes',
        function($scope, $log, resolvedProduct, Product, Category, ProductTypes) {

    $scope.templates = {'edit': 'views/partials/product.edit.form.partial.html',
        'read': 'views/partials/product.read.partial.html',
        'attribute': 'views/partials/product.attribute.partial.html'};

    $scope.view = $scope.templates.edit;

    $scope.product = !angular.equals(resolvedProduct, {}) ? resolvedProduct : new Product({images: [], attributes: [], id: null });

    $scope.types = ProductTypes.query();

    $scope.categories = Category.query();

    $scope.format = 'yyyy-mm-dd';

    $scope.attribute_view = !$scope.product.id ? '' : $scope.templates.attribute ;

    $scope.save = function () {
        $scope.product.$save(function(response){
            $scope.success = 'Product created successfully';
            $scope.error = null;
            $scope.view =  $scope.templates.read;
            $scope.attribute_view = $scope.templates.attribute;
        }, function(error){
            $scope.saveStatus = null;
            $scope.error = error;
        });
    };

    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };
}]);

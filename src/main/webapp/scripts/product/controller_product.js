/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddProductCtrl', ['$scope', '$log', 'resolvedProduct', 'Product', 'Category', 'ProductTypes',
        function($scope, $log, resolvedProduct, Product, Category, ProductTypes) {

    $scope.templates = {'edit': 'views/partials/product-form.html',
        'read': 'views/partials/product-view.html',
        'attribute': 'views/partials/product-attributes.html'};

    $scope.product = !angular.equals(resolvedProduct, {}) ? resolvedProduct : new Product({images: [], attributes: [], id: null });

    $scope.types = ProductTypes.query();

    $scope.categories = Category.query();

    $scope.displayFormat = 'yyyy-MM-dd';

    $scope.useTime = true;

    $scope.attribute_view = !$scope.product.id ? '' : $scope.templates.attribute;

    $scope.productForm = {};

     var validations = {

                _buildResult: function (valid, message) {
                    return {
                        'valid': !! valid,
                        error: valid ? null : message
                    };
                },

                decimal: function (message, optional) {
                    var self = this;
                    return function (value) {
                        return self._buildResult(!value  ? optional : /^[0-9]+(\.[0-9]+)?$/.test(value), message);
                    };
                },

                required: function (message) {
                    var self = this;
                    return function (value) {
                        return self._buildResult(value, message);
                    };
                },

                pipe: function () {
                    var self = this,
                        args = arguments;
                    return function (value) {
                        for (var i in args) {
                            var result = args[i](value);
                            if (!result.valid) {
                                return result;
                            }
                        }
                        return self._buildResult(true);
                    };
                },

                instance: function (validation, resultBag) {
                    return {
                        validate: function (model) {
                            var invalidCount = 0;
                            resultBag = resultBag || {};
                            for (var prop in validation) {
                                if (validation.hasOwnProperty(prop)) {
                                    var value = model[prop];
                                    resultBag[prop] = validation[prop](value);
                                    if (!resultBag[prop].valid) {
                                      invalidCount++;
                                    }
                                }
                            }
                            return !invalidCount;
                        }
                    };
                }
            };
     $scope.formValidator = validations.instance({
         'price': validations.pipe(validations.required("Required Field"), validations.decimal("Decimal required")),
         'title': validations.required("Required Field"),
         'category': validations.required("Required Field"),
         'rentalPrice': validations.decimal("Decimal required", true),
         'expireDate': validations.required("Required Field"),
         'releaseDate': validations.required("Required Field"),
         'productType': validations.required("Required Field"),
         'oldPrice': validations.decimal("Decimal required", true)
     }, $scope.productForm);

    $scope.save = function () {
        if($scope.formValidator.validate($scope.product)){
            console.log($scope.productForm);
            $scope.product.$save(function(response){
                $scope.success = 'Product created successfully';
                $scope.error = null;
                $scope.view =  $scope.templates.read;
                $scope.attribute_view = $scope.templates.attribute;
            }, function(error){
                $scope.saveStatus = null;
                $log.info(error);
                $scope.error = "ServerSide Unknown Error";
            });
        }
    };

    $scope.edit = function () {
        $scope.saveStatus = null;
        $scope.view = $scope.templates.edit;
    };

    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };

    $scope.edit();

}]);

/**
 * Created by jasurbek.umarov on 9/30/2014.
 */
// JavaScript source code

shopstuffsApp
    .controller('AddProductCtrl', ['$scope', '$log', '$routeParams', 'Product', 'Category', 'ProductTypes',
        function($scope, $log, $routeParams, Product, Category, ProductTypes) {

    $scope.templates = {
        'edit': 'views/partials/product-form.html',
        'read': 'views/partials/product-view.html',
        'attribute': 'views/partials/product-attributes.html',
        'image': 'views/partials/product-images.html'
    };

     $scope.productForm = {};
     $scope.master = {};

    if (angular.isDefined($routeParams.productId)) {
        $scope.product = Product.get({ "id": $routeParams.productId });
        $scope.product.$promise.then(function () {
          $scope.read();
        });
    } else {
        $scope.product = new Product({images: [], attributes: [], id: null });
        $scope.edit();
    }


    $scope.alerts = {};

    $scope.types = ProductTypes.query();

    $scope.categories = Category.query();

    $scope.displayFormat = 'yyyy-MM-dd';

    $scope.useTime = true;


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
        if($scope.formValidator.validate($scope.master)){
            angular.extend($scope.product, $scope.master);
            $scope.product.$save(function(response){
                $scope.alerts = { success: 'Product created successfully' };
                $scope.read();
            }, function(error){
                $log.info(error);
                $scope.alerts = { error: 'Unknown Error' };
            });
        }
    };

    $scope.edit = function () {
        // clean errors and success alerts
        $scope.alerts = {};
        $scope.master = angular.copy($scope.product);
        $scope.view = $scope.templates.edit;
    };

     $scope.imageView = function () {
           $scope.view = $scope.templates.image;
     };
            
    $scope.cancel = function () {
        $scope.product = null;
        // must redirect to previous page.
    };

    $scope.cancel = function () {
        $scope.master = angular.copy($scope.product);
        $scope.read();
    };
}]);

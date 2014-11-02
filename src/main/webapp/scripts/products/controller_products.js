'use strict';

shopstuffsApp.controller('ProductsCtrl', function ($scope,  Product, Image, $q) {
    $scope.page = {index: 1, page: 1};
    $scope.products = [];
    $scope.search = {};

    function observer (oldValue, newValue) {
        if (oldValue !== newValue) { $scope.update(); };
    }

    $scope.update = function() {
        var searchModel = angular.extend({}, $scope.search);
        Product.search({ page: $scope.page.index || 1 }, searchModel)
            .$promise.then(function(response){
                $scope.products = response.products || [];
                $scope.page.total = response.pages;
        });
    };

    $scope.hoveredProduct = {};
    $scope.getImgUrl = function(id) {
        var noImage = 'images/no-image.png';
        var deferred = $q.defer();
        Image.query({ productId: id }).$promise.then(function(images){
            deferred.resolve(images && images.length ? images[0].thumbnailImagePath : 'images/no-image.png');
        }, function(){
            deferred.reject('unknown error');
        });
        return deferred.promise;
    }

    $scope.$watchCollection('search', observer);
    $scope.$watch('page.index', observer);

    $scope.update();

});

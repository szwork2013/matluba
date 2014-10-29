'use strict';

shopstuffsApp.controller('ProductsCtrl', function ($scope,  Product) {
    $scope.page = {index: 1, page: 1};
    $scope.products = [];
    $scope.search = {};

    function observer (oldValue, newValue) {
        if (oldValue !== newValue) { $scope.update(); };
    }

    $scope.update = function() {
        console.log("update", Date.now());
        var searchModel = angular.extend({}, $scope.search);
        Product.search({ page: $scope.page.index || 1 }, searchModel).$promise.then(function(response){
            $scope.products = response.products || [];
            $scope.page.total = response.pages;
        });
    };
    $scope.$watchCollection('search', observer);
    $scope.$watch('page.index', observer);

    $scope.update();

});

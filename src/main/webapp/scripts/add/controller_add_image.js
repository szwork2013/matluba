/**
 * Created by jasurbek.umarov on 10/4/2014.
 */
shopstuffsApp.controller('AddImageController', ['$scope', function($scope){
    $scope.addImage = function (image) {
        $scope.product.images.push(image);
    }
}]);

'use strict';

shopstuffsApp.controller('CompanyController', function ($scope, Company) {
    $scope.alerts = {};

    $scope.master = Company.get().$promise.then(function(response){
        $scope.master = response;
        $scope.reset();
    });

    $scope.update = function(company) {
        $scope.master = angular.copy(company);
        Company.save($scope.master, function () {
            $scope.alerts = {success: 'Changes has been saved!'};
        });
    };

    $scope.reset = function() {
        $scope.company = angular.copy($scope.master);
        console.log($scope.company);
    };

    $scope.canUpdate = function(company) {
        return !$scope.form.$invalid  && !angular.equals(company, $scope.master);
    };
});

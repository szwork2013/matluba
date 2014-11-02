'use strict';

shopstuffsApp.controller('CompanyController', function ($scope, resolvedCompany, Company) {

        $scope.companys = resolvedCompany;

        $scope.create = function () {
            Company.save($scope.company,
                function () {
                    $scope.companys = Company.query();
                    $('#saveCompanyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.company = Company.get({id: id});
            $('#saveCompanyModal').modal('show');
        };

        $scope.delete = function (id) {
            Company.delete({id: id},
                function () {
                    $scope.companys = Company.query();
                });
        };

        $scope.clear = function () {
            $scope.company = {id: null, name: null, description: null, phone: null, email:null,
                               address:null, facebook:null, google:null,twitter:null, owner:null };
        };
    });

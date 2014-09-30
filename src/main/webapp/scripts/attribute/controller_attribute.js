'use strict';

shopstuffsApp.controller('AttributeController', function ($scope, resolvedAttribute, Attribute) {

        $scope.attributes = resolvedAttribute;

        $scope.create = function () {
            Attribute.save($scope.attribute,
                function () {
                    $scope.attributes = Attribute.query();
                    $('#saveAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.attribute = Attribute.get({id: id});
            $('#saveAttributeModal').modal('show');
        };

        $scope.delete = function (id) {
            Attribute.delete({id: id},
                function () {
                    $scope.attributes = Attribute.query();
                });
        };

        $scope.clear = function () {
            $scope.attribute = {id: null, sampleTextAttribute: null, sampleDateAttribute: null};
        };
    });

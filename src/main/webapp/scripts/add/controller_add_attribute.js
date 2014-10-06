/**
 * Created by jasurbek.umarov on 10/4/2014.
 */
shopstuffsApp.controller('AddAttributeController', ['$scope', 'Attribute', function ($scope, Attribute) {
    $scope.attr_form = {};
    $scope.attr_types = ['number', 'text', 'date'];

    $scope.attr_form.showAddView = false;

    $scope.p_attr = {};
    $scope.p_attr.attributeType = $scope.attr_types[1];

    $scope.editAttr = function (attr) {
        $scope.show();
        $scope.p_attr = attr;
    };
    $scope.addNew = function () {
        $scope.show();
        $scope.p_attr = {};
    };
    $scope.toggleAttrView  = function () {
        $scope.attr_form.showAddView = !$scope.attr_form.showAddView;
    };
    $scope.show  = function () {
        $scope.attr_form.showAddView = true;
    };
    $scope.saveAttr = function () {
        Attribute.save($scope.p_attr, function (savedAttr) {
            $scope.p_attr.id = savedAttr.id;
            $scope.product.attributes.push($scope.p_attr);
            $scope.p_attr = {};
            $scope.toggleAttrView();
        });
    };
}]);

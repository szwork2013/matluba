shopstuffsApp.controller('AddAttributeController', ['$scope', 'Attribute', function ($scope, Attribute) {
    $scope.attr_form = {};
    $scope.attr_form.showAddView = false;

    $scope.attr = {};
    $scope.attr_types = ['number', 'text', 'date'];
    $scope.attr.attributeType = $scope.attr_types[1];

    $scope.labels=Attribute.labels();

    $scope.editAttr = function (attr) {
        $scope.show();
        $scope.attr = attr;
    };
    $scope.addNew = function () {
        $scope.show();
        $scope.attr = {};
    };
    $scope.toggleAttrView  = function () {
        $scope.attr_form.showAddView = !$scope.attr_form.showAddView;
    };
    $scope.onLabelSelected = function (label) {
        if (label) {
            $scope.options=Attribute.options({id:label.id});
        }
    };
    $scope.show  = function () {
        $scope.attr_form.showAddView = true;
    };
    $scope.saveAttr = function () {
        Attribute.save($scope.attr, function (savedAttr) {
            $scope.attr.id = savedAttr.id;
            $scope.product.attributes.push($scope.attr);
            $scope.attr = {};
            $scope.toggleAttrView();
        });
    };
}]);

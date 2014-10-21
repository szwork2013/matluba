/**
 * Created by jasurbek.umarov on 10/19/2014.
 */
shopstuffsApp.controller('AttributeModalCtrl', function ($scope, label, Attribute) {
    $scope.modes = { Edit: 'Edit', New: 'New Attribute', Read: 'ValueAdding' };
    $scope.mode = $scope.modes.Read;

    $scope.label = label;

    console.log(label);

    $scope.doneEditAttribute = function () {
        $scope.mode = $scope.modes.Read;
    }

    $scope.addOption = function (item) {
        console.log($scope.label);
        var attribute = new Attribute({ value: item.value,
                    parent: { id: $scope.label.id },
                    type: $scope.label.type });

        attribute.$save();

        $scope.label.children.push( attribute );
    }

});
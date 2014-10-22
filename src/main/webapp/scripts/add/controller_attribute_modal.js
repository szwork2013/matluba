/**
 * Created by jasurbek.umarov on 10/19/2014.
 */
shopstuffsApp.controller('AttributeModalCtrl', function ($scope, label, Attribute, $modalInstance) {
    $scope.label = label;

    $scope.validators = {
        'text': function (value) {
            return { valid: angular.isString(value), errorText: 'Invalid String' };
        },
        'number': function (value) {
            return { valid: angular.isNumber(value), errorText: 'Invalid Number' };
        },
        color: function () {
            return { valid: true };
        }};

    $scope.addOption = function (item) {
        var result;
        if ($scope.validators in $scope.label.type) {
            result = $scope.validators[$scope.label.type](item.value);
        } else {
            throw new Error('Unexpected type');
        }

        if (result.valid) {
            $scope.error = result.errorText
            return;
        }

        var attribute = new Attribute({ value: item.value,
                    parent: { id: $scope.label.id },
                    type: $scope.label.type });

        attribute.$save();

        $scope.label.children.push( attribute );
    }

    $scope.ok = function () {
        $modalInstance.close();
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});